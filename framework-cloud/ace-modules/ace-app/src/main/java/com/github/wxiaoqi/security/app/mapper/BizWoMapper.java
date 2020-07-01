package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.dict.DictThreeVal;
import com.github.wxiaoqi.security.api.vo.order.out.PlanWoExcelVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoListForWebVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoListVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.app.entity.BizWo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工单表
 *
 * @author huangxl
 * @Date 2018-12-03 15:00:10
 */
public interface BizWoMapper extends CommonMapper<BizWo> {

    /**
     * 根据工单ID列表查询我的工单
     * @param map
     * @return
     */
    List<WoListVo> selectWoListByWoId(Map<?, ?> map);

    /**
     *按工单类型统计我的工单列表
     * @param map
     * @return
     */
    List<Map<String,Integer>> selectMyWoListCountByWoType(Map<?, ?> map);

    /**
     * 根据业务查询我的订单列表
     * @param map
     * @return
     */
    List<WoListVo> selectWoListByUserId(Map<?, ?> map);



    /**
     * 根据状态查询我的工单列表
     * @param map
     * @return
     */
    List<WoListVo> selectMyWoList(Map<?, ?> map);

    /**
     * 查询公司拥有的物业人员用户及拥有技能
     * @param map
     * @return
     */
    List<Map<String,Object>> getPropertyUserList(Map<?, ?> map);

    /**
     * 查询工单调度基本信息
     * @param woId
     * @return
     */
    List<Map<String,Object>> getWoSkillInfo(String woId);

    /**
     * 查询公司拥有的客服人员
     * @param companyId
     * @return
     */
    List<String> getCustomerUserList(String companyId);

    /**
     * 查询公司拥有的商业服务人员
     * @param companyId
     * @return
     */
    List<String> getBusinessUserList(String companyId);

    /**
     * 查询客服、商业人员的待接工单
     * @param companyId
     * @return
     */
    List<String> getWaitWoByCompanyId(@Param("companyId") String companyId,@Param("projectId") String projectId);

    /**
     * 查询物业人员的待接工单
     * @param userId
     * @return
     */
    List<Map<String,String>> getWaitWoByPropertyUserId(@Param("userId") String userId,@Param("projectId")String projectId);

    /**
     * 获取当前项目管理公司
     * @param companyId
     * @return
     */
    String getCompanyIdByProjectId(String companyId);

    /**
     * 查询投诉、报修工单列表
     * @param map
     * @return
     */
    List<WoListForWebVo> queryIncidentList(Map<?, ?> map);

    /**
     * 查询投诉、报修工单列表记录总数
     * @param map
     * @return
     */
    int queryIncidentListTotal(Map<?, ?> map);

    /**
     * 获取工单详情to Web
     * @param woId
     * @return
     */
    WoListForWebVo getWoDetailForWeb(String woId);

    /**
     * 获取业务数据字典三级分类的名称和值
     * @param val 三级字典值
     * @param pid 一级的父ID编码
     * @return
     */
    DictThreeVal getBizDictThreeVal(@Param("val")String val, @Param("pid")String pid);

    /**
     * 获取业务数据字典两级分类的名称和值
     * @param val 三级字典值
     * @param pid 三级的父ID编码
     * @return
     */
    DictThreeVal getBizDictTwoVal(@Param("val")String val, @Param("pid")String pid);

    /**
     * 查询工单类型
     * @param id
     * @return
     */
    String selectWoTypeById(String id);

    /**
     * 根据工单id查询服务名称
     * @param id
     * @return
     */
    String seelctResrrevaNameById(String id);


    List<PlanWoExcelVo> getPlanWoExcel(Map<?, ?> map);
}
