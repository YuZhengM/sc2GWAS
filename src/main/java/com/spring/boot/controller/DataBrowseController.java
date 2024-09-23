package com.spring.boot.controller;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.pojo.vo.DataBrowseResultVO;
import com.spring.boot.pojo.vo.DataBrowseVO;
import com.spring.boot.service.DataBrowseService;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
@RequestMapping("/data_browse")
@CrossOrigin
@RestController
public class DataBrowseController {

    private DataBrowseService dataBrowseService;

    public DataBrowseController() {
    }

    @Autowired
    public DataBrowseController(DataBrowseService dataBrowseService) {
        this.dataBrowseService = dataBrowseService;
    }

    @GetMapping("/gwas")
    public Result<List<Gwas>> listGwasBrowseData() {
        List<Gwas> singleCellList = dataBrowseService.listGwasBrowseData();
        return ResultUtil.success("[listGwasBrowseData]: 查询数据", singleCellList);
    }

    @PostMapping("/gwas")
    public Result<DataBrowseResultVO<Gwas>> listGwasBrowseDataAndCount(@RequestBody DataBrowseVO dataBrowseVO) {
        DataBrowseResultVO<Gwas> dataBrowseResult = dataBrowseService.listGwasBrowseDataAndCount(dataBrowseVO);
        return ResultUtil.success("[listGwasBrowseDataAndCount]: 查询数据", dataBrowseResult);
    }

    @GetMapping("/sample")
    public Result<List<SingleCell>> listSingleCellBrowseData() {
        List<SingleCell> gwasList = dataBrowseService.listSingleCellBrowseData();
        return ResultUtil.success("[listSingleCellBrowseData]: 查询数据", gwasList);
    }

    @PostMapping("/sample")
    public Result<DataBrowseResultVO<SingleCell>> listSingleCellBrowseDataAndCount(@RequestBody DataBrowseVO dataBrowseVO) {
        DataBrowseResultVO<SingleCell> dataBrowseResult = dataBrowseService.listSingleCellBrowseDataAndCount(dataBrowseVO);
        return ResultUtil.success("[listSingleCellBrowseDataAndCount]: 查询数据", dataBrowseResult);
    }

}
