package com.github.wxiaoqi.security.app.biz.order.context;

import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: guohao
 * @create: 2020-04-18 12:08
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateProductOrderContext extends CreateOrderContext {

    private static final long serialVersionUID = -1602528397760069946L;
    private String projectId;

    private int appType;

    private int orderType;

    //收货人信息
    private RecipientInfo recipientInfo;

    //商户信息
    private List<TenantInfo> tenantList;
    //

    List<OrderIdResult> orderIdResultList;

}
