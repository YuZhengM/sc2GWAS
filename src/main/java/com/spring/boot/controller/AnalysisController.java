package com.spring.boot.controller;

import com.spring.boot.pojo.vo.GwasGeneEnrichmentVO;
import com.spring.boot.service.AnalysisService;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Yu Zhengmin
 */
@RequestMapping("/analysis")
@CrossOrigin
@RestController
public class AnalysisController {

    private AnalysisService analysisService;

    public AnalysisController() {
    }

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/gwas/gene/enrichment")
    public Result<Map<String, Object>> getGwasGeneEnrichment(@RequestBody GwasGeneEnrichmentVO gwasGeneEnrichmentVO) {
        Map<String, Object> gwasGeneEnrichmentList = analysisService.getGwasGeneEnrichment(gwasGeneEnrichmentVO);
        return ResultUtil.success("success", gwasGeneEnrichmentList);
    }

}
