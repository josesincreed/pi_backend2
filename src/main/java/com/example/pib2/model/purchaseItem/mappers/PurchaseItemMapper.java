package com.example.pib2.model.purchaseItem.mappers;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import com.example.pib2.model.purchaseItem.PurchaseItem;
import com.example.pib2.model.purchase.Purchase;
import com.example.pib2.model.product.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PurchaseItemMapper {

    @Mapping(source = "purchase.id", target = "purchaseId")
    @Mapping(source = "product.id", target = "productId")
    PurchaseItemDto toDto(PurchaseItem purchaseItem);

    @Mapping(source = "purchaseId", target = "purchase")
    @Mapping(source = "productId", target = "product")
    PurchaseItem toEntity(PurchaseItemDto purchaseItemDto);

    // Conversión manual de IDs a entidades
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
}