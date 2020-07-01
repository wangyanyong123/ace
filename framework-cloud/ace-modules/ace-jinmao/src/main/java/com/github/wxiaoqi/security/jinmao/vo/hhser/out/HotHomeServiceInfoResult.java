package com.github.wxiaoqi.security.jinmao.vo.hhser.out;

import com.github.wxiaoqi.security.jinmao.vo.common.ObjectIdAndName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: guohao
 * @create: 2020-04-15 15:58
 **/
@Data
public class HotHomeServiceInfoResult implements Serializable {

    private static final long serialVersionUID = 3571220294046915125L;

    @ApiModelProperty(value = "热门到家id")
    private String id;

    @ApiModelProperty(value = "热门到家标题")
    private String title;

    @ApiModelProperty(value = "热门到家标题")
    private String imgUrl;

    @ApiModelProperty(value = "展示位置")
    private Integer position;

    @ApiModelProperty(value = "展示序号")
    private Integer sortNum;

    @ApiModelProperty(value = "商户id")
    private String tenantId;

    @ApiModelProperty(value = "业务id，预约服务id")
    private String busId;

    @ApiModelProperty(value = "业务名称，预约服务名称")
    private String busName;

    @ApiModelProperty(value = "项目信息，")
    private List<ObjectIdAndName> projectList;


}
