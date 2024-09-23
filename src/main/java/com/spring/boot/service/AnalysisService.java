package com.spring.boot.service;

import com.spring.boot.pojo.GwasGeneEnrichment;
import com.spring.boot.pojo.vo.GwasGeneEnrichmentVO;
import com.spring.boot.util.model.vo.echarts.EchartsData;
import com.spring.boot.util.model.vo.echarts.EchartsPieData;

import java.util.List;
import java.util.Map;

public interface AnalysisService {

    Map<String, Object> getGwasGeneEnrichment(GwasGeneEnrichmentVO gwasGeneEnrichmentVO);

}
