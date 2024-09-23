package com.spring.boot.service;

import com.spring.boot.pojo.*;
import com.spring.boot.util.model.vo.echarts.EchartsGraphData;

import java.util.List;

public interface GeneDetailService {
    GeneInfo getGeneInfo(String gene);

    List<GeneDiseaseAssociation> getGenePhenotypeInfo(String gene);

    List<GwasGseMarker> getGenePhenotypeSampleInfo(String gene);

    List<CommonSnp> listGeneInteractiveCommonSnp(String gene);

    List<Crispr> listGeneInteractiveCrispr(String gene);

    List<Enhancer> listGeneInteractiveEnhancer(String gene);

    List<Eqtl> listGeneInteractiveEqtl(String gene);

    List<RiskSnp> listGeneInteractiveRiskSnp(String gene);

    List<RnaInteraction> listGeneInteractiveRnaInteraction(String gene);

    List<SuperEnhancerDbsuper> listGeneInteractiveSuperEnhancerDbsuper(String gene);

    List<SuperEnhancerSeav3> listGeneInteractiveSuperEnhancerSeav3(String gene);

    List<SuperEnhancerSedbv2> listGeneInteractiveSuperEnhancerSedbv2(String gene);

    List<Tfbs> listGeneInteractiveTfbs(String gene);

    EchartsGraphData getGwasGeneGraph(String gene);
}
