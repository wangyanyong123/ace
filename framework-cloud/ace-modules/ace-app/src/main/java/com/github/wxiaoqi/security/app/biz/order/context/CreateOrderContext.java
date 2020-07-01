package com.github.wxiaoqi.security.app.biz.order.context;

import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: guohao
 * @create: 2020-04-18 12:08
 **/
@Data
public class CreateOrderContext implements Serializable {
    private static final long serialVersionUID = 5408756957623416624L;

    private String orderId;

    private String userId;

    //是否为0元订单
    private boolean isZeroOrder;

    private BuyProductOutVo buyProductOutVo;
}
