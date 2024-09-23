package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_super_enhancer_sedbv2
 */
@Data
public class SuperEnhancerSedbv2 implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String sedbId;

    private String tissueType;

    private String cellType;

    @Serial
    private static final long serialVersionUID = 1L;
}