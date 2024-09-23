package com.spring.boot.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * t_go
 *
 * @author Admin
 */
@Data
public class Go implements Serializable {
    private String gseId;

    private Integer cluster;

    private String description;

    private Double geneRatio;

    private Double pValue;

    private Double pAdjust;

    private Double qValue;

    private Integer count;

    private String cellType;

    @Serial
    private static final long serialVersionUID = 1L;
}