package com.github.wxiaoqi.security.jinmao.vo.adHomePage.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdAppHomePageList implements Serializable {

    @ApiModelProperty(value = "每个广告的详细信息")
    private List<AdHomePageInfo>  adList;
    @ApiModelProperty(value = "时间总数")
    private Integer totalTime;
}
