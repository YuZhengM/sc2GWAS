package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_cell_type_differentiation_score
 */
@Data
public class CellTypeDifferentiationScore implements Serializable {
    private String gwasId;

    private String gseId;

    private Double variance;

    private Double stdDev;

    private Double range;

    private String tissue;

    private String domain;

    private String trait;

    private String cellType;

    @Serial
    private static final long serialVersionUID = 1L;
}