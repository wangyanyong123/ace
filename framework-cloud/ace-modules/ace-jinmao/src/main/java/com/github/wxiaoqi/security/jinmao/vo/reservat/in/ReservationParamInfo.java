package com.github.wxiaoqi.security.jinmao.vo.reservat.in;


import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.UpdateSpecVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ReservationParamInfo implements Serializable {

    @ApiModelProperty(value = "商品基本信息")
    private ReservationParam productInfo;
    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;
    @ApiModelProperty(value = "商品规格信息")
    private List<UpdateSpecVo> specInfo;

}
