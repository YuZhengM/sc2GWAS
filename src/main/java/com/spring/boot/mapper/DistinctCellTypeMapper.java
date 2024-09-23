package com.spring.boot.mapper;

import com.spring.boot.pojo.DistinctCellType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistinctCellTypeMapper {

    List<DistinctCellType> searchByGseId(String gseId);

    List<DistinctCellType> searchByCellType(String cellType);
}