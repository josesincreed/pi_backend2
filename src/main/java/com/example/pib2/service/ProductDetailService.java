package com.example.pib2.service;

import com.example.pib2.model.product_details.dto.ProductDetailDTO;

import java.util.List;

public interface ProductDetailService {

    ProductDetailDTO createProduct(ProductDetailDTO productDTO);

    ProductDetailDTO getProductById(Long id);

    List<ProductDetailDTO> getAllProducts();

    ProductDetailDTO updateProduct(Long id, ProductDetailDTO productDTO);

    void deleteProduct(Long id);
}
