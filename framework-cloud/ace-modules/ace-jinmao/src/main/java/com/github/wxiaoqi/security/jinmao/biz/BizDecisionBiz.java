package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.job.AceXxlJobBiz;
import com.github.wxiaoqi.security.jinmao.vo.decision.in.EditDecisionAnnexRequest;
import com.github.wxiaoqi.security.jinmao.vo.decision.in.EditDecisionRequest;
import com.github.wxiaoqi.security.jinmao.vo.decision.in.QueryDecisionRequest;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionAnnexInfo;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizDecision;
import com.github.wxiaoqi.security.jinmao.mapper.BizDecisionMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 决策表
 *
 * @author guohao
 * @Date 2020-06-04 13:33:19
 */
@Service
public class BizDecisionBiz extends BusinessBiz<BizDecisionMapper,BizDecision> {

    @Autowired
    private BizDecisionAnnexBiz bizDecisionAnnexBiz;

    @Autowired
    private BizCrmHouseBiz bizCrmHouseBiz;
    @Autowired
    private AceXxlJobBiz aceXxlJobBiz;


    @Transactional(rollbackFor = Exception.class)
    public void edit(EditDecisionRequest request) {
        request.check();
        BizDecision edit = new BizDecision();
        BeanUtils.copyProperties(request,edit);
        edit.setModifyBy(BaseContextHandler.getUserID());
        if(StringUtils.isEmpty(request.getId())){
            edit.setId(UUIDUtils.generateUuid());
            edit.setCreateBy(BaseContextHandler.getUserID());
            edit.setCreateTime(new Date());
            edit.setPublishStatus(AceDictionary.PUBLISH_STATUS_YES);
            edit.setStatus(AceDictionary.DATA_STATUS_VALID);

            int projectHouseCount = getProjectHouseCount(request.getProjectId());
            Assert.isTrue(projectHouseCount > 0,"该项目无房屋信息，不可创建决策");
            edit.setHouseCount(projectHouseCount);
            this.mapper.insertSelective(edit);
        }else{
            BizDecision bizDecision = this.selectById(request.getId());

            Assert.isTrue(bizDecision.getEventType().equals(request.getEventType()),
                    "事件类型，不可修改");
            Assert.isTrue(System.currentTimeMillis() <= bizDecision.getEndTime().getTime(),
                    "决策已结束，不可修改");
            if(System.currentTimeMillis()>= bizDecision.getStartTime().getTime()
                && System.currentTimeMillis() < bizDecision.getEndTime().getTime()){
                Assert.isTrue(bizDecision.getStartTime().equals(request.getStartTime()),
                        "决策已开始，不可修改开始时间");
            }

            this.mapper.updateByPrimaryKeySelective(edit);
        }

        EditDecisionAnnexRequest annexRequest = new EditDecisionAnnexRequest();
        annexRequest.setAnnexList(request.getAnnexList());
        annexRequest.setDecisionId(edit.getId());
        bizDecisionAnnexBiz.edit(annexRequest);

        addDecisionEndJob(edit.getId(),request.getEndTime(),edit.getEndTime(),StringUtils.isEmpty(request.getId()));
    }

    private void addDecisionEndJob(String decisionId,Date sourceEndTime,Date targetEndTime,Boolean isNew){
        if(isNew || sourceEndTime.compareTo(targetEndTime) != 0){
            aceXxlJobBiz.addDecisionEndJob(decisionId,targetEndTime);
        }
    }

    public List<DecisionListVo> findList(QueryDecisionRequest request){
        request.check();
        return this.mapper.selectList(request);

    }

    public int countList(QueryDecisionRequest request) {
        request.check();
       return this.mapper.countList(request);
    }

    public DecisionInfoVo findInfo(String id) {
        BizDecision bizDecision = this.mapper.selectByPrimaryKey(id);
        DecisionInfoVo decisionInfoVo = new DecisionInfoVo();
        BeanUtils.copyProperties(bizDecision,decisionInfoVo);
        List<DecisionAnnexInfo> annexInfoList = bizDecisionAnnexBiz.findInfoListByDecisionId(id);
        decisionInfoVo.setAnnexList(annexInfoList);
        return decisionInfoVo;
    }

    private int getProjectHouseCount(String projectId){

        return bizCrmHouseBiz.getProjectHouseCount(projectId);
    }

    public void deleteById(String id, String remark) {
        this.mapper.deleteById(id,remark,BaseContextHandler.getUserID(),new Date());
    }
}
