package com.github.wxiaoqi.security.jinmao.vo.chamberTopic.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/5
 */
@Data
public class ChanberTopicTagParam implements Serializable {

    @ApiModelProperty(value = "话题标签id(编辑时传)")
    private String id;
    @ApiModelProperty(value = "话题标签名称")
    private String topicTagName;
    @ApiModelProperty(value = "排序")
    private String viewSort;
    @ApiModelProperty(value = "所属项目")
    private List<ResultProjectVo> projectVo;

}
