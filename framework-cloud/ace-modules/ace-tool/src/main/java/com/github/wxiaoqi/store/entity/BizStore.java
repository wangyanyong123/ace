package com.github.wxiaoqi.store.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 库存实体类
 */
@Data
@Table(name = "biz_store")
public class BizStore implements Serializable {

    @Id
    private String id;

    //商品Id
    @Column(name = "tenant_id")
    private String tenantId;

    //商品Id
    @Column(name = "product_id")
    private String productId;

    // 规格ID
    @Column(name = "spec_id")
    private String specId;

    //商品编码
    @Column(name = "product_code")
    private String productCode;

    //商品类型：1-商品类型；2-预约类型；
    @Column(name = "product_type")
    private Integer productType;

    //商品名称
    @Column(name = "product_name")
    private String productName;

    //库存数量
    @Column(name = "store_num")
    private Integer storeNum;

    //时间段：0-不限时间（商品类型）；1-上午（预约类型）；2-下午（预约类型）；
    @Column(name = "time_slot")
    private Integer timeSlot;

    //0-删除；1-未删除
    @Column(name = "status")
    private Boolean status;

    //操作人ID
    @Column(name = "create_by")
    private String createBy;

    //
    @Column(name = "create_time")
    private Date createTime;

    //
    @Column(name = "modify_by")
    private String modifyBy;

    //
    @Column(name = "modify_time")
    private Date modifyTime;

    //
    @Column(name = "delete_time")
    private Date deleteTime;

    //是否限制库存:1-限制；0-不限制；
    @Column(name = "is_limit")
    private Boolean isLimit;

}
