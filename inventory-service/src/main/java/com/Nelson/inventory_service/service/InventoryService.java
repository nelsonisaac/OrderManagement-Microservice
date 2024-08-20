package com.Nelson.inventory_service.service;

import com.Nelson.inventory_service.dto.InventoryResponse;
import com.Nelson.inventory_service.model.Inventory;
import com.Nelson.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    @Autowired
    private final InventoryRepository inventoryRepository;
    // As we have multiple skuCodes for 1 order, we cannot return only single skucode as response
    // instead we return a list of skucodes for 1 order. So we create a dto InventoryResponse and
    // map all the list to that dto, and send it as response to Orderservice
    @Transactional(readOnly = true)
    @SneakyThrows //Just to remove exception, not advisable to use
    public List<InventoryResponse> isInStock(List<String> skuCode){
/*        log.info("Wait started");
        Thread.sleep(10000); //To achieve slow response from service all, we wantedly slowing it down using threads
        log.info("Wait ended"); */
//        System.out.println(inventoryRepository.findBySkuCodeList((skuCode)));
//        List<String> l = inventoryRepository.findBySkuCodeList((skuCode));
        List<InventoryResponse> l = inventoryRepository.findBySkuCodeList(skuCode)
                .stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()
                ).toList();

        return l;
    }

    public Optional<Inventory> getId(Long id){
        return inventoryRepository.findById(id);
    }
}
