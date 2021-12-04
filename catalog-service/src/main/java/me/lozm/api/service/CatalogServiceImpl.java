package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import me.lozm.domain.catalog.repository.CatalogRepository;
import me.lozm.domain.catalog.vo.CatalogInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;


    @Override
    public List<CatalogInfoVo> getCatalogList() {
        return catalogRepository.findAll()
                .stream()
                .map(catalog -> mapStrictly(catalog, CatalogInfoVo.class))
                .collect(toList());
    }

}
