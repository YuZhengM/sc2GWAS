package com.spring.boot.service;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.pojo.vo.SearchVO;
import com.spring.boot.util.model.PageResult;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
public interface SearchService {

    List<String> listInformationByField(String id, String field, SearchVO searchVO);

    PageResult<Gwas> listGwasByTraitBased(SearchVO searchVO);

    PageResult<SingleCell> listSingleCellByTissueBased(SearchVO searchVO);

    PageResult<SingleCell> listSingleCellByCellTypeBased(SearchVO searchVO);

    PageResult<Gwas> listGwasByMeshIdBased(SearchVO searchVO);

    List<String> listGene();

}
