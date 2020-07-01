package com.github.wxiaoqi.security.im.vo.housekeeperchat.out;

import com.github.wxiaoqi.security.im.vo.userchat.out.HouseKeeperVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:00 2018/12/19
 * @Modified By:
 */
@Data
public class HouseholdVo extends HouseKeeperVo implements Serializable {
	private static final long serialVersionUID = 7780012007542708882L;
	@ApiModelProperty(value = "身份类型：1、家属；2、租客；3、业主" ,required = true ,example = "3")
	private String identityType;
}
