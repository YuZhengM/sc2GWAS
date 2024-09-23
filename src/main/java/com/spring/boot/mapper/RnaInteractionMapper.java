package com.spring.boot.mapper;

import com.spring.boot.pojo.RnaInteraction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RnaInteractionMapper {

    List<RnaInteraction> selectByChrAndStartAndEnd(@Param("chr") String chr, @Param("start") Integer start, @Param("end") Integer end);

}