package com.github.wxiaoqi.security.jinmao.vo.integralscreen.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/29
 */
@Data
public class IntegralScreenInfo implements Serializable {


    private String id;
    @ApiModelProperty(value = "起算积分")
    private String startIntegral;
    @ApiModelProperty(value = "截止积分(-1表示只算起算积分)")
    private String endIntegral;


}
