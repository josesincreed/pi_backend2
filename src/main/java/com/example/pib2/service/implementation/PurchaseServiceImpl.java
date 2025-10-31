package com.example.pib2.service.implementation;

import com.example.pib2.exception.CustomException;
import com.example.pib2.helpers.TechnicalErrorMessage;
import com.example.pib2.model.product.Product;
import com.example.pib2.model.purchase.Purchase;
import com.example.pib2.model.purchase.dto.PurchaseDto;
import com.example.pib2.model.purchase.mappers.PurchaseMapper;
import com.example.pib2.model.purchaseItem.PurchaseItem;
import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import com.example.pib2.model.purchaseItem.mappers.PurchaseItemMapper;
import com.example.pib2.repository.ProductRepository;
import com.example.pib2.repository.PurchaseItemRepository;
import com.example.pib2.repository.PurchaseRepository;
import com.example.pib2.service.PurchaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseItemMapper purchaseItemMapper;

    /**
     * Crea una nueva compra con sus ítems y reduce el stock de los productos comprados.
     */
    @Override
    @Transactional
    public PurchaseDto create(PurchaseDto dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new CustomException(
                    TechnicalErrorMessage.ORDER_EMPTY.getCode(),
                    "La compra no puede estar vacía. Debe contener al menos un producto."
            );
        }

        Purchase purchase = purchaseMapper.toEntity(dto);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setCity(dto.getCity());

        List<PurchaseItem> items = dto.getItems().stream().map(itemDto -> {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new CustomException(
                            TechnicalErrorMessage.PRODUCT_NOT_FOUND.getCode(),
                            TechnicalErrorMessage.PRODUCT_NOT_FOUND.getMessage(itemDto.getProductId())
                    ));

            if (product.getStockQuantity() < itemDto.getQuantity()) {
                throw new CustomException(
                        "INSUFFICIENT_STOCK",
                        "Stock insuficiente para el producto: " + product.getName()
                );
            }

            // Reducir stock
            product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            // Asociar ítem con la compra
            PurchaseItem item = purchaseItemMapper.toEntity(itemDto);
            item.setPurchase(purchase); // 👈 Asocia la compra
            return item;
        }).collect(Collectors.toList());

        // 👇 Agregar los ítems antes de guardar la compra
        purchase.setItems(items);

        // 👇 Guardar la compra (gracias al cascade = ALL, también guarda los ítems)
        Purchase savedPurchase = purchaseRepository.save(purchase);

        return purchaseMapper.toDto(savedPurchase);
    }


    @Override
    public List<PurchaseDto> getAll() {
        return purchaseRepository.findAll()
                .stream()
                .map(purchaseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> getByUserId(Long userId) {
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        if (purchases.isEmpty()) {
            throw new CustomException(
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(userId)
            );
        }
        return purchases.stream()
                .map(purchaseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseDto getById(Long id) {
        return purchaseRepository.findById(id)
                .map(purchaseMapper::toDto)
                .orElseThrow(() -> new CustomException(
                        TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                        TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(id)
                ));
    }

    @Override
    public void delete(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new CustomException(
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(id)
            );
        }
        purchaseRepository.deleteById(id);
    }
}
