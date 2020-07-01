package com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultGoodVisitInfo implements Serializable {

    @ApiModelProperty(value = "好物详情")
    private ResultGoodVisitInfoVo goodVisitVo;

    public ResultGoodVisitInfoVo getGoodVisitVo() {
        return goodVisitVo;
    }

    public void setGoodVisitVo(ResultGoodVisitInfoVo goodVisitVo) {
        this.goodVisitVo = goodVisitVo;
    }
}
