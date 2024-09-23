package com.spring.boot.mapper;

import com.spring.boot.pojo.SuperEnhancerSedbv2;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperEnhancerSedbv2Mapper {

    List<SuperEnhancerSedbv2> selectByChrAndStartAndEnd(@Param("chr") String chr, @Param("start") Integer start, @Param("end") Integer end);

}