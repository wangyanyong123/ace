package com.github.wxiaoqi.security.api.vo.store.pc;

import com.github.wxiaoqi.security.api.vo.store.BaseStore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 库存类
 */
@Data
@ApiModel(description= "库存类")
public class PCStore extends BaseStore {

    @NotEmpty(message="缺少参数productId")
    @ApiModelProperty("产品ID")
    private String productId; // 产品ID

    @NotEmpty(message="缺少参数tenantId")
    @ApiModelProperty("产品ID")
    private String tenantId; // 商户ID

    @NotEmpty(message="缺少参数specId")
    @ApiModelProperty("规格ID")
    private String specId; // 规格ID

    @NotEmpty(message="缺少参数productCode")
    @ApiModelProperty("产品编码")
    private String productCode; // 产品编码

    @NotNull(message="缺少参数productType")
    @ApiModelProperty("产品类型:1-实体商品；2-预约服务；3-活动")
    private Integer productType; // 产品类型：1-实体类型；2-预约类型；3-活动类型；

    @NotEmpty(message="缺少参数productName")
    @ApiModelProperty("产品名称")
    private String productName; // 产品名称

    @NotEmpty(message="缺少参数createBy")
    @ApiModelProperty("操作人ID")
    private String createBy; // 操作人ID,

    @Min(value = 1,message = "库存数输入有误")
    @ApiModelProperty("入库数量")
    private Integer storeNum; // 入库存数量

    @NotNull(message="缺少参数isLimit")
    @ApiModelProperty("库存是否限制：true-限制；false-不限制；如果为true则入库数失效")
    private Boolean isLimit;


    public Integer getStoreNum(){
       return ObjectUtils.isEmpty(storeNum)?0:storeNum;
    }
}
