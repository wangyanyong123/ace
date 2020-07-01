package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizDecoreteApply;
import com.github.wxiaoqi.security.app.vo.decorete.out.DecoreteVo;
import com.github.wxiaoqi.security.app.vo.decorete.out.MyDecoreteInfo;
import com.github.wxiaoqi.security.app.vo.decorete.out.MyDecoreteVo;
import com.github.wxiaoqi.security.app.vo.propertybill.out.UserBillOutVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 装修监理申请表
 * 
 * @author huangxl
 * @Date 2019-04-01 15:20:10
 */
public interface BizDecoreteApplyMapper extends CommonMapper<BizDecoreteApply> {

    /**
     * 查询当前项目下的装修监理服务
     * @param projectId
     * @return
     */
    DecoreteVo selectDecoreteList(String projectId);

    /**
     * 查询装修监理服务的详情
     * @param id
     * @return
     */
    DecoreteVo selectDecoreteInfo(String id);


    /**
     * 查询当前项目下的用户预约装修监理记录
     * @param userId
     * @param projectId
     * @return
     */
    List<MyDecoreteVo> selectUserDecoreteListByProjectId(@Param("userId") String userId, @Param("projectId") String projectId,
                                                         @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查看预约详情
     * @param woId
     * @return
     */
    MyDecoreteInfo selectUserDecoreteInfo(String woId);


    /**
     * 支付宝、微信回调通知成功后 待支付状态改为待接单
     * @param userId
     * @param subId
     * @return
     */
    int updateDecoreteApplyStatus(@Param("userId") String userId, @Param("subId") String subId);


    UserBillOutVo selectActualInfoBySubId(String subId);

    String selectBusNameById(String busId);
 }
