package com.github.wxiaoqi.security.jinmao.vo.hhser.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: guohao
 * @create: 2020-04-15 14:53
 **/
@Data
public class SearchHotHomeServiceResult {

    @ApiModelProperty(value = "热门到家id")
    private String id;

    @ApiModelProperty(value = "热门到家标题")
    private String title;

    @ApiModelProperty(value = "热门到家展示位置")
    private Integer position;

    @ApiModelProperty(value = "热门到家 排序")
    private Integer sortNum;

}
