package com.github.wxiaoqi.security.app.vo.coupon;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ProductVo implements Serializable {

    private List<CompanyProductInfoVo> companyProductInfoVo;


}
