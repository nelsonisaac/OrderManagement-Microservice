package com.Nelson.inventory_service.controller;

import com.Nelson.inventory_service.dto.InventoryResponse;
import com.Nelson.inventory_service.model.Inventory;
import com.Nelson.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    @Autowired
    private final InventoryService inventoryService;
    /*THIS IS TO REQUEST PARAMS FROM USER AND SEND IT TO DB */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }

    /*THIS IS TO GET PATH FROM THE URL AND SEND IT TO DB
    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("skuCode") String skuCode){
        return inventoryService.isInStock(skuCode);
    }*/


    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Inventory> getId(@PathVariable("id") Long id){
        return inventoryService.getId(id);
    }

}
