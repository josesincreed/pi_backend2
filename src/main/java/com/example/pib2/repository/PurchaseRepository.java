package com.example.pib2.repository;

import com.example.pib2.model.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId(Long userId); // buscar compras de un usuario
}