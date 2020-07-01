package com.github.wxiaoqi.security.jinmao.vo.reservat.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultSpecVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultReservationInfo implements Serializable {

    @ApiModelProperty(value = "商品基本信息")
    private ReservationInfo productInfo;
    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;
    @ApiModelProperty(value = "商品规格信息")
    private List<ResultSpecVo> specInfo;


}
