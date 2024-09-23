package com.spring.boot.pojo.vo;

import com.spring.boot.pojo.SingleCell;
import com.spring.boot.util.model.vo.FieldNumber;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 得到所有 Data Browse 左边类型和数量
 *
 * @author Yu Zhengmin
 */
@Builder
@ToString
@Data
public class DataBrowseResultVO<T> implements Serializable {

    private List<FieldNumber> domainList;

    private List<FieldNumber> phenotypeList;

    private List<FieldNumber> biosampleTypeList;

    private List<FieldNumber> tissueTypeList;

    private List<FieldNumber> cellTypeList;

    private List<T> dataBrowseDataList;

}
