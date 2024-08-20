package com.Nelson.inventory_service.repository;

import com.Nelson.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
//    @Query("SELECT s FROM Inventory s WHERE s.skuCode = :skuCode")
//    Optional<Inventory> findBySkucode(@Param("skuCode") String skuCode);

    @Query("SELECT s FROM Inventory s WHERE s.skuCode IN :skuCode")
    List<Inventory> findBySkuCodeList(List<String> skuCode);

}
