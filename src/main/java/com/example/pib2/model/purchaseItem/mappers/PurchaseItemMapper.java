package com.example.pib2.model.purchaseItem.mappers;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import com.example.pib2.model.purchaseItem.PurchaseItem;
import com.example.pib2.model.purchase.Purchase;
import com.example.pib2.model.product.Product;
import org.mapstruct.*;

/**
 * Mapper que convierte entre PurchaseItem y su DTO,
 * evitando ciclos infinitos y manejando relaciones por ID.
 */
@Mapper(componentModel = "spring")
public interface PurchaseItemMapper {

    // De entidad a DTO
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "purchase.id", target = "purchaseId"),
            @Mapping(source = "product.id", target = "productId")
    })
    PurchaseItemDto toDto(PurchaseItem purchaseItem);

    // De DTO a entidad (se ignora purchase porque se asigna manualmente en el servicio)
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "purchase", ignore = true),
            @Mapping(target = "product", expression = "java(mapProduct(purchaseItemDto.getProductId()))")
    })
    PurchaseItem toEntity(PurchaseItemDto purchaseItemDto);

    // ======================
    // Métodos auxiliares
    // ======================

    default Product mapProduct(Long productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    default Long mapProduct(Product product) {
        return product != null ? product.getId() : null;
    }

    default Purchase mapPurchase(Long purchaseId) {
        if (purchaseId == null) {
            return null;
        }
        Purchase purchase = new Purchase();
        purchase.setId(purchaseId);
        return purchase;
    }

    default Long mapPurchase(Purchase purchase) {
        return purchase != null ? purchase.getId() : null;
    }
}
