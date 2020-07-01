package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BaseTenantContract;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.ResultBusinessInfo;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.ResultContractVo;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.ResultProjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 商户协议表
 * 
 * @author zxl
 * @Date 2018-12-05 10:04:44
 */
public interface BaseTenantContractMapper extends CommonMapper<BaseTenantContract> {

    /**
     * 根据ID查询协议详情
     * @param id
     * @return
     */
    ResultContractVo selectContractInfo(String id);

    String getPhotoInfo(@Param("url") String url);

    /**
     * 根据ID查询协议服务
     * @param id
     * @return
     */
    List<ResultProjectInfo> selectContractProjectById(String id);

    /**
     * 根据ID查询协议业务
     * @param id
     * @return
     */
    List<ResultBusinessInfo> selectContractBusinessById(String id);

    List<ResultBusinessVo> selectContractBusinessInfo();
}
