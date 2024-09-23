package com.spring.boot.pojo.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class EnrichmentVO implements Serializable {

    private String gseId;

    private Integer cluster;

    private Double value;

}
