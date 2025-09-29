package com.example.pib2.repository;

import com.example.pib2.model.purchaseItem.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
    List<PurchaseItem> findByPurchaseId(Long purchaseId); // buscar ítems de una compra
}