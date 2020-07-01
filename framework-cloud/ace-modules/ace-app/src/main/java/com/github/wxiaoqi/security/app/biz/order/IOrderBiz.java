package com.github.wxiaoqi.security.app.biz.order;

import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;

/**
 * @author: guohao
 * @create: 2020-04-18 12:01
 **/
public interface IOrderBiz {
    BuyProductOutVo createOrder(BuyProductInfo buyProductInfo);

}
