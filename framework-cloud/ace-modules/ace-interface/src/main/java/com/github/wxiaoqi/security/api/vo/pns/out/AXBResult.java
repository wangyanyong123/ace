package com.github.wxiaoqi.security.api.vo.pns.out;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.wxiaoqi.security.api.vo.constant.AXBResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/** 接口返回的结果
 * @author Mr.AG
 * @version 2018-05-16 20:56:32
 * @email 463540703@qq.com
 */
@Data
@ApiModel(description= "平台响应数据")
public class AXBResult implements Serializable {
    @ApiModelProperty(value = "平台返回的错误码，0表示成功")
    private Integer code; //平台返回的错误码，0表示成功
    @ApiModelProperty(value = "平台放回的错误消息")
    private String msg; //平台放回的错误消息
    @ApiModelProperty(value = "平台返回的响应数据")
    private AXBResultData data; //平台返回的响应数据

    //判断绑定是否成功
    @JsonIgnore
    public boolean isSuccess(){
        if(code != null){
            boolean flag = AXBResponseCodeEnum.SUCCESS.getKey().equals(code);
            return flag;
        }
       return false;
    }

    //是否绑定关系过期或不存在
    @JsonIgnore
    public boolean isBindExpiredOrNotExist(){
        if(code != null){
            boolean flag = AXBResponseCodeEnum.EXPIRED_OR_NOT_EXIST.getKey().equals(code);
            return flag;
        }
        return false;
    }

    //是否号码已有相关绑定关系
    @JsonIgnore
    public boolean isNumberBountt(){
        if(code != null){
            boolean flag = AXBResponseCodeEnum.NUMBER_BOUND.getKey().equals(code);
            return flag;
        }
        return false;
    }
}
