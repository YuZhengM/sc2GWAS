package com.spring.boot.mapper;

import com.spring.boot.pojo.Crispr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrisprMapper {

    List<Crispr> selectByChrAndStartAndEnd(@Param("chr") String chr, @Param("start") Integer start, @Param("end") Integer end);

}