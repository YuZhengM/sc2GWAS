package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_distinct_cluster_cell_type
 */
@Data
public class DistinctClusterCellType implements Serializable {
    private String gseId;

    private String cellType;

    private String disease;

    @Serial
    private static final long serialVersionUID = 1L;
}