package com.spring.boot.controller;

import com.spring.boot.pojo.*;
import com.spring.boot.service.GeneDetailService;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.model.vo.echarts.EchartsGraphData;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/gene/detail")
@CrossOrigin
@RestController
public class GeneDetailController {

    private GeneDetailService detailService;


    public GeneDetailController() {
    }

    @Autowired
    public GeneDetailController(GeneDetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/gene/{gene}")
    public Result<GeneInfo> getGeneInfo(@PathVariable String gene) {
        GeneInfo geneInfo = detailService.getGeneInfo(gene);
        return ResultUtil.success("[getGeneInfo]: success", geneInfo);
    }

    @GetMapping("/gene/phenotype/{gene}")
    public Result<List<GeneDiseaseAssociation>> getGenePhenotypeInfo(@PathVariable String gene) {
        List<GeneDiseaseAssociation> geneDiseaseAssociationList = detailService.getGenePhenotypeInfo(gene);
        return ResultUtil.success("[getGenePhenotypeInfo]: success", geneDiseaseAssociationList);
    }

    @GetMapping("/gene/phenotype/sample/{gene}")
    public Result<List<GwasGseMarker>> getGenePhenotypeSampleInfo(@PathVariable String gene) {
        List<GwasGseMarker> gwasGseMarkerList = detailService.getGenePhenotypeSampleInfo(gene);
        return ResultUtil.success("[getGenePhenotypeSampleInfo]: success", gwasGseMarkerList);
    }

    @GetMapping("/gene/interactive/common_snp/{gene}")
    public Result<List<CommonSnp>> listGeneInteractiveCommonSnp(@PathVariable String gene) {
        List<CommonSnp> commonSnpList = detailService.listGeneInteractiveCommonSnp(gene);
        return ResultUtil.success("[listGeneInteractiveCommonSnp]: success", commonSnpList);
    }

    @GetMapping("/gene/interactive/crispr/{gene}")
    public Result<List<Crispr>> listGeneInteractiveCrispr(@PathVariable String gene) {
        List<Crispr> crisprList = detailService.listGeneInteractiveCrispr(gene);
        return ResultUtil.success("[listGeneInteractiveCrispr]: success", crisprList);
    }

    @GetMapping("/gene/interactive/enhancer/{gene}")
    public Result<List<Enhancer>> listGeneInteractiveEnhancer(@PathVariable String gene) {
        List<Enhancer> enhancerList = detailService.listGeneInteractiveEnhancer(gene);
        return ResultUtil.success("[listGeneInteractiveEnhancer]: success", enhancerList);
    }

    @GetMapping("/gene/interactive/eqtl/{gene}")
    public Result<List<Eqtl>> listGeneInteractiveEqtl(@PathVariable String gene) {
        List<Eqtl> eqtlList = detailService.listGeneInteractiveEqtl(gene);
        return ResultUtil.success("[listGeneInteractiveEqtl]: success", eqtlList);
    }

    @GetMapping("/gene/interactive/risk_snp/{gene}")
    public Result<List<RiskSnp>> listGeneInteractiveRiskSnp(@PathVariable String gene) {
        List<RiskSnp> riskSnpList = detailService.listGeneInteractiveRiskSnp(gene);
        return ResultUtil.success("[listGeneInteractiveRiskSnp]: success", riskSnpList);
    }

    @GetMapping("/gene/interactive/rna_interaction/{gene}")
    public Result<List<RnaInteraction>> listGeneInteractiveRnaInteraction(@PathVariable String gene) {
        List<RnaInteraction> rnaInteractionList = detailService.listGeneInteractiveRnaInteraction(gene);
        return ResultUtil.success("[listGeneInteractiveRnaInteraction]: success", rnaInteractionList);
    }

    @GetMapping("/gene/interactive/super_enhancer_dbsuper/{gene}")
    public Result<List<SuperEnhancerDbsuper>> listGeneInteractiveSuperEnhancerDbsuper(@PathVariable String gene) {
        List<SuperEnhancerDbsuper> superEnhancerDbsuperList = detailService.listGeneInteractiveSuperEnhancerDbsuper(gene);
        return ResultUtil.success("[listGeneInteractiveSuperEnhancerDbsuper]: success", superEnhancerDbsuperList);
    }

    @GetMapping("/gene/interactive/super_enhancer_seav3/{gene}")
    public Result<List<SuperEnhancerSeav3>> listGeneInteractiveSuperEnhancerSeav3(@PathVariable String gene) {
        List<SuperEnhancerSeav3> superEnhancerSeav3List = detailService.listGeneInteractiveSuperEnhancerSeav3(gene);
        return ResultUtil.success("[listGeneInteractiveSuperEnhancerSeav3]: success", superEnhancerSeav3List);
    }

    @GetMapping("/gene/interactive/super_enhancer_sedbv2/{gene}")
    public Result<List<SuperEnhancerSedbv2>> listGeneInteractiveSuperEnhancerSedbv2(@PathVariable String gene) {
        List<SuperEnhancerSedbv2> superEnhancerSedbv2List = detailService.listGeneInteractiveSuperEnhancerSedbv2(gene);
        return ResultUtil.success("[listGeneInteractiveSuperEnhancerSedbv2]: success", superEnhancerSedbv2List);
    }

    @GetMapping("/gene/interactive/tfbs/{gene}")
    public Result<List<Tfbs>> listGeneInteractiveTfbs(@PathVariable String gene) {
        List<Tfbs> tfbsList = detailService.listGeneInteractiveTfbs(gene);
        return ResultUtil.success("[listGeneInteractiveTfbs]: success", tfbsList);
    }

    @GetMapping("/gwas_gene/graph/gene/{gene}")
    public Result<EchartsGraphData> getGwasGeneGraph(@PathVariable String gene) {
        EchartsGraphData echartsGraphData = detailService.getGwasGeneGraph(gene);
        return ResultUtil.success("[getGwasGeneGraph]: success", echartsGraphData);
    }

}
