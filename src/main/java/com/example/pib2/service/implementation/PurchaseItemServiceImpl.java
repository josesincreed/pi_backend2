package com.example.pib2.service.implementation;

import com.example.pib2.exception.CustomException;
import com.example.pib2.helpers.TechnicalErrorMessage;
import com.example.pib2.model.product.Product;
import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import com.example.pib2.model.purchaseItem.mappers.PurchaseItemMapper;
import com.example.pib2.model.purchaseItem.PurchaseItem;
import com.example.pib2.repository.PurchaseItemRepository;
import com.example.pib2.repository.ProductRepository;
import com.example.pib2.repository.PurchaseRepository;
import com.example.pib2.service.PurchaseItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseItemServiceImpl implements PurchaseItemService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final PurchaseItemMapper purchaseItemMapper;

    @Override
    public PurchaseItemDto create(PurchaseItemDto dto) {
        // Validar existencia de la compra
        if (dto.getPurchaseId() == null || !purchaseRepository.existsById(dto.getPurchaseId())) {
            throw new CustomException(
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(dto.getPurchaseId())
            );
        }

        // Validar existencia del producto
        if (dto.getProductId() == null || !productRepository.existsById(dto.getProductId())) {
            throw new CustomException(
                    TechnicalErrorMessage.PRODUCT_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.PRODUCT_NOT_FOUND.getMessage(dto.getProductId())
            );
        }

        // Validar cantidad positiva
        if (dto.getQuantity() <= 0) {
            throw new CustomException(
                    "INVALID_QUANTITY",
                    "La cantidad del producto debe ser mayor a cero."
            );
        }

        // Mapear y guardar el ítem
        PurchaseItem entity = purchaseItemMapper.toEntity(dto);
        PurchaseItem saved = purchaseItemRepository.save(entity);

        // Retornar DTO
        return purchaseItemMapper.toDto(saved);
    }

    @Override
    public List<PurchaseItemDto> getAll() {
        return purchaseItemRepository.findAll()
                .stream()
                .map(purchaseItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseItemDto getById(Long id) {
        return purchaseItemRepository.findById(id)
                .map(purchaseItemMapper::toDto)
                .orElseThrow(() -> new CustomException(
                        TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                        TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(id)
                ));
    }

    @Override
    public List<PurchaseItemDto> getByPurchaseId(Long purchaseId) {
        List<PurchaseItemDto> items = purchaseItemRepository.findByPurchaseId(purchaseId)
                .stream()
                .map(purchaseItemMapper::toDto)
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            throw new CustomException(
                    TechnicalErrorMessage.ORDER_EMPTY.getCode(),
                    TechnicalErrorMessage.ORDER_EMPTY.getMessage()
            );
        }

        return items;
    }

    @Override
    public PurchaseItemDto update(Long id, PurchaseItemDto dto) {
        PurchaseItem existing = purchaseItemRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                        TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(id)
                ));

        existing.setQuantity(dto.getQuantity());
        existing.setPrice(dto.getPrice());

        return purchaseItemMapper.toDto(purchaseItemRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!purchaseItemRepository.existsById(id)) {
            throw new CustomException(
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(id)
            );
        }
        purchaseItemRepository.deleteById(id);
    }

    /**
     * Reduce el stock de los productos después de registrar una compra.
     * Si algún producto no tiene suficiente stock, lanza una excepción y revierte la transacción.
     */
    @Override
    @Transactional
    public void reduceStockAfterPurchase(List<PurchaseItemDto> purchasedItems) {
        for (PurchaseItemDto item : purchasedItems) {
            Long productId = item.getProductId();
            int quantityPurchased = item.getQuantity();

            // Buscar el producto
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new CustomException(
                            TechnicalErrorMessage.PRODUCT_NOT_FOUND.getCode(),
                            "Producto con ID " + productId + " no encontrado."
                    ));

            // Validar stock suficiente
            if (product.getStockQuantity() < quantityPurchased) {
                throw new CustomException(
                        "INSUFFICIENT_STOCK",
                        "Stock insuficiente para el producto: " + product.getName()
                );
            }

            // Reducir stock
            product.setStockQuantity(product.getStockQuantity() - quantityPurchased);

            // Guardar cambios
            productRepository.save(product);
        }
    }
}
