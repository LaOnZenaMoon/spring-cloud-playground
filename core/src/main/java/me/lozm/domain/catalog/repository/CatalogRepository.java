package me.lozm.domain.catalog.repository;

import me.lozm.domain.catalog.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
}
