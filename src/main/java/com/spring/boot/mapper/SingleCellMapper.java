package com.spring.boot.mapper;

import com.spring.boot.pojo.SingleCell;
import com.spring.boot.pojo.vo.DataBrowseVO;
import com.spring.boot.pojo.vo.SearchVO;
import com.spring.boot.util.util.result.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleCellMapper {

    SingleCell selectByGseId(String gseId);

    List<SingleCell> selectAll();

    List<SingleCell> selectByDisease(String disease);

    List<String> selectFieldByInfo(@Param("field") String field, @Param("searchVO") SearchVO searchVO);

    List<SingleCell> selectByInfo(@Param("searchVO") SearchVO searchVO, @Param("page") Page page);

    List<SingleCell> selectInfoByDiseaseAndCellType(@Param("searchVO") SearchVO searchVO, @Param("page") Page page);

    List<String> selectGseIdByDisease(String disease);

    List<SingleCell> searchByDataBrowseParams(@Param("dataBrowseVO") DataBrowseVO dataBrowseVO);

    List<SingleCell> selectByGseIdList(List<String> gseIdList);
}
