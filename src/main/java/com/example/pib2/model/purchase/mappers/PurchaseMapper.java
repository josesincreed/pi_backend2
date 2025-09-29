package com.example.pib2.model.purchase.mappers;

import com.example.pib2.model.purchase.dto.PurchaseDto;
import com.example.pib2.model.purchase.Purchase;
import com.example.pib2.model.purchaseItem.mappers.PurchaseItemMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PurchaseItemMapper.class})
public interface PurchaseMapper {

    PurchaseDto toDto(Purchase purchase);

    Purchase toEntity(PurchaseDto purchaseDto);
}