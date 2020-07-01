package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizSettlementWechat;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.ResultWechatVo;
import org.apache.ibatis.annotations.Param;

/**
 * 租户(公司、商户)微信账号信息表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
public interface BizSettlementWechatMapper extends CommonMapper<BizSettlementWechat> {

    /**
     * 根据租户id查询微信账号详情
     * @param id
     * @return
     */
    ResultWechatVo selectWechatInfo(@Param("id") String id);

    int updateByPrimarySelective(BizSettlementWechat wechat);
	
}
