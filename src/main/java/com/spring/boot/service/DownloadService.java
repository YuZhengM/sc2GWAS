package com.spring.boot.service;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.GwasGse;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.util.model.PageResult;
import com.spring.boot.util.util.result.Page;

import java.util.List;

public interface DownloadService {

    List<SingleCell> listSampleInformation();

    List<Gwas> listGwasInformation();

    List<GwasGse> listGwasGseInformation();

    PageResult<GwasGse> listGwasGseInformationWithPage(Page page);
}
