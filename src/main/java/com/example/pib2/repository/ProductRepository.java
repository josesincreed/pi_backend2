package com.example.pib2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pib2.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
