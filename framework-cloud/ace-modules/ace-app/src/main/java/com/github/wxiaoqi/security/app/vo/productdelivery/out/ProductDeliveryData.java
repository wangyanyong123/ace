package com.github.wxiaoqi.security.app.vo.productdelivery.out;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductDeliveryData implements Serializable {

    private static final long serialVersionUID = 3656648064686301215L;
    private String tenantId;

    private String productId;

    private List<String> cityCodeList;
}
