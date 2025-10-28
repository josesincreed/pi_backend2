package com.example.pib2.model.product_details;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String origen;
    private String tipoArtesania;
    private String oficio;
    private String materiaPrima;
    private String etnia;
    private String programa;
    private String sku;

    @Column(length = 1000)
    private String descripcionDetallada;
}
