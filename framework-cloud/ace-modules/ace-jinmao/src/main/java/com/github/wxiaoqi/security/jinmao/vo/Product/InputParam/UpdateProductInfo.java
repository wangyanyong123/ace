package com.github.wxiaoqi.security.jinmao.vo.Product.InputParam;


import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateProductInfo extends BaseIn {

    private static final long serialVersionUID = 7728119640457072770L;
    @ApiModelProperty(value = "商品基本信息")
    private UpdateProductInfoVo productInfo;
    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;
    @ApiModelProperty(value = "商品规格信息")
    private List<UpdateSpecVo> specInfo;


    @Override
    public void check() {
        Assert.notEmpty(specInfo,"请设置商品规格信息");
        specInfo.forEach(item->{
            Assert.hasLength(item.getUnit(),"请设置规格单位");
        });
    }
}
