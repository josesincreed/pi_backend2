package com.example.pib2.service;

import com.example.pib2.model.purchase.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {
    PurchaseDto create(PurchaseDto dto);
    List<PurchaseDto> getAll();
    List<PurchaseDto> getByUserId(Long userId);
    PurchaseDto getById(Long id);
    void delete(Long id);
}
