package com.example.pib2.service;

import com.example.pib2.model.purchase.dto.PurchaseDto;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    PurchaseDto create(PurchaseDto dto);
    List<PurchaseDto> getAll();
    List<PurchaseDto> getByUserId(Long userId);
    Optional<PurchaseDto> getById(Long id);
    boolean delete(Long id);
}