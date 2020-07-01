package com.github.wxiaoqi.security.merchant.vo.express;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 快递公司
 * 
 * @author wangyanyong
 * @Date 2020-04-24 16:13:06
 */
@Data
public class BizExpressCompanyVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    private String id;
	
	    //快递公司编码
    private String companyCode;
	
	    //快递公司名称
    private String companyName;
}
