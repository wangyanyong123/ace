package com.github.wxiaoqi.security.schedulewo.biz;


import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.schedulewo.constant.DBConstant;
import com.github.wxiaoqi.security.schedulewo.constant.OperateConstants;
import com.github.wxiaoqi.security.schedulewo.entity.BizWo;
import com.github.wxiaoqi.security.schedulewo.feign.OrderEngineFegin;
import com.github.wxiaoqi.security.schedulewo.mapper.BizWoMapper;
import com.github.wxiaoqi.security.schedulewo.vo.DoOperateByTypeVo;
import com.github.wxiaoqi.security.schedulewo.vo.SrsWo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单
 *
 * @author zxl
 * @Date 2018-11-23 13:54:35
 */
@Service
@Slf4j
public class BizWoBiz extends BusinessBiz<BizWoMapper, BizWo> {

    @Autowired
    private BizWoMapper bizWoMapper;
    @Autowired
    private OrderEngineFegin orderEngineFegin;
    /**
     * 调度工单
     * @param srsWO
     * @return
     */
    public synchronized List<SrsWo> updateAndGetWOList(SrsWo srsWO){
        List<SrsWo> woList = bizWoMapper.getWOListForSchedule(srsWO);
        updateWoStatus(woList, DBConstant.WoStatus.YSL);//修改工单状态
        return woList;
    }

    public synchronized List<SrsWo> updateAndGetTurnWOList(SrsWo srsWO) {
        List<SrsWo> woList = bizWoMapper.getTurnWOListForSchedule(srsWO);
        //是否锁住待接转单工单(0-未锁住,1-已锁住)
       // updateWoLockStatus(woList,"1");//锁住待接的转单工单标记
        return woList;
    }
    public void updateWoDealType(SrsWo srsWO) {
        bizWoMapper.updateWoDealType(srsWO);
    }

    /**
     * 修改工单的状态
     * @param woList
     * @param woStatus
     */
    public void updateWoStatus(List<SrsWo> woList, DBConstant.WoStatus woStatus) {

        if(woList == null || woList.size() == 0){
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("woStatus", woStatus.toString());
        List<String> ids = new ArrayList<String>(woList.size());
        for (SrsWo wo : woList) {
            //新的工单引擎生成的工单
            DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
            doOperateByTypeVo.setId(wo.getId());
            doOperateByTypeVo.setOperateType(OperateConstants.OperateType.WOHANDLE.toString());
//            Map<String, Object> paramMap = new HashMap<String, Object>();
//            paramMap.put("woId", wo.getId());
//            paramMap.put("operateType", OperateConstants.OperateType.WOHANDLE.toString());
//            paramMap.put("modifyBy", "scheduler");
            ObjectRestResponse operateResult = orderEngineFegin.doOperateByType(doOperateByTypeVo);
            log.info("受理工单处理结果:"+operateResult.toString());
        }
//        if(ids!=null && ids.size()>0){
//            params.put("woIds", ids);
//            params.put("modifyTime", Calendar.getInstance().getTime());
//            params.put("modifyBy", "scheduler");
//            srsWODao.updateWoStatus(params);
//        }

    }

}