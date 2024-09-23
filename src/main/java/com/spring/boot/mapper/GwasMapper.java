package com.spring.boot.mapper;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.vo.DataBrowseVO;
import com.spring.boot.pojo.vo.SearchVO;
import com.spring.boot.util.util.result.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GwasMapper {

    Gwas selectByGwasId(@Param("gwasId") String gwasId);

    List<Gwas> selectAll();

    List<Gwas> selectByGwasIdList(@Param("gwasIdList") List<String> gwasIdList);

    List<Gwas> selectBySubChapterLevel(@Param("subChapterLevel") String subChapterLevel);

    List<String> selectFieldByInfo(@Param("field") String field, @Param("searchVO") SearchVO searchVO);

    List<Gwas> selectByInfo(@Param("searchVO") SearchVO searchVO, @Param("page") Page page);

    List<String> selectGwasIdBySubChapterLevel(@Param("subChapterLevel") String subChapterLevel);

    List<Gwas> selectInfoByMeshId(SearchVO searchVO, Page page);

    List<Gwas> searchByDataBrowseParams(@Param("dataBrowseVO") DataBrowseVO dataBrowseVO);
}
