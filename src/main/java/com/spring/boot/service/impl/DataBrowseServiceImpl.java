package com.spring.boot.service.impl;

import com.google.common.collect.Lists;
import com.spring.boot.mapper.ClusterCellCountMapper;
import com.spring.boot.mapper.DistinctCellTypeMapper;
import com.spring.boot.mapper.GwasMapper;
import com.spring.boot.mapper.SingleCellMapper;
import com.spring.boot.pojo.DistinctCellType;
import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.pojo.vo.DataBrowseResultVO;
import com.spring.boot.pojo.vo.DataBrowseVO;
import com.spring.boot.service.DataBrowseService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.vo.FieldNumber;
import com.spring.boot.util.util.ListUtil;
import com.spring.boot.util.util.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yu Zhengmin
 */
@CacheConfig(cacheNames = "dataBrowse", keyGenerator = "cacheKeyGenerator")
@Service
public class DataBrowseServiceImpl implements DataBrowseService {

    private static final Log log = LogFactory.getLog(DataBrowseServiceImpl.class);

    private GwasMapper gwasMapper;
    private SingleCellMapper singleCellMapper;
    private ClusterCellCountMapper clusterCellCountMapper;
    private DistinctCellTypeMapper distinctCellTypeMapper;

    public DataBrowseServiceImpl() {
    }

    @Autowired
    public DataBrowseServiceImpl(GwasMapper gwasMapper, SingleCellMapper singleCellMapper, DistinctCellTypeMapper distinctCellTypeMapper, @Qualifier("clusterCellCountMapper") ClusterCellCountMapper clusterCellCountMapper) {
        this.gwasMapper = gwasMapper;
        this.singleCellMapper = singleCellMapper;
        this.distinctCellTypeMapper = distinctCellTypeMapper;
        this.clusterCellCountMapper = clusterCellCountMapper;
    }

    @Cacheable
    @Override
    public List<Gwas> listGwasBrowseData() {
        return gwasMapper.selectAll();
    }

    @Cacheable
    @Override
    public List<SingleCell> listSingleCellBrowseData() {
        return singleCellMapper.selectAll();
    }

    private List<FieldNumber> listFieldCount(Map<String, Long> map) {
        List<FieldNumber> fieldNumberList = Lists.newArrayListWithCapacity(map.size());
        map.forEach((k, v) -> fieldNumberList.add(FieldNumber.builder().field(k).number(v.intValue()).build()));
        return fieldNumberList;
    }

    @Cacheable
    @Override
    public DataBrowseResultVO<Gwas> listGwasBrowseDataAndCount(DataBrowseVO dataBrowseVO) {
        dataBrowseVO = new DataBrowseVO(dataBrowseVO);
        log.info("[listGwasBrowseDataAndCount]: dataBrowseVO {}", dataBrowseVO.toString());
        List<Gwas> gwasList = gwasMapper.searchByDataBrowseParams(dataBrowseVO);

        Map<String, Long> domainNumbers = gwasList.stream().collect(Collectors.groupingBy(Gwas::getDomain, Collectors.counting()));
        Map<String, Long> phenotypeNumbers = gwasList.stream().collect(Collectors.groupingBy(Gwas::getTrait, Collectors.counting()));

        return DataBrowseResultVO.<Gwas>builder()
                .domainList(listFieldCount(domainNumbers))
                .phenotypeList(listFieldCount(phenotypeNumbers))
                .dataBrowseDataList(gwasList).build();
    }

    @Cacheable
    @Override
    public DataBrowseResultVO<SingleCell> listSingleCellBrowseDataAndCount(DataBrowseVO dataBrowseVO) {
        dataBrowseVO = new DataBrowseVO(dataBrowseVO);
        log.info("[listSingleCellBrowseDataAndCount]: dataBrowseVO {}", dataBrowseVO.toString());
        List<SingleCell> singleCellList = singleCellMapper.searchByDataBrowseParams(dataBrowseVO);

        Map<String, Long> domainNumbers = singleCellList.stream().collect(Collectors.groupingBy(SingleCell::getDomain, Collectors.counting()));
        Map<String, Long> phenotypeNumbers = singleCellList.stream().collect(Collectors.groupingBy(SingleCell::getDisease, Collectors.counting()));
        Map<String, Long> biosampleTypeNumbers = singleCellList.stream().collect(Collectors.groupingBy(SingleCell::getBiosampleType, Collectors.counting()));
        Map<String, Long> tissueTypeNumbers = singleCellList.stream().collect(Collectors.groupingBy(SingleCell::getTissueType, Collectors.counting()));
//        Map<String, Long> tissueTypeNumbers = singleCellList.stream().collect(Collectors.groupingBy(SingleCell::getTissueType, Collectors.counting()));

        List<String> gseIdList = singleCellList.stream().map(SingleCell::getGseId).distinct().toList();

//        List<DistinctCellType> distinctCellTypeList = distinctCellTypeMapper.searchByCellType(dataBrowseVO.getCellType());

        List<FieldNumber> fieldNumberList = NullUtil.listEmpty();
        if (ListUtil.isNotEmpty(gseIdList)) {
            fieldNumberList = clusterCellCountMapper.searchCellTypeCountByGseIdList(gseIdList, dataBrowseVO.getCellType());
        }

        return DataBrowseResultVO.<SingleCell>builder()
                .domainList(listFieldCount(domainNumbers))
                .phenotypeList(listFieldCount(phenotypeNumbers))
                .biosampleTypeList(listFieldCount(biosampleTypeNumbers))
                .tissueTypeList(listFieldCount(tissueTypeNumbers))
                .cellTypeList(fieldNumberList)
                .dataBrowseDataList(singleCellList).build();
    }
}
