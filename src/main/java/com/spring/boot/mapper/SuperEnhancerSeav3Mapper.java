package com.spring.boot.mapper;

import com.spring.boot.pojo.SuperEnhancerSeav3;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperEnhancerSeav3Mapper {

    List<SuperEnhancerSeav3> selectByChrAndStartAndEnd(@Param("chr") String chr, @Param("start") Integer start, @Param("end") Integer end);

}