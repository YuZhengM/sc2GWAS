package com.spring.boot.mapper;

import com.spring.boot.pojo.Fdr;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellTypeFdrMapper {

    List<Fdr> selectByGseIdList(List<String> gseIdList);
}