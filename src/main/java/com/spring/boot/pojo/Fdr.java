package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_cell_type_fdr
 */
@Data
public class Fdr implements Serializable {
    private String gseId;

    private String gwasId;

    private String cellType;

    private Integer totalCells;

    private Integer significantCellsFdr05;

    private Double propSignificantFdr05;

    private Integer significantCellsFdr1;

    private Double propSignificantFdr1;

    private Integer significantCellsFdr2;

    private Double propSignificantFdr2;

    @Serial
    private static final long serialVersionUID = 1L;
}