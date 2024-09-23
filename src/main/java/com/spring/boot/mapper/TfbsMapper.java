package com.spring.boot.mapper;

import com.spring.boot.pojo.Tfbs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TfbsMapper {

    List<Tfbs> selectByChrAndStartAndEnd(@Param("chr") String chr, @Param("start") Integer start, @Param("end") Integer end);

}