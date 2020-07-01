package com.github.wxiaoqi.security.jinmao.vo.hhser.in;

import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: guohao
 * @create: 2020-04-15 14:36
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchHotHomeServiceIn extends BaseQueryIn {
    private static final long serialVersionUID = 6594001228470547740L;

    @ApiModelProperty(value = "项目ID")
    private String projectId;

    @ApiModelProperty(value = "标题")
    private String title;

    @Override
    public void doCheck() {
    }
}
