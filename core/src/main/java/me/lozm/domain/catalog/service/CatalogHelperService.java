package me.lozm.domain.catalog.service;

import lombok.RequiredArgsConstructor;
import me.lozm.domain.catalog.entity.Catalog;
import me.lozm.domain.catalog.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogHelperService {

    private final CatalogRepository catalogRepository;


    public Optional<Catalog> findCatalog(String productId) {
        return catalogRepository.findByProductId(productId);
    }

    public Catalog getCatalog(String productId) {
        return findCatalog(productId).orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상품입니다. productId: %s", productId)));
    }

}
