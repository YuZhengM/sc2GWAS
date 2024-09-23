package com.spring.boot.service.impl;

import com.spring.boot.mapper.DistinctClusterCellTypeMapper;
import com.spring.boot.mapper.GeneMapper;
import com.spring.boot.mapper.GwasMapper;
import com.spring.boot.mapper.SingleCellMapper;
import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.pojo.vo.SearchVO;
import com.spring.boot.service.SearchService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.PageResult;
import com.spring.boot.util.util.NullUtil;
import com.spring.boot.util.util.StringUtil;
import com.spring.boot.util.util.result.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
@CacheConfig(cacheNames = "search", keyGenerator = "cacheKeyGenerator")
@Service
public class SearchServiceImpl implements SearchService {

    private static final Log log = LogFactory.getLog(SearchServiceImpl.class);

    private GwasMapper gwasMapper;
    private SingleCellMapper singleCellMapper;
    private DistinctClusterCellTypeMapper distinctClusterCellTypeMapper;
    private GeneMapper geneMapper;

    public SearchServiceImpl() {
    }

    @Autowired
    public SearchServiceImpl(GwasMapper gwasMapper, SingleCellMapper singleCellMapper, DistinctClusterCellTypeMapper distinctClusterCellTypeMapper, GeneMapper geneMapper) {
        this.gwasMapper = gwasMapper;
        this.singleCellMapper = singleCellMapper;
        this.distinctClusterCellTypeMapper = distinctClusterCellTypeMapper;
        this.geneMapper = geneMapper;
    }


    @Cacheable
    @Override
    public List<String> listInformationByField(String id, String field, SearchVO searchVO) {

        if (StringUtil.isEqual(id, "trait")) {
            if (StringUtil.isEqual(field, "f_domain")) {
                return gwasMapper.selectFieldByInfo(field, searchVO);
            } else {
                List<String> traitList = gwasMapper.selectFieldByInfo("f_sub_chapter_level", searchVO);
                List<String> stringList = gwasMapper.selectFieldByInfo("f_trait", searchVO);
                traitList.addAll(stringList);
                return traitList.stream().distinct().toList();
            }
        } else if (StringUtil.isEqual(id, "tissue")) {
            return singleCellMapper.selectFieldByInfo(field, searchVO);
        } else if (StringUtil.isEqual(id, "cell_type")) {
            return distinctClusterCellTypeMapper.selectFieldByInfo(field, searchVO);
        }

        return NullUtil.listEmpty();
    }

    @Cacheable
    @Override
    public PageResult<Gwas> listGwasByTraitBased(SearchVO searchVO) {
        if (StringUtil.isNotEmpty(searchVO.getDomain()) && StringUtil.isNotEqual(searchVO.getDomain(), "All")) {
            searchVO.setDomain("%" + searchVO.getDomain().replaceAll("( disease.*| Disease.*|'s|’s)", "") + "%");
        }
        if (StringUtil.isNotEmpty(searchVO.getTrait()) && StringUtil.isNotEqual(searchVO.getTrait(), "All")) {
            searchVO.setTrait("%" + searchVO.getTrait().replaceAll("( disease.*| Disease.*|'s|’s)", "") + "%");
        }
        return PageResultUtil.format(searchVO.getPage(), () -> gwasMapper.selectByInfo(searchVO, searchVO.getPage()));
    }

    @Cacheable
    @Override
    public PageResult<SingleCell> listSingleCellByTissueBased(SearchVO searchVO) {
        return PageResultUtil.format(searchVO.getPage(), () -> singleCellMapper.selectByInfo(searchVO, searchVO.getPage()));
    }

    @Cacheable
    @Override
    public PageResult<SingleCell> listSingleCellByCellTypeBased(SearchVO searchVO) {
        return PageResultUtil.format(searchVO.getPage(), () -> singleCellMapper.selectInfoByDiseaseAndCellType(searchVO, searchVO.getPage()));
    }

    @Cacheable
    @Override
    public PageResult<Gwas> listGwasByMeshIdBased(SearchVO searchVO) {
        return PageResultUtil.format(searchVO.getPage(), () -> gwasMapper.selectInfoByMeshId(searchVO, searchVO.getPage()));
    }

    @Cacheable
    @Override
    public List<String> listGene() {
        return geneMapper.selectGeneAll();
    }
}
