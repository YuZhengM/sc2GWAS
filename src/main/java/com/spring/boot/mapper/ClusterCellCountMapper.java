package com.spring.boot.mapper;

import com.spring.boot.pojo.ClusterCellCount;
import com.spring.boot.pojo.vo.SearchVO;
import com.spring.boot.util.model.vo.FieldNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterCellCountMapper {

    List<ClusterCellCount> searchByGseId(@Param("gseId") String gseId);

//    List<ClusterCellCount> searchClusterByGseId(@Param("gseId") String gseId);

    List<String> selectFieldByInfo(@Param("field") String field, @Param("searchVO") SearchVO searchVO);

    List<FieldNumber> searchCellTypeCountByGseIdList(@Param("gseIdList") List<String> gseIdList, @Param("cellType") String cellType);
}