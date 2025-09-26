package com.example.pib2.service.implementation;

import com.example.pib2.model.purchase.dto.PurchaseDto;
import com.example.pib2.model.purchaseItem.mappers.PurchaseItemMapper;
import com.example.pib2.model.purchase.mappers.PurchaseMapper;
import com.example.pib2.model.purchase.Purchase;
import com.example.pib2.model.purchaseItem.PurchaseItem;
import com.example.pib2.repository.PurchaseRepository;
import com.example.pib2.repository.ProductRepository;
import com.example.pib2.repository.PurchaseItemRepository;
import com.example.pib2.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseItemMapper purchaseItemMapper;

    @Override
    public PurchaseDto create(PurchaseDto dto) {
        Purchase purchase = purchaseMapper.toEntity(dto);
        purchase.setPurchaseDate(LocalDateTime.now());

        // Guardamos primero la compra
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Guardamos los ítems
        List<PurchaseItem> items = dto.getItems().stream().map(itemDto -> {
            if (!productRepository.existsById(itemDto.getProductId())) {
                throw new RuntimeException("El producto con ID " + itemDto.getProductId() + " no existe");
            }
            PurchaseItem item = purchaseItemMapper.toEntity(itemDto);
            item.setPurchase(savedPurchase);
            return item;
        }).collect(Collectors.toList());

        purchaseItemRepository.saveAll(items);
        savedPurchase.setItems(items);

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
        return purchaseRepository.findByUserId(userId)
                .stream()
                .map(purchaseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PurchaseDto> getById(Long id) {
        return purchaseRepository.findById(id)
                .map(purchaseMapper::toDto);
    }

    @Override
    public boolean delete(Long id) {
        if (purchaseRepository.existsById(id)) {
            purchaseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}