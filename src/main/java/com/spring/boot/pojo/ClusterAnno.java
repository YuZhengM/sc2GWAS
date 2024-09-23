package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_cluster_anno
 */
@Data
public class ClusterAnno implements Serializable {
    private String gseId;

    private String barcode;

    private Double tsne1;

    private Double tsne2;

    private Integer cluster;

    private String gsmId;

    private Double umap1;

    private Double umap2;

    private String cellType;

    private Integer index;

    @Serial
    private static final long serialVersionUID = 1L;
}