package com.spring.boot.mapper;

import com.spring.boot.pojo.GwasGseMarker;
import com.spring.boot.pojo.GwasMarker;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GwasGseMarkerMapper {

    List<GwasGseMarker> selectByGene(String gene);

    List<GwasGseMarker> selectTopByGseId(@Param("gseId") String gseId);

    List<GwasMarker> selectByGwasIdAndGseId(@Param("gwasId") String gwasId, @Param("gseId") String gseId, @Param("pValue") Double pValue);

    List<GwasGseMarker> selectTopByGene(String gene);
}