package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_gene_info
 */
@Data
public class GeneInfo implements Serializable {
    private String gene;

    private String ncbiOfficialSymbol;

    private String entrezId;

    private String ensemblId;

    private String reverse;

    private String chromosome;

    private String location;

    private String chr;

    private Integer start;

    private Integer end;

    private String fullName;

    @Serial
    private static final long serialVersionUID = 1L;
}