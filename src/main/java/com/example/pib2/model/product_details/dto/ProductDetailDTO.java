package com.example.pib2.model.product_details.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {

    private Long productId;
    private String origen;
    private String tipoArtesania;
    private String oficio;
    private String materiaPrima;
    private String etnia;
    private String programa;
    private String sku;
    private String descripcionDetallada;
}
