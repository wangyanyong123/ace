package com.github.wxiaoqi.security.merchant.mapper;

import com.github.wxiaoqi.security.merchant.entity.BizExpressCompany;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.merchant.vo.express.BizExpressCompanyVO;

import java.util.List;

/**
 * 快递公司
 * 
 * @author wangyanyong
 * @Date 2020-04-24 16:13:06
 */
public interface BizExpressCompanyMapper extends CommonMapper<BizExpressCompany> {

    /**
     * 查询快递公司
     * @return
     */
    List<BizExpressCompanyVO> queryExpressCompanyList();
}
