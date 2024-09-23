package com.spring.boot.service;

import com.spring.boot.pojo.*;
import com.spring.boot.pojo.vo.EnrichmentVO;
import com.spring.boot.util.model.vo.echarts.EchartsData;
import com.spring.boot.util.model.vo.echarts.EchartsGraphData;
import com.spring.boot.util.model.vo.echarts.EchartsPieData;
import com.spring.boot.util.model.vo.plotly.PlotlyClusterData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Yu Zhengmin
 */
public interface DetailService {

    SingleCell getSampleInfo(String gseId);

    EchartsPieData<String, String> getCellTypeCount(String gseId);

    EchartsData<String, Integer> getSampleCount(String gseId);

    PlotlyClusterData<Double, Double> listClusterCoordinate(String gseId, Double cellRate, String type, String metadata);

    List<Gwas> listGwasByGseId(String gseId);

    List<ClusterCellCount> getClusterList(String gseId);

    List<Go> listGoData(EnrichmentVO enrichmentVO);

    List<Kegg> listKeggData(EnrichmentVO enrichmentVO);

    List<String> listGeneByGseId(String gseId);

    PlotlyClusterData<Double, Double> getClusterGeneExpression(String gseId, String geneName, Double cellRate, String type);

    PlotlyClusterData<Double, Double> listScdrsScatterData(String gseId, String gwasId, Double cellRate, String type, String value) throws IOException;

    PlotlyClusterData<Double, Double> getClusterGeneExpressionByH5ad(String gseId, String geneName, Double cellRate, String type) throws IOException;

    Gwas getGwas(String gwasId);

    EchartsPieData<String, String> getGwasChrCount(String gwasId);

    List<String> listGseByGwasId(String gwasId);

    EchartsGraphData getRelationByGwasId(String gwasId);

    EchartsGraphData getRelationByGseId(String gseId);

    List<GwasMarker> listGwasMarkerByGwasIdAndGseId(String gwasId, String gseId, Double pValue);

    EchartsData<String, String> getRelationHeatmapByGwasId(String gwasId);

    EchartsData<String, String> getRelationHeatmapByGseId(String gseId);

    List<String> listGeneByIdAndType(String gseId, String type);

    List<GwasScore> listGwasGseScoreByGwasIdAndGseId(String gwasId, String gseId);

    EchartsData<String, String> getSampleCellTypeCount(String gseId);

    EchartsGraphData getGwasGeneGraph(String gseId);

    EchartsData<String, String> getTraitRelevantCellByGwasIdAndGseId(String gwasId, String gseId);

    Map<String, Object> getGwasTraitCellRelationshipData(String gseId);

    Map<String, Object> getGwasTraitCellRelationshipDataByGwasId(String gwasId);

    List<CellTypeDifferentiationScore> listDifferentiationScore(String gseId, String cellCategories);

    List<CellTypeDifferentiationScore> listDifferentiationScoreByGwasId(String gwasId, String cellCategories);
}
