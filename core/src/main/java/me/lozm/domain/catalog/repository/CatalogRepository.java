package me.lozm.domain.catalog.repository;

import me.lozm.domain.catalog.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByProductId(String productId);

}
