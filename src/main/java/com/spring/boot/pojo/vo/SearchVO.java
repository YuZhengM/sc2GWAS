package com.spring.boot.pojo.vo;

import com.spring.boot.util.util.result.Page;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class SearchVO implements Serializable {

    private String trait;

    private String domain;

    private String biosampleType;

    private String tissueType;

    private String gseId;

    private String disease;

    private String cellType;

    private String meshId;

    private Page page;

}
