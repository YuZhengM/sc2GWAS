package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneMapper {

    List<String> searchGeneByGseId(@Param("gseId") String gseId);

    List<String> searchGeneByIdAndTable(@Param("id") String id, @Param("table") String table);

    List<String> selectGeneAll();

}