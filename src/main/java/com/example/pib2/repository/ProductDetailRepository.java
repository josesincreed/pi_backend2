package com.example.pib2.repository;

import com.example.pib2.model.product_details.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByTipoArtesania(String tipoArtesania);
}
