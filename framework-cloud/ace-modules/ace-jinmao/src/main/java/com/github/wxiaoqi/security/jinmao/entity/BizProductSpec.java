package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品规格表
 * 
 * @author zxl
 * @Date 2018-12-05 09:58:44
 */
@Data
@Table(name = "biz_product_spec")
public class BizProductSpec implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //商品ID
    @Column(name = "product_id")
    private String productId;
	
	    //规格名称
    @Column(name = "spec_name")
    private String specName;
	
	    //原价
    @Column(name = "original_price")
    private BigDecimal originalPrice;
	
	    //单价
    @Column(name = "price")
    private BigDecimal price;
	
	    //最小量
    @Column(name = "lowest_num")
    private BigDecimal lowestNum;
	
	    //单位
    @Column(name = "unit")
    private String unit;
	
	    //规格logo
    @Column(name = "spec_image")
    private String specImage;

	//排序
	@Column(name = "sort")
    private Integer sort;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_Stamp")
    private Date timeStamp;
	
	    //创建人
    @Column(name = "create_By")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_Time")
    private Date modifyTime;

	@Column(name = "spec_Type_Code")
    private String specTypeCode;

	@Column(name = "spec_Type_Val")
	private String specTypeVal;

}
