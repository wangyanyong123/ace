package com.github.wxiaoqi.security.api.vo.store.reservation;

import com.github.wxiaoqi.security.api.vo.store.BaseStore;
import com.github.wxiaoqi.security.common.util.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 对接库存修改方法
 */
@Data
@ApiModel(description= "对内库存修改方法")
public class ReservationStore extends BaseStore {

    @NotEmpty(message="缺少参数specId")
    @ApiModelProperty("规格ID")
    private String specId; // 规格ID
    @NotNull(message="缺少参数accessNum")
    @ApiModelProperty("出入库数量：正式表示入库；负数表示出库")
    private Integer accessNum; // 出入库存数量
    @ApiModelProperty("订单ID")
    private String orderId;
    @ApiModelProperty("操作人ID")
    private String createBy;
    @ApiModelProperty("预约服务时间")
    private Date reservationTime;

    //时间段：0-不限时间（商品类型）；1-上午（预约类型）；2-下午（预约类型）
    public Integer getTimeSlot(){
        return DateUtils.isAmOrPm(reservationTime);
    }
}
