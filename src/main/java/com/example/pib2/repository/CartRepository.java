package com.example.pib2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pib2.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {}

