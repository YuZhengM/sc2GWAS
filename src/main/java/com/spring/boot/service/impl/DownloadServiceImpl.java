package com.spring.boot.service.impl;

import com.spring.boot.mapper.GwasGseMapper;
import com.spring.boot.mapper.GwasMapper;
import com.spring.boot.mapper.SingleCellMapper;
import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.GwasGse;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.service.DownloadService;
import com.spring.boot.util.model.PageResult;
import com.spring.boot.util.util.result.Page;
import com.spring.boot.util.util.result.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
@CacheConfig(cacheNames = "download", keyGenerator = "cacheKeyGenerator")
@Service
public class DownloadServiceImpl implements DownloadService {

    private SingleCellMapper singleCellMapper;
    private GwasMapper gwasMapper;
    private GwasGseMapper gwasGseMapper;

    public DownloadServiceImpl() {
    }

    @Autowired
    public DownloadServiceImpl(SingleCellMapper singleCellMapper, GwasMapper gwasMapper, GwasGseMapper gwasGseMapper) {
        this.singleCellMapper = singleCellMapper;
        this.gwasMapper = gwasMapper;
        this.gwasGseMapper = gwasGseMapper;
    }


    @Cacheable
    @Override
    public List<SingleCell> listSampleInformation() {
        return singleCellMapper.selectAll();
    }

    @Cacheable
    @Override
    public List<Gwas> listGwasInformation() {
        return gwasMapper.selectAll();
    }

    @Cacheable
    @Override
    public List<GwasGse> listGwasGseInformation() {
        return gwasGseMapper.selectAll();
    }

    @Override
    public PageResult<GwasGse> listGwasGseInformationWithPage(Page page) {
        return PageResultUtil.format(page, () -> gwasGseMapper.selectAllWithPage(page));
    }
}
