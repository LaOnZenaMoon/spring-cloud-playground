package me.lozm.api.controller;

import lombok.RequiredArgsConstructor;
import me.lozm.api.service.CatalogService;
import me.lozm.domain.catalog.dto.CatalogInfoResponseDto;
import me.lozm.domain.catalog.vo.CatalogInfoVo;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final Environment environment;
    private final CatalogService catalogService;


    @GetMapping("health-check")
    public String healthCheck() {
        return String.format("Catalog service is available on PORT %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("catalogs")
    public ResponseEntity<List<CatalogInfoResponseDto>> getCatalogList() {
        List<CatalogInfoVo> catalogList = catalogService.getCatalogList();

        List<CatalogInfoResponseDto> responseList = catalogList.stream()
                .map(catalogInfoVo -> mapStrictly(catalogInfoVo, CatalogInfoResponseDto.class))
                .collect(toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

}
