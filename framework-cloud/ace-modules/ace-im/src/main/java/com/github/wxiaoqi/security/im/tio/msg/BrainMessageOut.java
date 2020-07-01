package com.github.wxiaoqi.security.im.tio.msg;

import com.github.wxiaoqi.security.im.vo.brainpower.out.BrainpowerQuestionVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: huangxl
 * @Description:
 */
@Data
public class BrainMessageOut implements Serializable {

	private static final long serialVersionUID = 8652724224231284348L;

	@ApiModelProperty(value = "类型(1-问题，2-功能，3-仅仅是文字)",required = true)
	private String type;
	@ApiModelProperty(value = "id，问题/功能id",required = false)
	private String id;
	@ApiModelProperty(value = "问题/功能/输入的文字",required = true)
	private String text;
	@ApiModelProperty(value = "答案",required = true)
	private String answer;
	@ApiModelProperty(value = "链接页面编码",required = false)
	private String jumpCode;
	@ApiModelProperty(value = "跳转链接名称",required = false)
	private String jumpLink;
	@ApiModelProperty(value = "图片(逗号分隔)",required = false)
	private String picture;
	@ApiModelProperty(value = "是否解决(0-未操作,1-未解决,2-已解决)",required = false)
	private String isSolve;
	@ApiModelProperty(value = "推荐问题列表",required = false)
	private List<BrainpowerQuestionVo> questionList;

}
