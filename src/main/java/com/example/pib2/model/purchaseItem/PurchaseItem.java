package com.example.pib2.model.purchaseItem;

import com.example.pib2.model.product.Product;
import com.example.pib2.model.purchase.Purchase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price; // precio unitario al momento de la compra

    // Relación con Purchase (muchos ítems pertenecen a una compra)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    // Relación con Product (cada ítem corresponde a un producto específico)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
