package com.example.pib2.model.purchase.mappers;

import com.example.pib2.model.purchase.Purchase;
import com.example.pib2.model.purchase.dto.PurchaseDto;
import com.example.pib2.model.purchaseItem.mappers.PurchaseItemMapper;
import org.mapstruct.*;

/**
 * Mapper que convierte entre entidades Purchase y sus DTOs, evitando ciclos infinitos.
 */
@Mapper(componentModel = "spring", uses = {PurchaseItemMapper.class})
public interface PurchaseMapper {

    @Mappings({
            @Mapping(target = "city", source = "city"),
            @Mapping(target = "items", source = "items"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "totalAmount", source = "totalAmount"),
            @Mapping(target = "purchaseDate", source = "purchaseDate")
    })
    PurchaseDto toDto(Purchase purchase);

    /**
     * Convierte un DTO a entidad. Se ignoran los campos autogenerados.
     */
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "id", ignore = true), // el ID lo genera la BD
            @Mapping(target = "items", ignore = false) // permite que se asignen los ítems correctamente
    })
    Purchase toEntity(PurchaseDto purchaseDto);
}
