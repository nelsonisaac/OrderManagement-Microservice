package com.Nelson.order_service.service;

import com.Nelson.order_service.config.WebClientConfig;
import com.Nelson.order_service.dto.InventoryResponse;
import com.Nelson.order_service.dto.OrderLineItemsDto;
import com.Nelson.order_service.dto.OrderRequest;
import com.Nelson.order_service.model.Order;
import com.Nelson.order_service.model.OrderLineItems;
import com.Nelson.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final WebClient.Builder webClientBuilder;
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        //Getting orderLineiTems and setting them in the order
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        //Getting the orderLineItems skucodes, which was set in the above process, and converting them into Array[]
        List<String> skuCodes = order.getOrderLineItemsList()
                                .stream()
                                .map(orderLineItem -> orderLineItem.getSkuCode()).toList();

        /*CALL INVENTORY SERVICE AND PLACE ORDER IF PRODUCT IS IN STOCK*/
        //WebClient is used to contact inventory service and retrieve the info
        //bodytoMono(): specifies that the body of the response should be converted to a Mono<Boolean>
        //block() :method blocks the current thread until the Mono completes and returns the result. This is useful
        //for converting asynchronous calls to synchronous ones,
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                        .uri("http://inventory-service/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        //Checks if the isInStock variable is true for all the items in the order using JAVA 8 Array.stream().allMatch()
        boolean inStock = Arrays.stream(inventoryResponseArray)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());
        if(inStock){
            orderRepository.save(order);
            return "Order placed";
        }else{
            throw new IllegalArgumentException("Product not in stock");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
