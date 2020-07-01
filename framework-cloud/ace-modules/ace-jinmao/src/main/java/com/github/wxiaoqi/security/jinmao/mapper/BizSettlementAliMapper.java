package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizSettlementAli;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.ResultAilVo;
import org.apache.ibatis.annotations.Param;

/**
 * 租户(公司、商户)支付宝账号信息表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
public interface BizSettlementAliMapper extends CommonMapper<BizSettlementAli> {

    /**
     *
     * @param id
     * @return
     */
    ResultAilVo selectAilInfo( @Param("id") String id);


}
