package com.spring.boot.mapper;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.GwasGse;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.util.util.result.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GwasGseMapper {

    List<String> selectGwasByGseId(String gseId);

    List<String> selectGseByGwasId(String gwasId);

    List<String> selectGseByGwasIdList(List<String> gwasIdList);

    List<String> selectGwasByGseIdList(List<String> gseIdList);

    List<GwasGse> selectAll();

    List<Gwas> selectByGseIdList(List<String> gseIdList);

    List<SingleCell> selectByGwasIdList(List<String> gwasIdList);

    List<GwasGse> selectAllWithPage(@Param("page") Page page);
}