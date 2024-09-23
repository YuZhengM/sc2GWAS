package com.spring.boot.mapper;

import com.spring.boot.pojo.GwasChrCounts;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GwasChrCountsMapper {

    List<GwasChrCounts> selectByGwasId(String gwasId);
}