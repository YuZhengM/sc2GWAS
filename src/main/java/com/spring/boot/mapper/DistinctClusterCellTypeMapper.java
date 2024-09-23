package com.spring.boot.mapper;

import com.spring.boot.pojo.vo.SearchVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistinctClusterCellTypeMapper {

    List<String> selectFieldByInfo(String field, SearchVO searchVO);
}
