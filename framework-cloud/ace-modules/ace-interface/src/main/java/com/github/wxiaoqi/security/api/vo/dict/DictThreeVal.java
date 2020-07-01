package com.github.wxiaoqi.security.api.vo.dict;

import com.github.wxiaoqi.security.api.vo.face.UnitInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangxl
 * @Description:
 * @Date: Created in 2019/2/13
 * @Modified By:
 */
@Data
public class DictThreeVal implements Serializable {


	private static final long serialVersionUID = 6089313251232986837L;
	@ApiModelProperty("一级分类编码值")
	private String oneCategoryCode;

	@ApiModelProperty("一级分类名称")
	private String oneCategoryName;

	@ApiModelProperty("二级分类编码值")
	private String twoCategoryCode;

	@ApiModelProperty("二级分类名称")
	private String twoCategoryName;

	@ApiModelProperty("三级分类编码值")
	private String threeCategoryCode;

	@ApiModelProperty("三级分类名称")
	private String threeCategoryName;
}
