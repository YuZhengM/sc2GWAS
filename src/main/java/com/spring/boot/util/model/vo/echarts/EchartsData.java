package com.spring.boot.util.model.vo.echarts;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * echarts 的 x, y 数据
 *
 * @author Yu Zhengmin
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class EchartsData<X, Y> implements Serializable {

    /**
     * x 轴数据 (X 类型)
     */
    private List<X> xData;

    /**
     * x 轴数据 (Y 类型)
     */
    private List<Y> yData;

    /**
     * x 轴数据
     */
    private List<List<Object>> data;

    private Object extraData;

}
