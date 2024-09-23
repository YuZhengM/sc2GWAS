package com.spring.boot.mapper;

import com.spring.boot.pojo.GwasScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GwasScoreMapper {

    List<GwasScore> selectByGwasIdTableAndGseId(@Param("table") String table, @Param("gseId") String gseId);
}