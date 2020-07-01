package com.github.wxiaoqi.security.app.vo.shopping.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpecDataForAddCart {

    private String productId;

    private String productName;

    private String tenantId;

    private String tenantName;
    //  商品销售不在与项目挂钩，与销售地区有关
//    private String projectId;

    private String specId;

    private String specName;

    private BigDecimal price;

}
