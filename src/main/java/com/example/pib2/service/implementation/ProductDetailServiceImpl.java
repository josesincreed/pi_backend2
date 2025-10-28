package com.example.pib2.service.implementation;

import com.example.pib2.model.product_details.ProductDetail;
import com.example.pib2.model.product_details.dto.ProductDetailDTO;
import com.example.pib2.model.product_details.mappers.ProductDetailMapper;
import com.example.pib2.repository.ProductDetailRepository;
import com.example.pib2.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductDetailMapper productDetailMapper;

    @Override
    public ProductDetailDTO createProduct(ProductDetailDTO productDTO) {
        ProductDetail productDetail = productDetailMapper.toEntity(productDTO);
        ProductDetail saved = productDetailRepository.save(productDetail);
        return productDetailMapper.toDTO(saved);
    }

    @Override
    public ProductDetailDTO getProductById(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductDetail not found with id: " + id));
        return productDetailMapper.toDTO(productDetail);
    }

    @Override
    public List<ProductDetailDTO> getAllProducts() {
        return productDetailRepository.findAll()
                .stream()
                .map(productDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailDTO updateProduct(Long id, ProductDetailDTO productDTO) {
        ProductDetail existing = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductDetail not found with id: " + id));

        // Actualizar campos (ejemplo, todos los campos del DTO)
        existing.setOrigen(productDTO.getOrigen());
        existing.setTipoArtesania(productDTO.getTipoArtesania());
        existing.setOficio(productDTO.getOficio());
        existing.setMateriaPrima(productDTO.getMateriaPrima());
        existing.setEtnia(productDTO.getEtnia());
        existing.setPrograma(productDTO.getPrograma());
        existing.setSku(productDTO.getSku());
        existing.setDescripcionDetallada(productDTO.getDescripcionDetallada());

        ProductDetail updated = productDetailRepository.save(existing);
        return productDetailMapper.toDTO(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        ProductDetail existing = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductDetail not found with id: " + id));
        productDetailRepository.delete(existing);
    }
}

