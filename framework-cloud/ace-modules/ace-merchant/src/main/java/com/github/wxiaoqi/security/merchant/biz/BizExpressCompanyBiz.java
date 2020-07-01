package com.github.wxiaoqi.security.merchant.biz;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.merchant.vo.express.BizExpressCompanyVO;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.merchant.entity.BizExpressCompany;
import com.github.wxiaoqi.security.merchant.mapper.BizExpressCompanyMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 快递公司
 *
 * @author wangyanyong
 * @Date 2020-04-24 16:13:06
 */
@Service
public class BizExpressCompanyBiz extends BusinessBiz<BizExpressCompanyMapper,BizExpressCompany> {


    /**
     * 查询快递公司
     * @return
     */
    public ObjectRestResponse queryExpressCompanyList(){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<BizExpressCompanyVO> bizExpressCompanyVOList = this.mapper.queryExpressCompanyList();
        objectRestResponse.setData(bizExpressCompanyVOList);
        return objectRestResponse;
    }
}