package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * t_single_cell
 */
@ToString
@Data
public class SingleCell implements Serializable {
    private String gseId;

    private String gsmId;

    private String domain;

    private String disease;

    private String biosampleType;

    private String tissueType;

    private String cellType;

    private Integer cellNumber;

    private String pmid;

    private String platform;

    private Integer sampleNumber;

    private String meshId;

    private Integer label;

    @Serial
    private static final long serialVersionUID = 1L;
}