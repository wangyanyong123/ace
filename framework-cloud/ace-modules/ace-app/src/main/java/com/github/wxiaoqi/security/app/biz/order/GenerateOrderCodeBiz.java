package com.github.wxiaoqi.security.app.biz.order;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BaseAppClientUserBiz;
import com.github.wxiaoqi.security.app.biz.BizUserProjectBiz;
import com.github.wxiaoqi.security.app.entity.BizCrmProject;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.mapper.BizCrmProjectMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubscribeMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author: guohao
 * @create: 2020-04-19 15:23
 **/
@Component
public class GenerateOrderCodeBiz {

    @Resource
    private BizCrmProjectMapper bizCrmProjectMapper;

    @Resource
    private BizSubscribeMapper bizSubscribeMapper;

    @Autowired
    private CodeFeign codeFeign;

    @Autowired
    private BizUserProjectBiz bizUserProjectBiz;

    public  String  generateOrderCode(String projectId,String tenantId,String busId){

        if(StringUtils.isEmpty(projectId)){
            CurrentUserInfosVo currentUserInfos = bizUserProjectBiz.getCurrentUserInfos();
            if(currentUserInfos !=null){
                projectId = currentUserInfos.getProjectId();
            }
        }
        String code = getCode(projectId);
        String companyCode = bizSubscribeMapper.getByCompanyId(tenantId);
        if (companyCode == null) {
            companyCode = "";
        }
        String bstype = "";
        if(BusinessConstant.getShoppingBusId().equals(busId)
                || BusinessConstant.getSeckillBusId().equals(busId)
                || BusinessConstant.getTravelBusId().equals(busId)){
            bstype = "-P-";
        }else if(BusinessConstant.getGroupBuyingBusId().equals(busId)){
            bstype = "-G-";
        }else if(BusinessConstant.getReservationBusId().equals(busId)){
            bstype = "-S-";
        }else if(BusinessConstant.getPropertyBusId().equals(busId)){
            bstype = "-W-";
        }
        String subCode = code + "-" + companyCode + bstype + DateTimeUtil.shortDateString() + "-";

        ObjectRestResponse objectRestResponse = codeFeign.getCode("Subscribe", subCode, "6", "0");
        if(objectRestResponse.success()){
            return (String)objectRestResponse.getData();
        }
        //TODO
        return "生成订单号失败";

    }

    private String getCode(String projectId){
        if(StringUtils.isEmpty(projectId)){
            return "ws";
        }
        List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(Collections.singletonList(projectId));
        String code = "";
        if(CollectionUtils.isNotEmpty(bizCrmProjectBizList)){
            String projectCode = bizCrmProjectBizList.get(0).getProjectCode();
            String[] projectCodeArray = projectCode.split("-");
            if(projectCodeArray!=null && projectCodeArray.length>=2) {
                code = projectCodeArray[1];
            }
        }
        return code;
    }
}
