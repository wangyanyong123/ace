package com.github.wxiaoqi.security.schedulewo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.schedulewo.entity.BizWo;
import com.github.wxiaoqi.security.schedulewo.vo.NoticeWoInfoVo;
import com.github.wxiaoqi.security.schedulewo.vo.SrsWo;
import com.github.wxiaoqi.security.schedulewo.vo.SubVo;
import com.github.wxiaoqi.security.schedulewo.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工单
 * 
 * @author zxl
 * @Date 2018-11-27 14:57:49
 */
public interface BizWoMapper extends CommonMapper<BizWo> {


    public List<SrsWo> getWOListForSchedule(SrsWo srsWO);

    /**
     * 扫描未接的转单工单
     * @param srsWO
     * @return
     */
    public List<SrsWo> getTurnWOListForSchedule(SrsWo srsWO);

    public SrsWo getWoById(String woId);

    public Map<String,Object> getWoInfoById(String woId);

    /**
     * 修改工单的状态
     */
    public void updateWoStatus(Map<String, Object> map);

    /**
     * 指派工单
     */
    public void updateWoOwner(Map<String, Object> map);

    /**
     * 修改工单处理方式
     * @param wo
     */
    public void updateWoDealType(SrsWo wo);

    /**
     * 详情
     * @param wo
     * @return
     */
    public Map<String, Object> getWoDetail(SrsWo wo);

    /**
     * 详情->工序
     * @param wo
     * @return
     */
    public List<Map<String, Object>> getWoSetpList(SrsWo wo);

    /**
     * 详情-> 操作
     * @param wo
     * @return
     */
    public List<Map<String, Object>> getWoActionList(Map<String, Object> wo);

    /**
     * 大厅
     * @param wo
     * @return
     */
    public List<Map<String, Object>> getWoSaloon(Map<String, Object> wo);

    /**
     * 查找特定的服务资源
     * @param wo
     * @return
     */
//    public Srs getAppointSrs(SrsWo wo);

    /**
     * 判断该用户是否给对应的清洁人员推送消息
     * @param woId
     * @param objectId
     */
    int isCanCleanShowerRoom(@Param("woId")String woId,@Param("objectId")String objectId);

    /**
     * 根据工单ID和操作类型查询操作ID
     * @param woId
     * @param operateType 按钮操作类型
     * @return
     */
    public Map<String, Object> getOperateIdByWoIdAndOperateType(@Param("woId")String woId,@Param("operateType")String operateType);

	List<NoticeWoInfoVo> needNoticeWo(@Param("list") List<String> list, @Param("outtime")int outtime);

	List<UserInfoVo> getUserInfoListByProjectId(@Param("projectId") String projectId);

	int insertNoticeWO(String id);

	List<SubVo> unconfirmedWo(@Param("list")List<String> list, @Param("unconfirmed")int unconfirmed);

    List<SubVo> missedOrderWo(@Param("list")List<String> list, @Param("missedorder")int missedorder);
}
