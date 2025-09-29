package com.example.pib2.model.product.mappers;

import com.example.pib2.model.product.dto.ProductDto;
import com.example.pib2.model.product.Product;
import com.example.pib2.model.category.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    @Mapping(source = "categoryId", target = "category")
    Product toEntity(ProductDto productDto);

    // Conversión manual: del id al objeto Category
    default Category map(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }

    // Conversión manual: de Category al id
    default Long map(Category category) {
        return category != null ? category.getId() : null;
    }
}