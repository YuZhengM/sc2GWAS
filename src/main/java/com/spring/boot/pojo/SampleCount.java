package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_sample_cell_count
 */
@Data
public class SampleCount implements Serializable {

    private String gseId;

    private String gsmId;

    private Integer cluster;

    private String cellType;

    private Integer count;

    @Serial
    private static final long serialVersionUID = 1L;
}
