package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_distinct_cell_type
 */
@Data
public class DistinctCellType implements Serializable {
    private String gseId;

    private String cellType;

    private Integer count;

    @Serial
    private static final long serialVersionUID = 1L;
}