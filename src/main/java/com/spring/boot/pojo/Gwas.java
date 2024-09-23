package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * t_gwas
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Gwas implements Serializable {

    private String gwasId;

    private String domain;

    private String chapterLevel;

    private String subChapterLevel;

    private String trait;

    private Integer sampleNumber;

    private String population;

    private String pmid;

    private Integer year;

    private Integer caseSampleNumber;

    private Integer controlSampleNumber;

    private Integer snpsNumber;

    private String genome;

    private Integer hitsNumber;

    private String note;

    private String downloadUrl;

    private String meshId;

    @Serial
    private static final long serialVersionUID = 1L;
}