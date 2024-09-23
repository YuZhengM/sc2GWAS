package com.spring.boot.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spring.boot.config.bean.ExecLinux;
import com.spring.boot.config.bean.Path;
import com.spring.boot.mapper.*;
import com.spring.boot.pojo.*;
import com.spring.boot.pojo.vo.EnrichmentVO;
import com.spring.boot.service.DetailService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.vo.echarts.*;
import com.spring.boot.util.model.vo.plotly.PlotlyClusterData;
import com.spring.boot.util.model.vo.plotly.PlotlyData;
import com.spring.boot.util.util.ListUtil;
import com.spring.boot.util.util.NullUtil;
import com.spring.boot.util.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.spring.boot.util.util.ApplicationUtil.*;
import static com.spring.boot.util.util.ApplicationUtil.listDoubleByPythonResult;

/**
 * @author Yu Zhengmin
 */
@CacheConfig(cacheNames = "detail", keyGenerator = "cacheKeyGenerator")
@Service
public class DetailServiceImpl implements DetailService {

    private static final Log log = LogFactory.getLog(DetailServiceImpl.class);

    private GwasGseMarkerMapper gwasGseMarkerMapper;
    private GwasMapper gwasMapper;
    private SingleCellMapper singleCellMapper;
    private ClusterCellCountMapper clusterCellCountMapper;
    private SampleCountMapper sampleCountMapper;
    private ClusterAnnoMapper clusterAnnoMapper;
    private GwasGseMapper gwasGseMapper;
    private GoMapper goMapper;
    private KeggMapper keggMapper;
    private GeneMapper geneMapper;
    private Path path;
    private GwasChrCountsMapper gwasChrCountsMapper;
    private ExecLinux execLinux;
    private GwasMarkerMapper gwasMarkerMapper;
    private FdrGseGwasMapper fdrGseGwasMapper;
    private GwasScoreMapper gwasScoreMapper;
    private CellTypeDifferentiationScoreMapper cellTypeDifferentiationScoreMapper;

    public DetailServiceImpl() {
    }

    @Autowired
    public DetailServiceImpl(GwasMapper gwasMapper, SingleCellMapper singleCellMapper, ClusterCellCountMapper clusterCellCountMapper, SampleCountMapper sampleCountMapper, ClusterAnnoMapper clusterAnnoMapper, GwasGseMapper gwasGseMapper, GoMapper goMapper, KeggMapper keggMapper, GeneMapper geneMapper, Path path, GwasChrCountsMapper gwasChrCountsMapper, ExecLinux execLinux, GwasMarkerMapper gwasMarkerMapper, FdrGseGwasMapper fdrGseGwasMapper, GwasScoreMapper gwasScoreMapper, @Qualifier("gwasGseMarkerMapper") GwasGseMarkerMapper gwasGseMarkerMapper, CellTypeDifferentiationScoreMapper cellTypeDifferentiationScoreMapper) {
        this.gwasMapper = gwasMapper;
        this.singleCellMapper = singleCellMapper;
        this.clusterCellCountMapper = clusterCellCountMapper;
        this.sampleCountMapper = sampleCountMapper;
        this.clusterAnnoMapper = clusterAnnoMapper;
        this.gwasGseMapper = gwasGseMapper;
        this.goMapper = goMapper;
        this.keggMapper = keggMapper;
        this.geneMapper = geneMapper;
        this.path = path;
        this.gwasChrCountsMapper = gwasChrCountsMapper;
        this.execLinux = execLinux;
        this.gwasMarkerMapper = gwasMarkerMapper;
        this.fdrGseGwasMapper = fdrGseGwasMapper;
        this.gwasScoreMapper = gwasScoreMapper;
        this.gwasGseMarkerMapper = gwasGseMarkerMapper;
        this.cellTypeDifferentiationScoreMapper = cellTypeDifferentiationScoreMapper;
    }

    @Cacheable
    @Override
    public SingleCell getSampleInfo(String gseId) {
        return singleCellMapper.selectByGseId(gseId);
    }

    @Cacheable
    @Override
    public EchartsPieData<String, String> getCellTypeCount(String gseId) {
        List<ClusterCellCount> clusterCellCountList = clusterCellCountMapper.searchByGseId(gseId);
        return getCellTypeCountEchartsData(clusterCellCountList);
    }

    @Cacheable
    @Override
    public EchartsData<String, Integer> getSampleCount(String gseId) {
        List<SampleCount> sampleCountList = sampleCountMapper.searchByGseId(gseId);
        return getSampleEchartsData(sampleCountList);
    }

    @Cacheable
    @Override
    public PlotlyClusterData<Double, Double> listClusterCoordinate(String gseId, Double cellRate, String type, String metadata) {

        List<ClusterCellCount> clusterCellList = clusterCellCountMapper.searchByGseId(gseId);

        List<ClusterAnno> clusterAnnoList = Lists.newArrayListWithExpectedSize(64);

        if (StringUtil.isEqual(metadata, "cluster")) {
            // 聚类信息
            List<PlotlyData<Double, Double>> plotlyDataList = Lists.newArrayListWithCapacity(clusterCellList.size());
            List<Double> xList;
            List<Double> yList;
            List<String> textList;
            for (ClusterCellCount clusterCell : clusterCellList) {
                int cellNumber = getRandomCellNumber(clusterCell.getCount(), cellRate);
                clusterAnnoList = clusterAnnoMapper.selectBySampleIdAndCellTypeAndNumber(gseId, clusterCell.getCluster(), cellNumber);
                xList = Lists.newArrayListWithExpectedSize(64);
                yList = Lists.newArrayListWithExpectedSize(64);
                textList = Lists.newArrayListWithExpectedSize(64);
                for (ClusterAnno clusterAnno : clusterAnnoList) {
                    // 添加坐标
                    if (StringUtil.isEqual(type, "umap")) {
                        xList.add(clusterAnno.getUmap1());
                        yList.add(clusterAnno.getUmap2());
                    } else if (StringUtil.isEqual(type, "tsne")) {
                        xList.add(clusterAnno.getTsne1());
                        yList.add(clusterAnno.getTsne2());
                    }
                    textList.add(clusterAnno.getCluster() + ":" + clusterAnno.getCellType());
                }
                plotlyDataList.add(PlotlyData.<Double, Double>builder().x(xList).y(yList).name(clusterCell.getCluster() + ":" + clusterCell.getCellType()).text(textList).build());
            }
            return PlotlyClusterData.<Double, Double>builder().plotlyDataList(plotlyDataList).build();
        } else if (StringUtil.isEqual(metadata, "sample")) {
            for (ClusterCellCount clusterCell : clusterCellList) {
                int cellNumber = getRandomCellNumber(clusterCell.getCount(), cellRate);
                List<ClusterAnno> subClusterAnnoList = clusterAnnoMapper.selectBySampleIdAndCellTypeAndNumber(gseId, clusterCell.getCluster(), cellNumber);
                clusterAnnoList.addAll(subClusterAnnoList);
            }

            List<SampleCount> sampleCountList = sampleCountMapper.searchByGseId(gseId);
            // 聚类信息
            List<PlotlyData<Double, Double>> plotlyDataList = Lists.newArrayListWithCapacity(sampleCountList.size());
            List<Double> xList;
            List<Double> yList;
            List<String> textList;
            for (SampleCount sampleCount : sampleCountList) {

                List<ClusterAnno> sampleClusterAnnoList = clusterAnnoList.stream().filter((clusterAnno) -> StringUtil.isEqual(clusterAnno.getGsmId(), sampleCount.getGsmId())).toList();

                xList = Lists.newArrayListWithExpectedSize(64);
                yList = Lists.newArrayListWithExpectedSize(64);
                textList = Lists.newArrayListWithExpectedSize(64);
                for (ClusterAnno clusterAnno : sampleClusterAnnoList) {
                    // 添加坐标
                    if (StringUtil.isEqual(type, "umap")) {
                        xList.add(clusterAnno.getUmap1());
                        yList.add(clusterAnno.getUmap2());
                    } else if (StringUtil.isEqual(type, "tsne")) {
                        xList.add(clusterAnno.getTsne1());
                        yList.add(clusterAnno.getTsne2());
                    }
                    textList.add(clusterAnno.getGsmId());
                }
                plotlyDataList.add(PlotlyData.<Double, Double>builder().x(xList).y(yList).name(sampleCount.getGsmId()).text(textList).build());

                clusterAnnoList.removeAll(sampleClusterAnnoList);
            }
            return PlotlyClusterData.<Double, Double>builder().plotlyDataList(plotlyDataList).build();
        } else {
            List<String> cellTypeList = clusterAnnoMapper.searchCellTypeByGseId(gseId);
            clusterAnnoList = clusterAnnoMapper.searchByGseId(gseId);
            // 聚类信息
            List<PlotlyData<Double, Double>> plotlyDataList = Lists.newArrayListWithCapacity(cellTypeList.size());
            List<Double> xList;
            List<Double> yList;
            List<String> textList;
            for (String cellType : cellTypeList) {

                List<ClusterAnno> sampleClusterAnnoList = clusterAnnoList.stream().filter((clusterAnno) -> StringUtil.isEqual(clusterAnno.getCellType(), cellType)).toList();
                xList = Lists.newArrayListWithExpectedSize(64);
                yList = Lists.newArrayListWithExpectedSize(64);
                textList = Lists.newArrayListWithExpectedSize(64);
                for (ClusterAnno clusterAnno : sampleClusterAnnoList) {
                    // 添加坐标
                    if (StringUtil.isEqual(type, "umap")) {
                        xList.add(clusterAnno.getUmap1());
                        yList.add(clusterAnno.getUmap2());
                    } else if (StringUtil.isEqual(type, "tsne")) {
                        xList.add(clusterAnno.getTsne1());
                        yList.add(clusterAnno.getTsne2());
                    }
                    textList.add(clusterAnno.getCellType());
                }
                plotlyDataList.add(PlotlyData.<Double, Double>builder().x(xList).y(yList).name(cellType).text(textList).build());

                clusterAnnoList.removeAll(sampleClusterAnnoList);
            }
            return PlotlyClusterData.<Double, Double>builder().plotlyDataList(plotlyDataList).build();
        }

    }

    @Cacheable
    @Override
    public List<Gwas> listGwasByGseId(String gseId) {
        List<String> gwasIdList = gwasGseMapper.selectGwasByGseId(gseId);
        return ListUtil.isEmpty(gwasIdList) ? NullUtil.listEmpty() : gwasMapper.selectByGwasIdList(gwasIdList);
    }

    @Cacheable
    @Override
    public List<ClusterCellCount> getClusterList(String gseId) {
        return clusterCellCountMapper.searchByGseId(gseId);
    }

    @Cacheable
    @Override
    public List<Go> listGoData(EnrichmentVO enrichmentVO) {
        List<Go> goList = goMapper.searchBySampleIdAndClusterAndP(enrichmentVO);
        return goList.stream().sorted(Comparator.comparing(Go::getPAdjust).reversed()).collect(Collectors.toList());
    }

    @Cacheable
    @Override
    public List<Kegg> listKeggData(EnrichmentVO enrichmentVO) {
        List<Kegg> keggList = keggMapper.searchBySampleIdAndClusterAndP(enrichmentVO);
        return keggList.stream().sorted(Comparator.comparing(Kegg::getPAdjust).reversed()).collect(Collectors.toList());
    }

    @Cacheable
    @Override
    public List<String> listGeneByGseId(String gseId) {
        return geneMapper.searchGeneByGseId(gseId);
    }

    @Cacheable
    @Override
    public PlotlyClusterData<Double, Double> getClusterGeneExpression(String gseId, String geneName, Double cellRate, String type) {
        SingleCell singleCell = getSampleInfo(gseId);
        List<ClusterAnno> clusterAnnoListAll = Lists.newArrayListWithExpectedSize(64);

        List<ClusterCellCount> clusterCellList = clusterCellCountMapper.searchByGseId(gseId);
        for (ClusterCellCount clusterCell : clusterCellList) {
            int cellNumber = getRandomCellNumber(clusterCell.getCount(), cellRate);
            List<ClusterAnno> clusterAnnoList = clusterAnnoMapper.selectBySampleIdAndCellTypeAndNumber(gseId, clusterCell.getCluster(), cellNumber);
            clusterAnnoListAll.addAll(clusterAnnoList);
        }
        return getTypeExpressionInfo(singleCell, path, clusterAnnoListAll, geneName, type);
    }

    @Cacheable
    @Override
    public PlotlyClusterData<Double, Double> listScdrsScatterData(String gseId, String gwasId, Double cellRate, String type, String value) throws IOException {
        String workPath = path.getWorkPath();

        String h5ad_file = workPath + "/scdrs/data/" + gseId + "_adata.h5ad";
        String python_file = workPath + "/scdrs/code/get_score.py";
        String exec = "python3 " + python_file + " " + h5ad_file + " " + gwasId + " " + cellRate + " " + type + " " + value;

        log.info("Exec linux: {}", exec);

        // exec python get result
        List<String> results = execLinux.execCommand(exec);
        List<String> barcodesList = listStringByPythonResult(results.get(0));
        List<String> cellTypeList = listStringByPythonResult(results.get(1));
        List<Integer> clusterList = listIntegerByPythonResult(results.get(2));
        List<Double> umap1s = listDoubleByPythonResult(results.get(3));
        List<Double> umap2s = listDoubleByPythonResult(results.get(4));
        List<Double> values = listDoubleByPythonResult(results.get(5));

        // 添加图信息
        List<PlotlyData<Double, Double>> plotlyDataList = Lists.newArrayListWithCapacity(1);
        List<String> textList = IntStream.range(0, barcodesList.size()).mapToObj(i -> cellTypeList.get(i) + "(" + clusterList.get(i) + ")" + "-" + barcodesList.get(i)).collect(Collectors.toList());
        List<String> extraTextList = IntStream.range(0, barcodesList.size()).mapToObj(i -> cellTypeList.get(i) + "(" + clusterList.get(i) + ")").collect(Collectors.toList());
        List<String> extraTextList1 = IntStream.range(0, barcodesList.size()).mapToObj(cellTypeList::get).collect(Collectors.toList());
        plotlyDataList.add(PlotlyData.<Double, Double>builder().x(umap1s).y(umap2s).color(values).name(gwasId).text(textList).extraText(extraTextList).extraText1(extraTextList1).build());
        return PlotlyClusterData.<Double, Double>builder().plotlyDataList(plotlyDataList).build();
    }

    @Cacheable
    @Override
    public PlotlyClusterData<Double, Double> getClusterGeneExpressionByH5ad(String gseId, String geneName, Double cellRate, String type) throws IOException {
        String workPath = path.getWorkPath();

        String h5ad_file = workPath + "/gene/data/" + gseId + "_index.h5ad";
        String python_file = workPath + "/gene/code/get_gene.py";
        String exec = "python3 " + python_file + " " + h5ad_file + " " + geneName + " " + cellRate + " " + type;

        // exec python get result
        List<String> results = execLinux.execCommand(exec);
        List<String> barcodesList = listStringByPythonResult(results.get(0));
        List<String> cellTypeList = listStringByPythonResult(results.get(1));
        List<Integer> clusterList = listIntegerByPythonResult(results.get(2));
        List<Double> umap1s = listDoubleByPythonResult(results.get(3));
        List<Double> umap2s = listDoubleByPythonResult(results.get(4));
        List<Double> values = listDoubleByPythonResult(results.get(5));

        // 添加图信息
        List<PlotlyData<Double, Double>> plotlyDataList = Lists.newArrayListWithCapacity(1);
        List<String> textList = IntStream.range(0, barcodesList.size()).mapToObj(i -> cellTypeList.get(i) + "(" + clusterList.get(i) + ")" + "-" + barcodesList.get(i)).collect(Collectors.toList());
        List<String> extraTextList = IntStream.range(0, barcodesList.size()).mapToObj(i -> cellTypeList.get(i) + "(" + clusterList.get(i) + ")").collect(Collectors.toList());
        plotlyDataList.add(PlotlyData.<Double, Double>builder().x(umap1s).y(umap2s).color(values).name(geneName).text(textList).extraText(extraTextList).build());
        return PlotlyClusterData.<Double, Double>builder().plotlyDataList(plotlyDataList).build();
    }

    @Cacheable
    @Override
    public Gwas getGwas(String gwasId) {
        return gwasMapper.selectByGwasId(gwasId);
    }

    @Cacheable
    @Override
    public EchartsPieData<String, String> getGwasChrCount(String gwasId) {
        List<GwasChrCounts> gwasChrCountsList = gwasChrCountsMapper.selectByGwasId(gwasId);
        return getChrCountEchartsData(gwasChrCountsList);
    }

    @Cacheable
    @Override
    public List<String> listGseByGwasId(String gwasId) {
        return gwasGseMapper.selectGseByGwasId(gwasId);
    }

    @Cacheable
    @Override
    public EchartsGraphData getRelationByGwasId(String gwasId) {
        Gwas anchorGwas = gwasMapper.selectByGwasId(gwasId);
        String subChapterLevel = anchorGwas.getSubChapterLevel();
        List<Gwas> gwasList = gwasMapper.selectBySubChapterLevel(subChapterLevel);

        List<EchartsNode> echartsNodeList = Lists.newArrayListWithCapacity(16);
        List<EchartsLink> echartsLinkList = Lists.newArrayListWithExpectedSize(32);

        List<String> nodeIdList = new ArrayList<>();

        EchartsNode echartsNode;

        // 获取所有有关此性状的其他同属类性状信息
        for (Gwas gwas : gwasList) {
            String gwasNodeId = gwas.getTrait() + "(" + gwas.getGwasId() + ")";
            // 添加性状结点
            echartsNode = new EchartsNode();
            echartsNode.setName(gwasNodeId);
            echartsNodeList.add(echartsNode);
            nodeIdList.add(gwasNodeId);
            // 从每个性状中获取相应所属的单细胞数据
            List<String> gseIdList = listGseByGwasId(gwasId);

            for (String gseId : gseIdList) {

                // 获取单细胞信息
                SingleCell singleCell = singleCellMapper.selectByGseId(gseId);
                String nodeId = singleCell.getTissueType() + "(" + singleCell.getGseId() + ")";
                // 添加单细胞结点
                if (!nodeIdList.contains(nodeId)) {
                    echartsNode = new EchartsNode();
                    echartsNode.setName(nodeId);
                    echartsNodeList.add(echartsNode);
                    nodeIdList.add(nodeId);
                }

                // 添加边的信息
                EchartsLink echartsLink = new EchartsLink();
                echartsLink.setSource(gwasNodeId);
                echartsLink.setTarget(nodeId);
                echartsLink.setValue(1);
                echartsLinkList.add(echartsLink);
            }

        }

        return EchartsGraphData.builder().nodes(echartsNodeList).links(echartsLinkList).build();
    }

    @Cacheable
    @Override
    public EchartsGraphData getRelationByGseId(String gseId) {

        SingleCell anchorSingleCell = singleCellMapper.selectByGseId(gseId);
        String disease = anchorSingleCell.getDisease();
        List<SingleCell> singleCellList = singleCellMapper.selectByDisease(disease);

        List<EchartsNode> echartsNodeList = Lists.newArrayListWithCapacity(16);
        List<EchartsLink> echartsLinkList = Lists.newArrayListWithExpectedSize(32);

        List<String> nodeIdList = new ArrayList<>();

        EchartsNode echartsNode;

        // 获取所有有关此性状的其他同属类性状信息
        for (SingleCell singleCell : singleCellList) {
            // 添加性状结点
            echartsNode = new EchartsNode();
            String nodeId = singleCell.getTissueType() + "(" + singleCell.getGseId() + ")";
            echartsNode.setName(nodeId);
            echartsNodeList.add(echartsNode);
            nodeIdList.add(nodeId);
            // 从每个性状中获取相应所属的单细胞数据
            List<String> gwasIdList = gwasGseMapper.selectGwasByGseId(gseId);

            for (String gwasId : gwasIdList) {
                // 获取单细胞信息
                Gwas gwas = gwasMapper.selectByGwasId(gwasId);
                String gwasNodeId = gwas.getTrait() + "(" + gwas.getGwasId() + ")";
                // 添加单细胞结点
                if (!nodeIdList.contains(gwasNodeId)) {
                    echartsNode = new EchartsNode();
                    echartsNode.setName(gwasNodeId);
                    echartsNodeList.add(echartsNode);
                    nodeIdList.add(gwasNodeId);
                }

                // 添加边的信息
                EchartsLink echartsLink = new EchartsLink();
                echartsLink.setSource(nodeId);
                echartsLink.setTarget(gwasNodeId);
                echartsLink.setValue(1);
                echartsLinkList.add(echartsLink);
            }

        }

        return EchartsGraphData.builder().nodes(echartsNodeList).links(echartsLinkList).build();
    }

    @Cacheable
    @Override
    public List<GwasMarker> listGwasMarkerByGwasIdAndGseId(String gwasId, String gseId, Double pValue) {
        return gwasGseMarkerMapper.selectByGwasIdAndGseId(gwasId, gseId, pValue);
    }

    @Cacheable
    @Override
    public EchartsData<String, String> getRelationHeatmapByGwasId(String gwasId) {
        Gwas anchorGwas = gwasMapper.selectByGwasId(gwasId);
        String subChapterLevel = anchorGwas.getSubChapterLevel();
        List<String> gwasIdList = gwasMapper.selectGwasIdBySubChapterLevel(subChapterLevel);

        List<String> gseIdList = gwasGseMapper.selectGseByGwasIdList(gwasIdList);

        List<FdrGseGwas> fdrList = fdrGseGwasMapper.selectByGseIdList(gseIdList);

        for (FdrGseGwas fdrGseGwas : fdrList) {
            fdrGseGwas.setSingleCell(singleCellMapper.selectByGseId(fdrGseGwas.getGseId()));
            fdrGseGwas.setGwas(gwasMapper.selectByGwasId(fdrGseGwas.getGwasId()));
        }

        return EchartsData.<String, String>builder().xData(gwasIdList).yData(gseIdList).extraData(fdrList).build();
    }

    @Cacheable
    @Override
    public EchartsData<String, String> getRelationHeatmapByGseId(String gseId) {

        SingleCell anchorSingleCell = singleCellMapper.selectByGseId(gseId);
        List<String> gseIdList;

        if (anchorSingleCell.getLabel() == 1) {
            gseIdList = Collections.singletonList(gseId);
        } else {
            String disease = anchorSingleCell.getDisease();
            gseIdList = singleCellMapper.selectGseIdByDisease(disease);
        }

        List<String> gwasIdList = gwasGseMapper.selectGwasByGseIdList(gseIdList);

        List<FdrGseGwas> fdrList = fdrGseGwasMapper.selectByGseIdList(gseIdList);

        for (FdrGseGwas fdrGseGwas : fdrList) {
            fdrGseGwas.setGwas(gwasMapper.selectByGwasId(fdrGseGwas.getGwasId()));
            fdrGseGwas.setSingleCell(singleCellMapper.selectByGseId(fdrGseGwas.getGseId()));
        }

        return EchartsData.<String, String>builder().xData(gwasIdList).yData(gseIdList).extraData(fdrList).build();
    }

    @Cacheable
    @Override
    public List<String> listGeneByIdAndType(String id, String type) {
        return geneMapper.searchGeneByIdAndTable(id, StringUtil.isEqual(type, "single_cell") ? "t_single_cell_gene" : "t_gwas_gene");
    }

    @Cacheable
    @Override
    public List<GwasScore> listGwasGseScoreByGwasIdAndGseId(String gwasId, String gseId) {
        String id = gwasId.split("_")[2];
        return gwasScoreMapper.selectByGwasIdTableAndGseId("t_gwas_" + id + "_score", gseId);
    }

    @Cacheable
    @Override
    public EchartsData<String, String> getSampleCellTypeCount(String gseId) {
        List<SampleCount> sampleCountList = clusterAnnoMapper.searchGsmClusterCountByGseId(gseId);

        List<String> gsmList = sampleCountList.stream().map(SampleCount::getGsmId).distinct().toList();
//        Map<String, List<SampleCount>> stringListMap = sampleCountList.stream().collect(Collectors.groupingBy(SampleCount::getGsmId));
        Map<String, List<SampleCount>> cellTypeListMap = sampleCountList.stream().collect(Collectors.groupingBy(SampleCount::getCellType));
        List<String> cellTypeList = sampleCountList.stream().map(SampleCount::getCellType).distinct().toList();

        List<List<Object>> data = Lists.newArrayListWithCapacity(cellTypeList.size());

        for (String cellType : cellTypeList) {
            List<SampleCount> sampleCounts = cellTypeListMap.get(cellType);
            List<Object> cellTypeCountList = Lists.newArrayListWithCapacity(cellTypeList.size());

            for (String ignored : gsmList) {
                cellTypeCountList.add(0);
            }

            for (SampleCount sampleCount : sampleCounts) {
                String gsmId = sampleCount.getGsmId();
                int indexOf = gsmList.indexOf(gsmId);
                cellTypeCountList.set(indexOf, sampleCount.getCount());

            }
            data.add(cellTypeCountList);
        }
        return EchartsData.<String, String>builder().xData(gsmList).yData(cellTypeList).data(data).build();
    }

    @Cacheable
    @Override
    public EchartsGraphData getGwasGeneGraph(String gseId) {
        List<GwasGseMarker> gwasGseMarkerList = gwasGseMarkerMapper.selectTopByGseId(gseId);

        List<EchartsNode> echartsNodeList = Lists.newArrayListWithCapacity(gwasGseMarkerList.size() + 1);
        List<EchartsLink> echartsLinkList = Lists.newArrayListWithCapacity(gwasGseMarkerList.size());

        List<EchartsCategories> categoriesList = Lists.newArrayListWithCapacity(2);
        categoriesList.add(EchartsCategories.builder().name("GSE").build());
        categoriesList.add(EchartsCategories.builder().name("Gene").build());

        EchartsNode echartsNode;
        // 添加性状结点
        echartsNode = new EchartsNode();
        echartsNode.setId(gseId);
        echartsNode.setName(gseId);
        echartsNode.setCategory("GSE");
        echartsNode.setSymbolSize(25);
        echartsNodeList.add(echartsNode);

        for (GwasGseMarker gwasGseMarker : gwasGseMarkerList) {
            // 添加性状结点
            echartsNode = new EchartsNode();
            echartsNode.setId(gwasGseMarker.getGene() + gwasGseMarker.getScore());
            echartsNode.setName(gwasGseMarker.getGene());
            echartsNode.setSymbolSize(20);
            echartsNode.setCategory("Gene");
            echartsNodeList.add(echartsNode);
            // 添加边的信息
            EchartsLink echartsLink = new EchartsLink();
            echartsLink.setSource(gseId);
            echartsLink.setTarget(gwasGseMarker.getGene() + gwasGseMarker.getScore());
            echartsLink.setValue(1);
            echartsLinkList.add(echartsLink);
        }

        return EchartsGraphData.builder().nodes(echartsNodeList).links(echartsLinkList).categories(categoriesList).build();
    }

    @Cacheable
    @Override
    public EchartsData<String, String> getTraitRelevantCellByGwasIdAndGseId(String gwasId, String gseId) {
        List<GwasScore> gwasScoreList = listGwasGseScoreByGwasIdAndGseId(gwasId, gseId);

        List<String> cellTypeList = gwasScoreList.stream().map(GwasScore::getCellType).distinct().toList();

        List<List<Object>> data = Lists.newArrayListWithCapacity(2);

        List<Object> cellTypeCountList1 = Lists.newArrayListWithCapacity(cellTypeList.size());
        List<Object> cellTypeCountList2 = Lists.newArrayListWithCapacity(cellTypeList.size());

        for (String cellType : cellTypeList) {
            List<GwasScore> gwasScoreList1 = gwasScoreList.stream().filter(gwasScore -> StringUtil.isEqual(gwasScore.getCellType(), cellType)).toList();
            int size = gwasScoreList1.size();
            long count = gwasScoreList1.stream().filter(gwasScore -> gwasScore.getNormScore() > 0).count();
            cellTypeCountList1.add(count);
            cellTypeCountList2.add(size - count);
        }

        data.add(cellTypeCountList1);
        data.add(cellTypeCountList2);
        return EchartsData.<String, String>builder().xData(cellTypeList).yData(Arrays.asList("Trait-relevant cell", "Other cell")).data(data).build();
    }

    @Cacheable
    @Override
    public Map<String, Object> getGwasTraitCellRelationshipData(String gseId) {
        SingleCell anchorSingleCell = singleCellMapper.selectByGseId(gseId);
        List<String> gseIdList;

        if (anchorSingleCell.getLabel() == 1) {
            gseIdList = Collections.singletonList(gseId);
        } else {
            String disease = anchorSingleCell.getDisease();
            gseIdList = singleCellMapper.selectGseIdByDisease(disease);
        }

        List<Gwas> gwasList = gwasGseMapper.selectByGseIdList(gseIdList);

        List<FdrGseGwas> fdrList = fdrGseGwasMapper.selectByGseIdList(gseIdList);

        for (FdrGseGwas fdrGseGwas : fdrList) {
            fdrGseGwas.setGwas(gwasMapper.selectByGwasId(fdrGseGwas.getGwasId()));
            fdrGseGwas.setSingleCell(singleCellMapper.selectByGseId(fdrGseGwas.getGseId()));
        }

        HashMap<String, Object> hashMap = Maps.newHashMapWithExpectedSize(2);
        hashMap.put("gwas", gwasList);
        hashMap.put("fdr", fdrList);
        return hashMap;
    }

    @Cacheable
    @Override
    public Map<String, Object> getGwasTraitCellRelationshipDataByGwasId(String gwasId) {

        Gwas anchorGwas = gwasMapper.selectByGwasId(gwasId);
        String subChapterLevel = anchorGwas.getSubChapterLevel();
        List<String> gwasIdList = gwasMapper.selectGwasIdBySubChapterLevel(subChapterLevel);

        List<String> gseIdList = gwasGseMapper.selectGseByGwasIdList(gwasIdList);

        List<SingleCell> singleCellList = singleCellMapper.selectByGseIdList(gseIdList);

        List<FdrGseGwas> fdrList = fdrGseGwasMapper.selectByGseIdList(gseIdList);

        for (FdrGseGwas fdrGseGwas : fdrList) {
            fdrGseGwas.setSingleCell(singleCellMapper.selectByGseId(fdrGseGwas.getGseId()));
            fdrGseGwas.setGwas(gwasMapper.selectByGwasId(fdrGseGwas.getGwasId()));
        }

        HashMap<String, Object> hashMap = Maps.newHashMapWithExpectedSize(2);
        hashMap.put("sample", singleCellList);
        hashMap.put("fdr", fdrList);
        return hashMap;
    }

    @Cacheable
    @Override
    public List<CellTypeDifferentiationScore> listDifferentiationScore(String gseId, String cellCategories) {
        return cellTypeDifferentiationScoreMapper.selectByGseIdAndCellType(gseId, "t_" + cellCategories);
    }

    @Cacheable
    @Override
    public List<CellTypeDifferentiationScore> listDifferentiationScoreByGwasId(String gwasId, String cellCategories) {
        return cellTypeDifferentiationScoreMapper.selectByGwasIdAndCellType(gwasId, "t_" + cellCategories);
    }
}
