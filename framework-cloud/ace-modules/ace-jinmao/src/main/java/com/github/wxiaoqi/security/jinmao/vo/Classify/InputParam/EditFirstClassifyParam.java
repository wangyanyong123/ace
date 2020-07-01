package com.github.wxiaoqi.security.jinmao.vo.Classify.InputParam;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public class EditFirstClassifyParam extends BaseIn {

    @ApiModelProperty(value = "一级分类id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "展示图片")
    private String imgUrl;

    @Override
    public void check() {
        Assert.hasLength(id,"一级分类id不能为空");
    }
}
