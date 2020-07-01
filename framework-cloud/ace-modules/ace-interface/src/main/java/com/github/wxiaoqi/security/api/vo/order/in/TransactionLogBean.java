package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionLogBean {

	@ApiModelProperty(value = "当前步骤")
	private String currStep;
    //描述文本(支持电话号码、链接、图片等)<tel>18820296127</tel><a href="">http://www.baidu.com</a><img></img>
	@ApiModelProperty(value = "描述文本(支持电话号码、链接、图片等)<tel>18820296127</tel><a href=\"\">http://www.baidu.com</a><img></img>")
    private String desc;
	@ApiModelProperty(value = "联系人")
	private String conName;
	@ApiModelProperty(value = "联系电话")
	private String conTel;
	@ApiModelProperty(value = "图片ID，多个图片用逗号分隔")
	private String imgIds;
	@ApiModelProperty(value = "评价分值（0~5分）")
	private Integer appraisalVal;
	@ApiModelProperty(value = "是否准时到岗(0-未评价，1-是，2-否)")
	private String isArriveOntime;
}
