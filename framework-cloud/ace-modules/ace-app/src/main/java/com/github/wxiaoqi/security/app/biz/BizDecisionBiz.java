package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.config.AppDefaultConfig;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizDecision;
import com.github.wxiaoqi.security.app.entity.BizDecisionVote;
import com.github.wxiaoqi.security.app.mapper.BizDecisionMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserHouseMapper;
import com.github.wxiaoqi.security.app.vo.decision.in.QueryDecisionRequest;
import com.github.wxiaoqi.security.app.vo.decision.in.SubmitVoteRequest;
import com.github.wxiaoqi.security.app.vo.decision.out.DecisionAnnexInfo;
import com.github.wxiaoqi.security.app.vo.decision.out.DecisionInfoVo;
import com.github.wxiaoqi.security.app.vo.decision.out.DecisionListVo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.UserHouseVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.decision.DecisionHandler;
import com.github.wxiaoqi.security.common.decision.DecisionPassRate;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 决策表
 *
 * @author guohao
 * @Date 2020-06-04 21:29:06
 */
@Service
public class BizDecisionBiz extends BusinessBiz<BizDecisionMapper,BizDecision> {

    @Autowired
    private BaseAppClientUserBiz clientUserBiz;
    @Resource
    private BizUserHouseMapper bizUserHouseMapper;

    @Autowired
    private BizDecisionVoteBiz bizDecisionVoteBiz;

    @Autowired
    private BizDecisionAnnexBiz bizDecisionAnnexBiz;

    @Autowired
    private DecisionHandler decisionHandler;




    @Transactional(rollbackFor = Exception.class)
    public void submitVote(SubmitVoteRequest request) {
        request.check();
        String userID = BaseContextHandler.getUserID();

        BizDecision bizDecision = this.selectById(request.getDecisionId());
        Assert.isTrue(System.currentTimeMillis() > bizDecision.getStartTime().getTime(),
                 "决策尚未开始。");
        Assert.isTrue(System.currentTimeMillis() < bizDecision.getEndTime().getTime(),
         "决策已结束。");
//
//        Assert.isTrue(AceDictionary.DECISION_STATUS_NONE.equals(bizDecision.getDecisionStatus()),
//                "决策已完成。");

        BaseAppClientUser clientUser = clientUserBiz.getUserById(userID);
        Assert.notNull(clientUser,"用户不存在");
        Assert.isTrue("1".equals(clientUser.getIsAuth()),"未认证房屋信息，请前去认证。");

        List<UserHouseVo> userHouseVos = bizUserHouseMapper.selectUserAllHouse(userID, bizDecision.getProjectId());

        List<UserHouseVo> canVoteHouseList = userHouseVos.stream()
                .filter(item -> !"2".equals(item.getIdentityType()))
                .collect(Collectors.toList());
        Assert.notEmpty(canVoteHouseList,"亲，您没有投票权限。");

        for (UserHouseVo userHouseVo : canVoteHouseList) {
            boolean exist = bizDecisionVoteBiz.existByHouseId(request.getDecisionId(), userHouseVo.getHouseId());
            if(!exist){
                doSubmitVote(request,userHouseVo,bizDecision);
            }
        }
    }

    public List<DecisionListVo> findList(QueryDecisionRequest request){
        request.check();
        return this.mapper.selectList(request);

    }

    public DecisionInfoVo findInfoVo(String decisionId){
        BizDecision bizDecision = this.mapper.selectByPrimaryKey(decisionId);
        DecisionInfoVo decisionInfoVo = new DecisionInfoVo();
        BeanUtils.copyProperties(bizDecision,decisionInfoVo);

        setPersonalInfo(decisionInfoVo,bizDecision.getHouseCount());

        List<DecisionAnnexInfo> annexInfoList = bizDecisionAnnexBiz.findInfoListByDecisionId(decisionId);
        decisionInfoVo.setAnnexList(annexInfoList);
        return decisionInfoVo;
    }

    public void setPersonalInfo(DecisionInfoVo decisionInfoVo,Integer houseCount){
        String userID = BaseContextHandler.getUserID();
        List<UserHouseVo> houseVoList = bizUserHouseMapper.selectUserAllHouse(userID, decisionInfoVo.getProjectId());
        //可投票房间数量
        long canVoteCount = 0L;
        //已投票房间数量
        long votedCount = 0L;
        for (UserHouseVo userHouseVo : houseVoList) {
            if("2".equals(userHouseVo.getIdentityType())){
                continue;
            }
            canVoteCount++;
            boolean exist = bizDecisionVoteBiz.existByHouseId(decisionInfoVo.getId(), userHouseVo.getHouseId());
            if(exist){
                votedCount++;
            }
        }
        long count = canVoteCount - votedCount;
        if(canVoteCount > 0){
            count = count >= 0?count : 0L;
        }else{
            count = -1;
        }
        BigDecimal personalRate =decisionHandler.getProgressRate(houseCount);

        decisionInfoVo.setPersonalRate(personalRate.multiply(new BigDecimal(canVoteCount)));
        decisionInfoVo.setCanVoteCount(count);


    }

    private void doSubmitVote(SubmitVoteRequest request,UserHouseVo houseVo,BizDecision decision){


        BigDecimal subProgressRate = BigDecimal.ZERO;
        if(AceDictionary.VOTE_STATUS_AGREE.equals(request.getVoteStatus())){
            subProgressRate   = decisionHandler.getProgressRate(decision.getHouseCount());
        }

        String userID = BaseContextHandler.getUserID();
        BizDecisionVote decisionVote = new BizDecisionVote();
        decisionVote.setId(UUIDUtils.generateUuid());
        decisionVote.setDecisionId(request.getDecisionId());
        decisionVote.setHouseId(houseVo.getHouseId());
        decisionVote.setHouseName(houseVo.getHouseName());
        decisionVote.setUserId(userID);
        decisionVote.setIdentityType(houseVo.getIdentityType());

        decisionVote.setVoteStatus(request.getVoteStatus());
        decisionVote.setProgressRate(subProgressRate);
        decisionVote.setProjectId(decision.getProjectId());
        decisionVote.setRemark(request.getRemark());
        decisionVote.setCreateBy(userID);
        decisionVote.setCreateTime(new Date());
        decisionVote.setStatus(AceDictionary.DATA_STATUS_VALID);

        //添加投票记录
        bizDecisionVoteBiz.insertSelective(decisionVote);

        if(AceDictionary.VOTE_STATUS_AGREE.equals(request.getVoteStatus())){
            //总进度
            BigDecimal totalProgressRate = decision.getProgressRate().add(subProgressRate);
            //是否通过
            boolean pass = decisionHandler.isPass(decision.getEventType(), totalProgressRate);
            //更新决策进度及决策状态
            this.mapper.additionProgressRate(decision.getId(),pass,subProgressRate,userID);
        }
    }

}
