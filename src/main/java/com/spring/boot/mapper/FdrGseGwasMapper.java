package com.spring.boot.mapper;

import com.spring.boot.pojo.FdrGseGwas;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FdrGseGwasMapper {

    List<FdrGseGwas> selectByGseIdList(List<String> gseIdList);

}