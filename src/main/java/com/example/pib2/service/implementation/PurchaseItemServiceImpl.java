package com.example.pib2.service.implementation;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import com.example.pib2.model.purchaseItem.mappers.PurchaseItemMapper;
import com.example.pib2.model.purchaseItem.PurchaseItem;
import com.example.pib2.repository.PurchaseItemRepository;
import com.example.pib2.repository.ProductRepository;
import com.example.pib2.repository.PurchaseRepository;
import com.example.pib2.service.PurchaseItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        if (!purchaseRepository.existsById(dto.getPurchaseId())) {
            throw new RuntimeException("La compra con ID " + dto.getPurchaseId() + " no existe");
        }
        if (!productRepository.existsById(dto.getProductId())) {
            throw new RuntimeException("El producto con ID " + dto.getProductId() + " no existe");
        }

        PurchaseItem entity = purchaseItemMapper.toEntity(dto);
        PurchaseItem saved = purchaseItemRepository.save(entity);
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
    public Optional<PurchaseItemDto> getById(Long id) {
        return purchaseItemRepository.findById(id)
                .map(purchaseItemMapper::toDto);
    }

    @Override
    public List<PurchaseItemDto> getByPurchaseId(Long purchaseId) {
        return purchaseItemRepository.findByPurchaseId(purchaseId)
                .stream()
                .map(purchaseItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PurchaseItemDto> update(Long id, PurchaseItemDto dto) {
        return purchaseItemRepository.findById(id)
                .map(existing -> {
                    existing.setQuantity(dto.getQuantity());
                    existing.setPrice(dto.getPrice());
                    return purchaseItemMapper.toDto(purchaseItemRepository.save(existing));
                });
    }

    @Override
    public boolean delete(Long id) {
        if (purchaseItemRepository.existsById(id)) {
            purchaseItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}