package com.spring.boot.mapper;

import com.spring.boot.pojo.CellTypeDifferentiationScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellTypeDifferentiationScoreMapper {

    List<CellTypeDifferentiationScore> selectByGseIdAndCellType(@Param("gseId") String gseId, @Param("table") String table);

    List<CellTypeDifferentiationScore> selectByGwasIdAndCellType(@Param("gwasId") String gwasId, @Param("table") String table);

}