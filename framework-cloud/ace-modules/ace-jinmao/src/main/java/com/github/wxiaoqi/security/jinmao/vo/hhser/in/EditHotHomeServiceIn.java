package com.github.wxiaoqi.security.jinmao.vo.hhser.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑服务到家实体
 *
 * @author: guohao
 * @create: 2020-04-14 19:40
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class EditHotHomeServiceIn extends BaseIn {
    private static final long serialVersionUID = -4022590659730668835L;

    @ApiModelProperty(value = "ID 编辑时必传")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    @ApiModelProperty(value = "展示位置 1：首页， 新增时默认为1:")
    private Integer position;

    @ApiModelProperty(value = "展示顺序, 正序排序 ,默认为1")
    private Integer sortNum;

    @ApiModelProperty(value = "业务ID， 预约服务ID")
    private String busId;

    @ApiModelProperty(value = "项目集合")
    private List<String> projectIdList;


    @Override
    public void check() {
        if (StringUtils.isEmpty(id)) {
            Assert.hasLength(title, "请填写标题");
            Assert.hasLength(imgUrl, "请上传图片");
            Assert.hasLength(busId, "请选择具体服务");
            if (position == null) {
                setPosition(1);
            }
            if (sortNum == null) {
                setSortNum(1);
            }
            Assert.notEmpty(projectIdList, "请选择项目。");
        }

    }

}
