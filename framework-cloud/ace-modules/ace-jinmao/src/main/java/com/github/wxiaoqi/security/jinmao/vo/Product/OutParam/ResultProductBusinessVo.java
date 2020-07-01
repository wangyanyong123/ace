package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultProductBusinessVo implements Serializable {

    @ApiModelProperty(value = "业务id")
    private String id;
    @ApiModelProperty(value = "业务编码")
    private String busCode;
    @ApiModelProperty(value = "业务名称")
    private String busName;
    @ApiModelProperty(value = "业务类型(1-普通类型，2-团购，3-好物探访，4-疯抢)")
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
