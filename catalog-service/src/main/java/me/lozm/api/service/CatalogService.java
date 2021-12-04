package me.lozm.api.service;

import me.lozm.domain.catalog.vo.CatalogInfoVo;

import java.util.List;

public interface CatalogService {

    List<CatalogInfoVo> getCatalogList();

}
