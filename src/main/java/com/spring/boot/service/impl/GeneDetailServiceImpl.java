package com.spring.boot.service.impl;

import com.google.common.collect.Lists;
import com.spring.boot.mapper.*;
import com.spring.boot.pojo.*;
import com.spring.boot.service.GeneDetailService;
import com.spring.boot.util.model.vo.echarts.EchartsCategories;
import com.spring.boot.util.model.vo.echarts.EchartsGraphData;
import com.spring.boot.util.model.vo.echarts.EchartsLink;
import com.spring.boot.util.model.vo.echarts.EchartsNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
@CacheConfig(cacheNames = "gene_detail", keyGenerator = "cacheKeyGenerator")
@Service
public class GeneDetailServiceImpl implements GeneDetailService {

    private GeneInfoMapper geneInfoMapper;
    private GeneDiseaseAssociationMapper geneDiseaseAssociationMapper;
    private GwasGseMarkerMapper gwasGseMarkerMapper;
    private CommonSnpMapper commonSnpMapper;
    private CrisprMapper crisprMapper;
    private EnhancerMapper enhancerMapper;
    private EqtlMapper eqtlMapper;
    private RiskSnpMapper riskSnpMapper;
    private RnaInteractionMapper rnaInteractionMapper;
    private SuperEnhancerDbsuperMapper superEnhancerDbsuperMapper;
    private SuperEnhancerSeav3Mapper superEnhancerSeav3Mapper;
    private SuperEnhancerSedbv2Mapper superEnhancerSedbv2Mapper;
    private TfbsMapper tfbsMapper;

    public GeneDetailServiceImpl() {
    }

    @Autowired
    public GeneDetailServiceImpl(GeneInfoMapper geneInfoMapper, GeneDiseaseAssociationMapper geneDiseaseAssociationMapper, GwasGseMarkerMapper gwasGseMarkerMapper, CommonSnpMapper commonSnpMapper, CrisprMapper crisprMapper, EnhancerMapper enhancerMapper, EqtlMapper eqtlMapper, RiskSnpMapper riskSnpMapper, RnaInteractionMapper rnaInteractionMapper, SuperEnhancerDbsuperMapper superEnhancerDbsuperMapper, SuperEnhancerSeav3Mapper superEnhancerSeav3Mapper, SuperEnhancerSedbv2Mapper superEnhancerSedbv2Mapper, TfbsMapper tfbsMapper) {
        this.geneInfoMapper = geneInfoMapper;
        this.geneDiseaseAssociationMapper = geneDiseaseAssociationMapper;
        this.gwasGseMarkerMapper = gwasGseMarkerMapper;
        this.commonSnpMapper = commonSnpMapper;
        this.crisprMapper = crisprMapper;
        this.enhancerMapper = enhancerMapper;
        this.eqtlMapper = eqtlMapper;
        this.riskSnpMapper = riskSnpMapper;
        this.rnaInteractionMapper = rnaInteractionMapper;
        this.superEnhancerDbsuperMapper = superEnhancerDbsuperMapper;
        this.superEnhancerSeav3Mapper = superEnhancerSeav3Mapper;
        this.superEnhancerSedbv2Mapper = superEnhancerSedbv2Mapper;
        this.tfbsMapper = tfbsMapper;
    }

    @Cacheable
    @Override
    public GeneInfo getGeneInfo(String gene) {
        return geneInfoMapper.selectByGene(gene);
    }

    @Cacheable
    @Override
    public List<GeneDiseaseAssociation> getGenePhenotypeInfo(String gene) {
        return geneDiseaseAssociationMapper.selectByGene(gene);
    }

    @Cacheable
    @Override
    public List<GwasGseMarker> getGenePhenotypeSampleInfo(String gene) {
        return gwasGseMarkerMapper.selectByGene(gene);
    }

    @Cacheable
    @Override
    public List<CommonSnp> listGeneInteractiveCommonSnp(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return commonSnpMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<Crispr> listGeneInteractiveCrispr(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return crisprMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<Enhancer> listGeneInteractiveEnhancer(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return enhancerMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<Eqtl> listGeneInteractiveEqtl(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return eqtlMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<RiskSnp> listGeneInteractiveRiskSnp(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return riskSnpMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<RnaInteraction> listGeneInteractiveRnaInteraction(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return rnaInteractionMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<SuperEnhancerDbsuper> listGeneInteractiveSuperEnhancerDbsuper(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return superEnhancerDbsuperMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<SuperEnhancerSeav3> listGeneInteractiveSuperEnhancerSeav3(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return superEnhancerSeav3Mapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<SuperEnhancerSedbv2> listGeneInteractiveSuperEnhancerSedbv2(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return superEnhancerSedbv2Mapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public List<Tfbs> listGeneInteractiveTfbs(String gene) {
        GeneInfo geneInfo = getGeneInfo(gene);
        return tfbsMapper.selectByChrAndStartAndEnd(geneInfo.getChr(), geneInfo.getStart(), geneInfo.getEnd());
    }

    @Cacheable
    @Override
    public EchartsGraphData getGwasGeneGraph(String gene) {
        List<GwasGseMarker> gwasGseMarkerList = gwasGseMarkerMapper.selectTopByGene(gene);

        List<EchartsNode> echartsNodeList = Lists.newArrayListWithCapacity(gwasGseMarkerList.size() + 1);
        List<EchartsLink> echartsLinkList = Lists.newArrayListWithCapacity(gwasGseMarkerList.size());

        List<EchartsCategories> categoriesList = Lists.newArrayListWithCapacity(2);
        categoriesList.add(EchartsCategories.builder().name("Gene").build());
        categoriesList.add(EchartsCategories.builder().name("GWAS").build());

        EchartsNode echartsNode;
        // 添加性状结点
        echartsNode = new EchartsNode();
        echartsNode.setId(gene);
        echartsNode.setName(gene);
        echartsNode.setCategory("Gene");
        echartsNode.setSymbolSize(27);
        echartsNodeList.add(echartsNode);

        for (GwasGseMarker gwasGseMarker : gwasGseMarkerList) {
            // 添加性状结点
            echartsNode = new EchartsNode();
            echartsNode.setId(gwasGseMarker.getGwasId() + gwasGseMarker.getScore());
            echartsNode.setName(gwasGseMarker.getGwasId());
            echartsNode.setSymbolSize(22);
            echartsNode.setCategory("GWAS");
            echartsNodeList.add(echartsNode);
            // 添加边的信息
            EchartsLink echartsLink = new EchartsLink();
            echartsLink.setSource(gene);
            echartsLink.setTarget(gwasGseMarker.getGwasId() + gwasGseMarker.getScore());
            echartsLink.setValue(1);
            echartsLinkList.add(echartsLink);
        }

        return EchartsGraphData.builder().nodes(echartsNodeList).links(echartsLinkList).categories(categoriesList).build();
    }

}
