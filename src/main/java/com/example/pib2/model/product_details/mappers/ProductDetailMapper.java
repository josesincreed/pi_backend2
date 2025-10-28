package com.example.pib2.model.product_details.mappers;

import com.example.pib2.model.product_details.ProductDetail;
import com.example.pib2.model.product_details.dto.ProductDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    ProductDetailMapper INSTANCE = Mappers.getMapper(ProductDetailMapper.class);

    ProductDetailDTO toDTO(ProductDetail productDetail);

    ProductDetail toEntity(ProductDetailDTO productDetailDTO);
}
