package com.github.wxiaoqi.security.api.vo.order.out;

import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:21 2019/3/28
 * @Modified By:
 */
@Data
public class RefundOrderAuditInfoVo implements Serializable {
	private static final long serialVersionUID = 3928848278499613769L;
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("订单id")
	private String subId;
	@ApiModelProperty("订单编码")
	private String code;
	@ApiModelProperty("商品名称")
	private String productName;
	@ApiModelProperty("客户名称")
	private String userName;

	@ApiModelProperty("客户昵称")
	private String nickName;

	@ApiModelProperty("联系方式")
	private String mobilePhone;
	@ApiModelProperty("商户")
	private String name;
	@ApiModelProperty("数量")
	private String subNum;
	@ApiModelProperty("金额")
	private String cost;
	@ApiModelProperty("下单时间")
	private String createTime;
	@ApiModelProperty("退款状态")
	private String auditStatus;

	@ApiModelProperty("业务类型 0：旧业务 ，1：商品订单，2：服务订单")
	private Integer busType;

	public String getUserName() {
		return StringUtils.isEmpty(userName)?nickName:userName;
	}
}
