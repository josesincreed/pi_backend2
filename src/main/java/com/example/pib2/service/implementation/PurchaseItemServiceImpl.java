package com.example.pib2.service.implementation;

import com.example.pib2.exception.CustomException;
import com.example.pib2.helpers.TechnicalErrorMessage;
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
            throw new CustomException(
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.ORDER_NOT_FOUND.getMessage(dto.getPurchaseId())
            );
        }
        if (!productRepository.existsById(dto.getProductId())) {
            throw new CustomException(
                    TechnicalErrorMessage.PRODUCT_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.PRODUCT_NOT_FOUND.getMessage(dto.getProductId())
            );
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
}
