package com.spring.boot.util.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spring.boot.config.bean.Path;
import com.spring.boot.pojo.*;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.vo.echarts.EchartsData;
import com.spring.boot.util.model.vo.echarts.EchartsPieData;
import com.spring.boot.util.model.vo.echarts.SeriesPieData;
import com.spring.boot.util.model.vo.plotly.PlotlyClusterData;
import com.spring.boot.util.model.vo.plotly.PlotlyData;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 为该项目特有的公共类
 *
 * @author Yu Zhengmin
 */
public class ApplicationUtil {

    private static final Log log = LogFactory.getLog(ApplicationUtil.class);

    public static String addCellTypeAndCluster(String cellType, Integer cluster) {
        return cellType + " (" + cluster + ")";
    }

    public static EchartsPieData<String, String> getCellTypeCountEchartsData(List<ClusterCellCount> clusterCellCountList) {
        if (ListUtil.isEmpty(clusterCellCountList)) {
            log.warn("[getCellTypeCountEchartsData] clusterCellTypeCountList 数据为空");
            return EchartsPieData.<String, String>builder().build();
        }
        int size = clusterCellCountList.size();
        // 建立容器
        List<String> legendList = Lists.newArrayListWithCapacity(size);
        List<SeriesPieData> seriesPieDataList = Lists.newArrayListWithCapacity(size);
        Map<String, String> description = Maps.newHashMapWithExpectedSize(size);
        // 添加标签和数据
        for (ClusterCellCount clusterCellCount : clusterCellCountList) {
            String cellType = clusterCellCount.getCellType();
            Integer number = clusterCellCount.getCount();
            legendList.add(cellType);
            seriesPieDataList.add(SeriesPieData.builder().name(cellType + "(" + clusterCellCount.getCluster() + ")").value(number).build());
        }
        return EchartsPieData.<String, String>builder().legend(legendList).data(seriesPieDataList).description(description).build();
    }

    public static EchartsPieData<String, String> getChrCountEchartsData(List<GwasChrCounts> gwasChrCountsList) {
        if (ListUtil.isEmpty(gwasChrCountsList)) {
            log.warn("[getChrCountEchartsData] fieldNumberList 数据为空");
            return EchartsPieData.<String, String>builder().build();
        }
        int size = gwasChrCountsList.size();
        // 建立容器
        List<String> legendList = Lists.newArrayListWithCapacity(size);
        List<SeriesPieData> seriesPieDataList = Lists.newArrayListWithCapacity(size);
        Map<String, String> description = Maps.newHashMapWithExpectedSize(size);
        // 添加标签和数据
        for (GwasChrCounts gwasChrCounts : gwasChrCountsList) {
            String field = gwasChrCounts.getChr();
            Integer number = gwasChrCounts.getCount();
            legendList.add(field);
            seriesPieDataList.add(SeriesPieData.builder().name(field).value(number).build());
        }
        return EchartsPieData.<String, String>builder().legend(legendList).data(seriesPieDataList).description(description).build();
    }

    public static EchartsData<String, Integer> getSampleEchartsData(List<SampleCount> sampleCountList) {
        if (ListUtil.isEmpty(sampleCountList)) {
            return EchartsData.<String, Integer>builder().build();
        }
        int size = sampleCountList.size();
        List<String> xList = Lists.newArrayListWithCapacity(size);
        List<Integer> yList = Lists.newArrayListWithCapacity(size);
        for (SampleCount sampleCount : sampleCountList) {
            xList.add(sampleCount.getGsmId());
            yList.add(sampleCount.getCount());
        }
        return EchartsData.<String, Integer>builder().xData(xList).yData(yList).build();
    }

    public static int getRandomCellNumber(Integer size, Double cellRate) {
        return (int) Math.ceil(size * cellRate);
    }

    public static PlotlyClusterData<Double, Double> getTypeExpressionInfo(SingleCell singleCell,
                                                                          Path path,
                                                                          List<ClusterAnno> clusterAnnoList,
                                                                          String name,
                                                                          String type) {
        String workPath = path.getWorkPath();
        // 返回值
        List<String> result = null;
        // 设置 R 变量
        String rExecFile = workPath + "/gene/code/gene.R";
        String rdsFile = workPath + "/gene/data/" + singleCell.getGseId() + "_expression_matrix.rds";
        // 连接 R
        try {
            RConnection conn = new RConnection();
            conn.assign("rExecFile", rExecFile);
            conn.assign("rdsFile", rdsFile);
            conn.assign("name", name);
            // 执行 R
            conn.eval("source(rExecFile)");
            // 得到返回值
            REXP eval = conn.eval("get_gene_row(rdsFile, name)");
            conn.close();
            result = Arrays.asList(eval.asStrings());
        } catch (RserveException | REXPMismatchException e) {
            log.error("[getClusterPeakExpression]: R -> Execution error >>> {}", e);
        }
        if (ListUtil.isEmpty(result)) {
            log.warn("[getClusterPeakExpression] R result information is NULL");
            return PlotlyClusterData.<Double, Double>builder().plotlyDataList(NullUtil.listEmpty()).build();
        }
        int size = result.size();
        int length = size / 2;
        // 添加图信息
        List<PlotlyData<Double, Double>> plotlyDataList = Lists.newArrayListWithCapacity(1);
        List<Double> color = result.subList(0, length).stream().map(Double::valueOf).toList();
        List<String> barcodesList = result.subList(length, size);
        // 添加信息
        List<Double> xList = Lists.newArrayListWithCapacity(length);
        List<Double> yList = Lists.newArrayListWithCapacity(length);
        List<String> textList = Lists.newArrayListWithCapacity(length);
        // List 添加顺序与外层循环相同
        for (String barcodes : barcodesList) {
            for (ClusterAnno clusterInfo : clusterAnnoList) {
                if (StringUtil.isEqual(barcodes, clusterInfo.getBarcode())) {
                    // 添加坐标
                    if (StringUtil.isEqual(type, "umap")) {
                        xList.add(clusterInfo.getUmap1());
                        yList.add(clusterInfo.getUmap2());
                    } else if (StringUtil.isEqual(type, "tsne")) {
                        xList.add(clusterInfo.getTsne1());
                        yList.add(clusterInfo.getTsne2());
                    }
                    textList.add(name + ": " + ApplicationUtil.addCellTypeAndCluster(clusterInfo.getCellType(), clusterInfo.getCluster()));
                    clusterAnnoList.remove(clusterInfo);
                    break;
                }
            }
        }
        plotlyDataList.add(PlotlyData.<Double, Double>builder().x(xList).y(yList).color(color).name(name).text(textList).build());
        return PlotlyClusterData.<Double, Double>builder().plotlyDataList(plotlyDataList).build();
    }

    public static List<String> listStringByPythonResult(String result) {
        String[] data = result.strip().replaceAll(" ", "").strip().split("','");
        data[0] = data[0].split("'")[1];
        data[data.length - 1] = data[data.length - 1].split("'")[0];
        return Arrays.asList(data);
    }

    public static List<String> listNumberByPythonResult(String result) {
        String[] data = result.strip().replaceAll(" ", "").split(",");
        data[0] = data[0].split("\\[")[1];
        data[data.length - 1] = data[data.length - 1].split("]")[0];
        return Arrays.asList(data);
    }

    public static List<Integer> listIntegerByPythonResult(String result) {
        List<String> strings = listNumberByPythonResult(result);
        return strings.stream().map(Integer::parseInt).toList();
    }

    public static List<Double> listDoubleByPythonResult(String result) {
        List<String> strings = listNumberByPythonResult(result);
        return strings.stream().map(Double::parseDouble).toList();
    }

}
