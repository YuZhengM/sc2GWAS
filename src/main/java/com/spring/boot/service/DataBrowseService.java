package com.spring.boot.service;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.pojo.vo.DataBrowseResultVO;
import com.spring.boot.pojo.vo.DataBrowseVO;

import java.util.List;

public interface DataBrowseService {

    List<Gwas> listGwasBrowseData();

    List<SingleCell> listSingleCellBrowseData();

    DataBrowseResultVO<Gwas> listGwasBrowseDataAndCount(DataBrowseVO dataBrowseVO);

    DataBrowseResultVO<SingleCell> listSingleCellBrowseDataAndCount(DataBrowseVO dataBrowseVO);
}
