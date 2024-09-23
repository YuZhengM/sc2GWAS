package com.spring.boot.controller;

import com.spring.boot.pojo.*;
import com.spring.boot.pojo.vo.EnrichmentVO;
import com.spring.boot.service.DetailService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.model.vo.echarts.EchartsData;
import com.spring.boot.util.model.vo.echarts.EchartsGraphData;
import com.spring.boot.util.model.vo.echarts.EchartsPieData;
import com.spring.boot.util.model.vo.plotly.PlotlyClusterData;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Yu Zhengmin
 */
@RequestMapping("/detail")
@CrossOrigin
@RestController
public class DetailController {

    private static final Log log = LogFactory.getLog(DetailController.class);

    private DetailService detailService;

    public DetailController() {
    }

    @Autowired
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/sample/{gse_id}")
    public Result<SingleCell> getSampleInfo(@PathVariable("gse_id") String gseId) {
        SingleCell singleCell = detailService.getSampleInfo(gseId);
        return ResultUtil.success("[getSampleInfo]: 查询成功", singleCell);
    }

    @GetMapping("/cell_type/count/{gse_id}")
    public Result<EchartsPieData<String, String>> getCellTypeCount(@PathVariable("gse_id") String gseId) {
        EchartsPieData<String, String> clusterCellCountList = detailService.getCellTypeCount(gseId);
        return ResultUtil.success("[getCellTypeCount]: 查询成功", clusterCellCountList);
    }

    @GetMapping("/sample/count/{gse_id}")
    public Result<EchartsData<String, Integer>> getSampleCount(@PathVariable("gse_id") String gseId) {
        EchartsData<String, Integer> sampleCountList = detailService.getSampleCount(gseId);
        return ResultUtil.success("[getSampleCount]: 查询成功", sampleCountList);
    }

    @GetMapping("/sample_cell_type/count/{gse_id}")
    public Result<EchartsData<String, String>> getSampleCellTypeCount(@PathVariable("gse_id") String gseId) {
        EchartsData<String, String> echartsData = detailService.getSampleCellTypeCount(gseId);
        return ResultUtil.success("[getSampleCellTypeCount]: 查询成功", echartsData);
    }

    @GetMapping("/gwas_trait_cell/gse/{gse_id}")
    public Result<Map<String, Object>> getGwasTraitCellRelationshipData(@PathVariable("gse_id") String gseId) {
        Map<String, Object> objectMap = detailService.getGwasTraitCellRelationshipData(gseId);
        return ResultUtil.success("[getGwasTraitCellRelationshipData]: 查询成功", objectMap);
    }

    @GetMapping("/gwas_trait_cell/gwas/{gwas_id}")
    public Result<Map<String, Object>> getGwasTraitCellRelationshipDataByGwasId(@PathVariable("gwas_id") String gwasId) {
        Map<String, Object> objectMap = detailService.getGwasTraitCellRelationshipDataByGwasId(gwasId);
        return ResultUtil.success("[getGwasTraitCellRelationshipDataByGwasId]: 查询成功", objectMap);
    }

    @GetMapping("/gwas_gene/graph/gse/{gse_id}")
    public Result<EchartsGraphData> getGwasGeneGraph(@PathVariable("gse_id") String gseId) {
        EchartsGraphData echartsGraphData = detailService.getGwasGeneGraph(gseId);
        return ResultUtil.success("[getGwasGeneGraph]: 查询成功", echartsGraphData);
    }

    @GetMapping("/cluster_coordinate/{gse_id}/{cell_rate}/{type}/{metadata}")
    public Result<PlotlyClusterData<Double, Double>> listClusterCoordinate(@PathVariable("gse_id") String gseId,
                                                                           @PathVariable("cell_rate") Double cellRate,
                                                                           @PathVariable("type") String type,
                                                                           @PathVariable("metadata") String metadata) {
        PlotlyClusterData<Double, Double> plotlyClusterData = detailService.listClusterCoordinate(gseId, cellRate, type, metadata);
        return ResultUtil.success("[listClusterCoordinate]: 查询成功", plotlyClusterData);
    }

    @GetMapping("/scdrs/{gse_id}/{gwas_id}/{cell_rate}/{type}/{value}")
    public Result<PlotlyClusterData<Double, Double>> listScdrsScatterData(@PathVariable("gse_id") String gseId,
                                                                          @PathVariable("gwas_id") String gwasId,
                                                                          @PathVariable("cell_rate") Double cellRate,
                                                                          @PathVariable("type") String type,
                                                                          @PathVariable("value") String value) throws IOException {
        PlotlyClusterData<Double, Double> plotlyClusterData = detailService.listScdrsScatterData(gseId, gwasId, cellRate, type, value);
        return ResultUtil.success("[listClusterCoordinate]: 查询成功", plotlyClusterData);
    }

    @GetMapping("/gwas/list/{gse_id}")
    public Result<List<Gwas>> listGwasByGseId(@PathVariable("gse_id") String gseId) {
        List<Gwas> gwasList = detailService.listGwasByGseId(gseId);
        return ResultUtil.success("[listGwasByGseId]: 查询成功", gwasList);
    }

    @GetMapping("/cluster/{gse_id}")
    public Result<List<ClusterCellCount>> getClusterList(@PathVariable("gse_id") String gseId) {
        List<ClusterCellCount> goKeggList = detailService.getClusterList(gseId);
        return ResultUtil.success("[getClusterList]: 查询信息结果", goKeggList);
    }

    @PostMapping("/enrichment/go")
    public Result<List<Go>> listGoData(@RequestBody EnrichmentVO enrichmentVO) {
        List<Go> goList = detailService.listGoData(enrichmentVO);
        return ResultUtil.success("[listGoData]: 查询信息结果", goList);
    }

    @PostMapping("/enrichment/kegg")
    public Result<List<Kegg>> listKeggData(@RequestBody EnrichmentVO enrichmentVO) {
        List<Kegg> keggList = detailService.listKeggData(enrichmentVO);
        return ResultUtil.success("[listKeggData]: 查询信息结果", keggList);
    }

    @GetMapping("/gene/expression/{gse_id}/{cell_rate}/{gene_name}/{type}")
    public Result<PlotlyClusterData<Double, Double>> getClusterGeneExpression(@PathVariable("gse_id") String gseId,
                                                                              @PathVariable("gene_name") String geneName,
                                                                              @PathVariable("cell_rate") Double cellRate,
                                                                              @PathVariable("type") String type) {
        PlotlyClusterData<Double, Double> plotlyClusterData = detailService.getClusterGeneExpression(gseId, geneName, cellRate, type);
        return ResultUtil.success("[getClusterGeneExpression]: 查询信息结果", plotlyClusterData);
    }

    @GetMapping("/gene/expression/{gse_id}/{cell_rate}/{gene_name}/{type}/h5ad")
    public Result<PlotlyClusterData<Double, Double>> getClusterGeneExpressionByH5ad(@PathVariable("gse_id") String gseId,
                                                                                    @PathVariable("gene_name") String geneName,
                                                                                    @PathVariable("cell_rate") Double cellRate,
                                                                                    @PathVariable("type") String type) throws IOException {
        PlotlyClusterData<Double, Double> plotlyClusterData = detailService.getClusterGeneExpressionByH5ad(gseId, geneName, cellRate, type);
        return ResultUtil.success("[getClusterGeneExpressionByH5ad]: 查询信息结果", plotlyClusterData);
    }

    @GetMapping("/gene/data/{id}/{type}")
    public Result<List<String>> listGeneByIdAndType(@PathVariable("id") String id, @PathVariable("type") String type) {
        List<String> geneList = detailService.listGeneByIdAndType(id, type);
        return ResultUtil.success("[listGeneByIdAndType]: 查询信息结果", geneList);
    }

    @GetMapping("/gwas/{gwas_id}")
    public Result<Gwas> getGwas(@PathVariable("gwas_id") String gwasId) {
        Gwas gwas = detailService.getGwas(gwasId);
        return ResultUtil.success("[getGwas]: 查询信息结果", gwas);
    }

    @GetMapping("/gwas/chr/count/{gwas_id}")
    public Result<EchartsPieData<String, String>> getGwasChrCount(@PathVariable("gwas_id") String gwasId) {
        EchartsPieData<String, String> echartsPieData = detailService.getGwasChrCount(gwasId);
        return ResultUtil.success("[getGwasChrCount]: 查询信息结果", echartsPieData);
    }

    @GetMapping("/gse/list/{gwas_id}")
    public Result<List<String>> listGseByGwasId(@PathVariable("gwas_id") String gwasId) {
        List<String> gseList = detailService.listGseByGwasId(gwasId);
        return ResultUtil.success("[listGseByGwasId]: 查询信息结果", gseList);
    }

    @GetMapping("/gwas/relation/{gwas_id}")
    public Result<EchartsGraphData> getRelationByGwasId(@PathVariable("gwas_id") String gwasId) {
        EchartsGraphData echartsGraphData = detailService.getRelationByGwasId(gwasId);
        return ResultUtil.success("[getRelationByGwasId]: 查询信息结果", echartsGraphData);
    }

    @GetMapping("/gse/relation/{gse_id}")
    public Result<EchartsGraphData> getRelationByGseId(@PathVariable("gse_id") String gseId) {
        EchartsGraphData echartsGraphData = detailService.getRelationByGseId(gseId);
        return ResultUtil.success("[getRelationByGseId]: 查询信息结果", echartsGraphData);
    }

    @GetMapping("/gwas/relation/heatmap/{gwas_id}")
    public Result<EchartsData<String, String>> getRelationHeatmapByGwasId(@PathVariable("gwas_id") String gwasId) throws IOException {
        EchartsData<String, String> echartsData = detailService.getRelationHeatmapByGwasId(gwasId);
        return ResultUtil.success("[getRelationHeatmapByGwasId]: 查询信息结果", echartsData);
    }

    @GetMapping("/gse/relation/heatmap/{gse_id}")
    public Result<EchartsData<String, String>> getRelationHeatmapByGseId(@PathVariable("gse_id") String gseId) {
        EchartsData<String, String> echartsData = detailService.getRelationHeatmapByGseId(gseId);
        return ResultUtil.success("[getRelationHeatmapByGseId]: 查询信息结果", echartsData);
    }

    @GetMapping("/gwas/marker/{gwas_id}/{gse_id}/{p_value}")
    public Result<List<GwasMarker>> listGwasMarkerByGwasIdAndGseId(@PathVariable("gwas_id") String gwasId, @PathVariable("gse_id") String gseId, @PathVariable("p_value") Double pValue) {
        List<GwasMarker> gwasMarkerList = detailService.listGwasMarkerByGwasIdAndGseId(gwasId, gseId, pValue);
        return ResultUtil.success("[listGwasMarkerByGwasIdAndGseId]: 查询信息结果", gwasMarkerList);
    }

    @GetMapping("/gwas/gse/score/{gwas_id}/{gse_id}")
    public Result<List<GwasScore>> listGwasGseScoreByGwasIdAndGseId(@PathVariable("gwas_id") String gwasId, @PathVariable("gse_id") String gseId) {
        List<GwasScore> gwasScoreList = detailService.listGwasGseScoreByGwasIdAndGseId(gwasId, gseId);
        return ResultUtil.success("[listGwasGseScoreByGwasIdAndGseId]: 查询信息结果", gwasScoreList);
    }

    @GetMapping("/gwas/gse/trait_relevant_cell/{gwas_id}/{gse_id}")
    public Result<EchartsData<String, String>> getTraitRelevantCellByGwasIdAndGseId(@PathVariable("gwas_id") String gwasId, @PathVariable("gse_id") String gseId) {
        EchartsData<String, String> echartsData = detailService.getTraitRelevantCellByGwasIdAndGseId(gwasId, gseId);
        return ResultUtil.success("[getTraitRelevantCellByGwasIdAndGseId]: 查询信息结果", echartsData);
    }

    @GetMapping("/gse/trait_relevant_cell/{gse_id}/{cell_categories}")
    public Result<List<CellTypeDifferentiationScore>> listDifferentiationScore(@PathVariable("gse_id") String gseId, @PathVariable("cell_categories") String cellCategories) {
        List<CellTypeDifferentiationScore> cellTypeDifferentiationScoreList = detailService.listDifferentiationScore(gseId, cellCategories);
        return ResultUtil.success("[listDifferentiationScore]: 查询信息结果", cellTypeDifferentiationScoreList);
    }


    @GetMapping("/gwas/trait_relevant_cell/{gwas_id}/{cell_categories}")
    public Result<List<CellTypeDifferentiationScore>> listDifferentiationScoreByGwasId(@PathVariable("gwas_id") String gwasId, @PathVariable("cell_categories") String cellCategories) {
        List<CellTypeDifferentiationScore> cellTypeDifferentiationScoreList = detailService.listDifferentiationScoreByGwasId(gwasId, cellCategories);
        return ResultUtil.success("[listDifferentiationScoreByGwasId]: 查询信息结果", cellTypeDifferentiationScoreList);
    }

}
