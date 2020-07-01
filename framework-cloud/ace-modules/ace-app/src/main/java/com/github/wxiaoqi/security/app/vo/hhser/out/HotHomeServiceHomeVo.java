package com.github.wxiaoqi.security.app.vo.hhser.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: guohao
 * @create: 2020-04-15 23:51
 **/
@Data
public class HotHomeServiceHomeVo {

    @ApiModelProperty(value = "热门到家标题")
    private String title;

    @ApiModelProperty(value = "热门到家图片")
    private String imgUrl;

    @ApiModelProperty(value = "具体服务id")
    private String busId;
}
