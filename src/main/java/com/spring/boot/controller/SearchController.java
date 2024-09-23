package com.spring.boot.controller;

import com.spring.boot.pojo.Gwas;
import com.spring.boot.pojo.SingleCell;
import com.spring.boot.pojo.vo.SearchVO;
import com.spring.boot.service.SearchService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.PageResult;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
@RequestMapping("/search")
@CrossOrigin
@RestController
public class SearchController {

    private static final Log log = LogFactory.getLog(SearchController.class);

    private SearchService searchService;

    public SearchController() {
    }

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/phenotype/{id}/{field}")
    public Result<List<String>> listInformationByField(@PathVariable String id,
                                                       @PathVariable String field,
                                                       @RequestBody SearchVO searchVO) {
        List<String> infoList = searchService.listInformationByField(id, field, searchVO);
        return ResultUtil.success("[listInformationByField]: success", infoList);
    }

    @PostMapping("/gwas/info")
    public Result<PageResult<Gwas>> listGwasByTraitBased(@RequestBody SearchVO searchVO) {
        PageResult<Gwas> gwasPageResult = searchService.listGwasByTraitBased(searchVO);
        return ResultUtil.success("[listGwasByTraitBased]: success", gwasPageResult);
    }

    @PostMapping("/tissue/info")
    public Result<PageResult<SingleCell>> listSingleCellByTissueBased(@RequestBody SearchVO searchVO) {
        PageResult<SingleCell> singleCellPageResult = searchService.listSingleCellByTissueBased(searchVO);
        return ResultUtil.success("[listSingleCellByTissueBased]: success", singleCellPageResult);
    }

    @PostMapping("/cell_type/info")
    public Result<PageResult<SingleCell>> listSingleCellByCellTypeBased(@RequestBody SearchVO searchVO) {
        PageResult<SingleCell> singleCellPageResult = searchService.listSingleCellByCellTypeBased(searchVO);
        return ResultUtil.success("[listSingleCellByCellTypeBased]: success", singleCellPageResult);
    }

    @PostMapping("/meshId/info")
    public Result<PageResult<Gwas>> listGwasByMeshIdBased(@RequestBody SearchVO searchVO) {
        PageResult<Gwas> gwasPageResult = searchService.listGwasByMeshIdBased(searchVO);
        return ResultUtil.success("[listGwasByMeshIdBased]: success", gwasPageResult);
    }

    @GetMapping("/gene/info")
    public Result<List<String>> listGene() {
        List<String> geneList = searchService.listGene();
        return ResultUtil.success("[listGene]: success", geneList);
    }

}
