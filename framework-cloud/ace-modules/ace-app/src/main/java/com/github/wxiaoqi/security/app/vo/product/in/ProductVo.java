package com.github.wxiaoqi.security.app.vo.product.in;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:50 2019/11/12
 * @Modified By:
 */
@Data
public class ProductVo implements Serializable{
	private static final long serialVersionUID = 7641132127225509490L;
	private String productId;
	private String subNum;
}
