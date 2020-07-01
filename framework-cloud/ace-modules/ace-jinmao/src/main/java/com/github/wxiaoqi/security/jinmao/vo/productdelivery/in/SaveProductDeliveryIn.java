package com.github.wxiaoqi.security.jinmao.vo.productdelivery.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.ProductDeliveryData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SaveProductDeliveryIn extends BaseIn {
    private static final long serialVersionUID = -6384242172080246871L;
    //
    private String productId;

    private List<ProductDeliveryData> deliveryList;

    @Override
    public void check() {

        Assert.hasLength(productId,"商品id不能为空");

        Assert.notEmpty(deliveryList,"区域范围不能为空");

        deliveryList.stream().forEach(item->{
            Assert.hasLength(item.getProcCode(),"省份编码不能为空");
            Assert.hasLength(item.getProcName(),"省份名称不能为空");
            Assert.hasLength(item.getCityCode(),"城市编码不能为空");
            Assert.hasLength(item.getCityName(),"城市名称不能为空");
        });

    }

}
