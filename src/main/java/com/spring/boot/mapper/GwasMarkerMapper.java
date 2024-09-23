package com.spring.boot.mapper;

import com.spring.boot.pojo.GwasMarker;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GwasMarkerMapper {

    List<GwasMarker> selectByGwasIdAndGseId(@Param("gwasId") String gwasId, @Param("gseId") String gseId, @Param("pValue") String pValue);

}