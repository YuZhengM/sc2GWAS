package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_gene_disease_association
 */
@Data
public class GeneDiseaseAssociation implements Serializable {
    private String geneSymbol;

    private String diseaseId;

    private String diseaseName;

    private String diseaseType;

    private String diseaseClass;

    private String diseaseSemanticType;

    private Double score;

    private Integer nofSnps;

    private String source;

    @Serial
    private static final long serialVersionUID = 1L;
}
