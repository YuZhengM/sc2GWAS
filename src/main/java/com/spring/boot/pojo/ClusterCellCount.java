package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_cluster_cell_count
 */
@Data
public class ClusterCellCount implements Serializable {
    private String gseId;

    private Integer cluster;

    private String cellType;

    private Integer count;

    @Serial
    private static final long serialVersionUID = 1L;
}