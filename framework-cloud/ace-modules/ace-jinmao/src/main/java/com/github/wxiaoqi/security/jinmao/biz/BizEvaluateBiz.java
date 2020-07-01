package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizEvaluateHousekeeper;
import com.github.wxiaoqi.security.jinmao.mapper.BizEvaluateHousekeeperMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizEvaluatePropertyMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.EvaluateVo;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.HousekeeperInfo;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.PropertyEvaluateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管家评价表
 *
 * @author huangxl
 * @Date 2019-02-19 16:55:19
 */
@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BizEvaluateBiz extends BusinessBiz<BizEvaluateHousekeeperMapper,BizEvaluateHousekeeper> {

    @Autowired
    private BizEvaluateHousekeeperMapper bizEvaluateHousekeeperMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizEvaluatePropertyMapper bizEvaluatePropertyMapper;


    /**
     * 查询管家评价列表
     * @param time
     * @param evaluateType
     * @param housekeeperId
     * @param page
     * @param limit
     * @return
     */
    public List<EvaluateVo> getEvaluateList(String time, String evaluateType, String searchVal, String projectId, String housekeeperId,
                                            Integer page, Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if(time == null && StringUtils.isEmpty(time)){
            time = sdf.format(new Date());
        }
        List<EvaluateVo> evaluateVoList =  bizEvaluateHousekeeperMapper.selectEvaluateList(type, BaseContextHandler.getTenantID(), time, evaluateType, searchVal
                ,projectId ,housekeeperId, startIndex, limit);
       if(evaluateVoList == null || evaluateVoList.size() == 0){
           evaluateVoList = new ArrayList<>();
       }
        return evaluateVoList;
    }


    public int selectEvaluateCount(String time, String evaluateType, String searchVal, String projectId, String housekeeperId){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if(time == null && StringUtils.isEmpty(time)){
            time = sdf.format(new Date());
        }
        return bizEvaluateHousekeeperMapper.selectEvaluateCount(type,BaseContextHandler.getTenantID(), time, evaluateType, searchVal,projectId, housekeeperId);
    }


    /**
     * 查询租户下所属管家
     * @return
     */
    public List<HousekeeperInfo> getHousekeeperList(String projectId){
        // String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        //查询当前租户id的身份
        String tenantType = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(tenantType)){
            projectId = bizProductMapper.selectProjectById(BaseContextHandler.getTenantID());
        }
        List<HousekeeperInfo> housekeeperInfoList = bizEvaluateHousekeeperMapper.selectHousekeeperByTenandId(projectId);
        if(housekeeperInfoList == null || housekeeperInfoList.size() == 0){
            housekeeperInfoList = new ArrayList<>();
        }
        return housekeeperInfoList;
    }


    /**
     * 查询物业评价列表
     * @param time
     * @param evaluateType
     * @param searchVal
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public List<PropertyEvaluateVo> getPropertyEvaluateList(String time, String evaluateType, String searchVal, String projectId,
                                                            Integer page, Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if(time == null && StringUtils.isEmpty(time)){
            time = sdf.format(new Date());
        }
        List<PropertyEvaluateVo> propertyEvaluateVoList = bizEvaluatePropertyMapper.selectPropertyEvaluateList(type,
                BaseContextHandler.getTenantID(),time, evaluateType, searchVal, projectId, startIndex, limit);
        if(propertyEvaluateVoList == null || propertyEvaluateVoList.size() == 0){
            propertyEvaluateVoList = new ArrayList<>();
        }
        return propertyEvaluateVoList;
    }


    public int selectPropertyEvaluateCount(String time, String evaluateType, String searchVal, String projectId){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if(time == null && StringUtils.isEmpty(time)){
            time = sdf.format(new Date());
        }
        return bizEvaluatePropertyMapper.selectPropertyEvaluateCount(type,BaseContextHandler.getTenantID(), time, evaluateType, searchVal, projectId);
    }


}