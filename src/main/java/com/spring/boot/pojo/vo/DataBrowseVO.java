package com.spring.boot.pojo.vo;

import com.spring.boot.util.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import static com.spring.boot.util.constant.ApplicationConstant.ALL_DATA_SYMBOL;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class DataBrowseVO implements Serializable {

    private String domain;

    private String phenotype;

    private String biosampleType;

    private String tissueType;

    private String cellType;

    private String type;

    public DataBrowseVO(DataBrowseVO dataBrowseVO) {
        if (StringUtil.isEqual(dataBrowseVO.type, "single_cell")) {
            this.domain = StringUtil.isEmpty(dataBrowseVO.getDomain()) ? ALL_DATA_SYMBOL : dataBrowseVO.getDomain();
            this.phenotype = StringUtil.isEmpty(dataBrowseVO.getPhenotype()) ? ALL_DATA_SYMBOL : dataBrowseVO.getPhenotype();
            this.cellType = StringUtil.isEmpty(dataBrowseVO.getCellType()) ? ALL_DATA_SYMBOL : dataBrowseVO.getCellType();
            this.biosampleType = StringUtil.isEmpty(dataBrowseVO.getBiosampleType()) ? ALL_DATA_SYMBOL : dataBrowseVO.getBiosampleType();
            this.tissueType = StringUtil.isEmpty(dataBrowseVO.getTissueType()) ? ALL_DATA_SYMBOL : dataBrowseVO.getTissueType();
        } else {
            this.domain = StringUtil.isEmpty(dataBrowseVO.getDomain()) ? ALL_DATA_SYMBOL : dataBrowseVO.getDomain();
            this.phenotype = StringUtil.isEmpty(dataBrowseVO.getPhenotype()) ? ALL_DATA_SYMBOL : dataBrowseVO.getPhenotype();
        }
        this.type = dataBrowseVO.type;
    }

    public boolean isPhenotypeEmpty() {
        return StringUtil.isEqual(phenotype, ALL_DATA_SYMBOL) || StringUtil.isEmpty(phenotype);
    }

    public boolean isDomainEmpty() {
        return StringUtil.isEqual(domain, ALL_DATA_SYMBOL) || StringUtil.isEmpty(domain);
    }

    public boolean isBiosampleTypeEmpty() {
        return StringUtil.isEqual(biosampleType, ALL_DATA_SYMBOL) || StringUtil.isEmpty(biosampleType);
    }

    public boolean isTissueTypeEmpty() {
        return StringUtil.isEqual(biosampleType, ALL_DATA_SYMBOL) || StringUtil.isEmpty(tissueType);
    }

    public boolean isCellTypeEmpty() {
        return StringUtil.isEqual(cellType, ALL_DATA_SYMBOL) || StringUtil.isEmpty(cellType);
    }

    public boolean isEmpty() {
        return StringUtil.isEqual(type, "single_cell") ?
                isPhenotypeEmpty() && isBiosampleTypeEmpty() && isTissueTypeEmpty() && isDomainEmpty()  && isCellTypeEmpty() :
                isDomainEmpty() && isPhenotypeEmpty();
    }

}
