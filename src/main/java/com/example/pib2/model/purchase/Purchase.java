package com.example.pib2.model.purchase;

import com.example.pib2.model.purchaseItem.PurchaseItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ejemplo: id del usuario que realizó la compra
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String city;

    // Relación con PurchaseItem (una compra puede tener varios ítems)
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> items;
}