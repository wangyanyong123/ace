package com.github.wxiaoqi.security.app.vo.reservation.out;

import com.github.wxiaoqi.security.api.vo.order.out.OperateButton;
import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WoDetail implements Serializable {

    @ApiModelProperty(value = "预约详情")
    private MyReservationInfo myReservationInfo;

    @ApiModelProperty(value = "工单操作按钮")
    private List<OperateButton> operateButtonList;

    @ApiModelProperty(value = "操作流水日志")
    private List<TransactionLogVo> transactionLogList;
}
