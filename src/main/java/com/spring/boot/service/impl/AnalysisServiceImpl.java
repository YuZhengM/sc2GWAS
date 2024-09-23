package com.spring.boot.service.impl;

import com.google.common.collect.Lists;
import com.spring.boot.config.bean.Path;
import com.spring.boot.pojo.GwasGeneEnrichment;
import com.spring.boot.pojo.vo.GwasGeneEnrichmentVO;
import com.spring.boot.service.AnalysisService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.util.*;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Yu Zhengmin
 */
@CacheConfig(cacheNames = "analysis", keyGenerator = "cacheKeyGenerator")
@Service
public class AnalysisServiceImpl implements AnalysisService {

    private Path path;

    private static final Log log = LogFactory.getLog(AnalysisServiceImpl.class);

    public AnalysisServiceImpl() {
    }

    @Autowired
    public AnalysisServiceImpl(Path path) {
        this.path = path;
    }

    private static List<GwasGeneEnrichment> getGasGeneEnrichment(GwasGeneEnrichmentVO gwasGeneEnrichmentVO, List<String> result) {

        String type = gwasGeneEnrichmentVO.getType();

//        boolean isFdr = gwasGeneEnrichmentVO.getFdr() == 1;

        int column;

        if (StringUtil.isEqual(type, "gwasGene")) {
            column = 8;
        } else if (StringUtil.isEqual(type, "clusterMarker")) {
            column = 10;
        } else {
            column = 9;
        }

//        int count1 = isFdr ? 1 : 0;
        int count1 = 1;

        int lineNumber = result.size() / (column + count1);

        List<GwasGeneEnrichment> gwasGeneEnrichmentList = Lists.newArrayListWithCapacity(lineNumber);

        for (int i = 0; i < lineNumber; i++) {
            GwasGeneEnrichment gwasGeneEnrichment = new GwasGeneEnrichment();
            gwasGeneEnrichment.setAnnotatedGene(result.get(i));
            gwasGeneEnrichment.setInter(NumberUtil.createInteger(result.get(i + lineNumber)));
            gwasGeneEnrichment.setTargetGeneNumber(NumberUtil.createInteger(result.get(i + lineNumber * 2)));
            gwasGeneEnrichment.setJaccard(NumberUtil.createDouble(result.get(i + lineNumber * 3)));
            gwasGeneEnrichment.setPValue(NumberUtil.createDouble(result.get(i + lineNumber * 4)));
            gwasGeneEnrichment.setFdrStr(result.get(i + lineNumber * 5));
            gwasGeneEnrichment.setBonferroni(NumberUtil.createDouble(result.get(i + lineNumber * 6)));
            gwasGeneEnrichment.setPercentage(result.get(i + lineNumber * (6 + count1)));

            if (StringUtil.isEqual(type, "gwasGene")) {
                gwasGeneEnrichment.setGwasId(result.get(i + lineNumber * (7 + count1)));
            } else if (StringUtil.isEqual(type, "clusterMarker")) {
                gwasGeneEnrichment.setGseId(result.get(i + lineNumber * (7 + count1)));
                gwasGeneEnrichment.setCluster(result.get(i + lineNumber * (8 + count1)));
                gwasGeneEnrichment.setCellType(result.get(i + lineNumber * (9 + count1)));
            } else {
                gwasGeneEnrichment.setGwasId(result.get(i + lineNumber * (7 + count1)));
                gwasGeneEnrichment.setGseId(result.get(i + lineNumber * (8 + count1)));
            }
            gwasGeneEnrichmentList.add(gwasGeneEnrichment);
        }
        return gwasGeneEnrichmentList.stream().peek((gwasGeneEnrichment -> gwasGeneEnrichment.setFdr(Double.parseDouble(gwasGeneEnrichment.getFdrStr())))).sorted(Comparator.comparing(GwasGeneEnrichment::getFdr)).collect(Collectors.toList());
    }

    @Cacheable
    @Override
    public Map<String, Object> getGwasGeneEnrichment(GwasGeneEnrichmentVO gwasGeneEnrichmentVO) {
        String workPath = path.getWorkPath();
        String pagePath = path.getPagePath();
        String type = gwasGeneEnrichmentVO.getType();

        String rExecFile = workPath + "/analysis/analysis_" + type + ".R";
//        String backgroundFile = workPath + "/analysis/" + type + "_genes.txt";
        String userPath = workPath + "/user/";

        log.info("[getGwasGeneEnrichment]: params: {}", gwasGeneEnrichmentVO.toString());

        String content = gwasGeneEnrichmentVO.getContent();
        String isFdr = gwasGeneEnrichmentVO.getFdr() == 1 ? "fdr" : "pvalue";
        boolean isFile = gwasGeneEnrichmentVO.getIsFile() == 1;

        String geneFile;
        if (isFile) {
            geneFile = userPath + gwasGeneEnrichmentVO.getFileId();
        } else {
            geneFile = userPath + type + "_" + StringUtil.getUniqueId() + ".txt";
            FileUtil.formation(geneFile, content);
        }


        String outputFilename = type + "_" + StringUtil.getUniqueId() + ".txt";
        String outputFile = pagePath + "/analysis/user/" + outputFilename;

        List<String> result = null;

        // 连接 R
        try {
            RConnection conn = new RConnection();

            // 设置 R 变量
            conn.assign("geneFile", geneFile);
            conn.assign("cla", isFdr);
            conn.assign("threshold", String.valueOf(gwasGeneEnrichmentVO.getThreshold()));
            conn.assign("rExecFile", rExecFile);
            conn.assign("outputFile", outputFile);
            // 执行 R
            conn.eval("source(rExecFile)");
            // 得到返回值
            REXP eval = conn.eval("phyperr(cla, threshold, geneFile, outputFile)");
            result = Arrays.asList(eval.asStrings());

            conn.close();
        } catch (RserveException | REXPMismatchException e) {
            log.error("[getGwasGeneEnrichment]: R -> type: {}, Execution error >>> {}", type, e.getMessage());
        }

        if (ListUtil.isEmpty(result)) {
            log.warn("[getGwasGeneEnrichment] R -> type: {} result information is NULL", type);
            return NullUtil.mapEmpty();
        }
        List<GwasGeneEnrichment> gwasGeneEnrichmentList = getGasGeneEnrichment(gwasGeneEnrichmentVO, result);
        Map<String, Object> map = new HashMap<>(2);
        map.put("data", gwasGeneEnrichmentList);
        map.put("filename", outputFilename);
        return map;
    }

}
