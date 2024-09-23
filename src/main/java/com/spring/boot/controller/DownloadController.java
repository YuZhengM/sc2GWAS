package com.spring.boot.controller;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.GwasGse;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.service.DownloadService;
import com.spring.boot.util.model.PageResult;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.util.result.Page;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
@RequestMapping("/download")
@CrossOrigin
@RestController
public class DownloadController {

    private DownloadService downloadService;

    public DownloadController() {
    }


    @Autowired
    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("/sample_information")
    public Result<List<SingleCell>> listSampleInformation() {
        List<SingleCell> singleCellList = downloadService.listSampleInformation();
        return ResultUtil.success("[listSampleInformation] success", singleCellList);
    }

    @GetMapping("/gwas_information")
    public Result<List<Gwas>> listGwasInformation() {
        List<Gwas> gwasList = downloadService.listGwasInformation();
        return ResultUtil.success("[listGwasInformation] success", gwasList);
    }

    @GetMapping("/gwas_gse_information")
    public Result<List<GwasGse>> listGwasGseInformation() {
        List<GwasGse> gwasGseList = downloadService.listGwasGseInformation();
        return ResultUtil.success("[listGwasGseInformation] success", gwasGseList);
    }

    @PostMapping("/gwas_gse_information_page")
    public Result<PageResult<GwasGse>> listGwasGseInformationWithPage(@RequestBody Page page) {
        PageResult<GwasGse> gwasGseList = downloadService.listGwasGseInformationWithPage(page);
        return ResultUtil.success("[listGwasGseInformationWithPage] success", gwasGseList);
    }

}
