package com.github.wxiaoqi.security.app.vo.decorete.out;

import com.github.wxiaoqi.security.api.vo.order.out.OperateButton;
import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.app.vo.propertybill.out.UserBillOutVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WoDetail implements Serializable {

    @ApiModelProperty(value = "预约详情")
    private MyDecoreteInfo myDecoreteInfo;

    @ApiModelProperty(value = "工单操作按钮")
    private List<OperateButton> operateButtonList;

    @ApiModelProperty(value = "操作流水日志")
    private List<TransactionLogVo> transactionLogList;

    @ApiModelProperty(value = "支付参数")
    private UserBillOutVo userBillOutVo;
}
