package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.vo.decision.in.EditDecisionAnnexRequest;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionAnnexInfo;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizDecisionAnnex;
import com.github.wxiaoqi.security.jinmao.mapper.BizDecisionAnnexMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 决策附件表
 *
 * @author guohao
 * @Date 2020-06-04 13:33:19
 */
@Service
public class BizDecisionAnnexBiz extends BusinessBiz<BizDecisionAnnexMapper,BizDecisionAnnex> {

    @Transactional(rollbackFor = Exception.class)
    public void edit(EditDecisionAnnexRequest annexRequest) {

        List<BizDecisionAnnex> oldList = this.mapper.selectByDecisionId(annexRequest.getDecisionId());
        List<DecisionAnnexInfo> annexList = annexRequest.getAnnexList();
        List<String> deleteIdList = new ArrayList<>();

        Collection<DecisionAnnexInfo> newList =null ;
        if(CollectionUtils.isEmpty(annexList)){
            deleteIdList = oldList.stream().map(BizDecisionAnnex::getId).collect(Collectors.toList());
        }else if(CollectionUtils.isEmpty(oldList)){
            newList = annexList;
        }else{
            List<String> oldUrlList = oldList.stream().map(BizDecisionAnnex::getUrl).collect(Collectors.toList());
            List<String> existUrl = new ArrayList<>();
           newList= annexList.stream().filter(item->{
                if(oldUrlList.contains(item.getUrl())){
                    existUrl.add(item.getUrl());
                    return false;
                }else{
                    return true;
                }
            }).collect(Collectors.toList());
           deleteIdList = oldList.stream().filter(item->!existUrl.contains(item.getUrl()))
                   .map(BizDecisionAnnex::getId).collect(Collectors.toList());
        }

        String userID = BaseContextHandler.getUserID();
        if(CollectionUtils.isNotEmpty(deleteIdList)){
            this.mapper.deleteByIds(deleteIdList,userID,new Date());
        }
        if(CollectionUtils.isNotEmpty(newList)){
            for (DecisionAnnexInfo annexInfo : newList) {
                BizDecisionAnnex annex = new BizDecisionAnnex();
                annex.setId(UUIDUtils.generateUuid());
                annex.setDecisionId(annexRequest.getDecisionId());
                annex.setName(annexInfo.getName());
                annex.setUrl(annexInfo.getUrl());
                annex.setCreateBy(userID);
                annex.setCreateTime(new Date());
                annex.setModifyBy(userID);
                annex.setStatus(AceDictionary.DATA_STATUS_VALID);
                this.mapper.insertSelective(annex);

            }
        }
    }

    public List<DecisionAnnexInfo> findInfoListByDecisionId(String decisionId) {
        List<BizDecisionAnnex> list = this.mapper.selectByDecisionId(decisionId);
        return list.stream().map(item->{
            DecisionAnnexInfo annexInfo = new DecisionAnnexInfo();
            annexInfo.setName(item.getName());
            annexInfo.setUrl(item.getUrl());
            return annexInfo;
        }).collect(Collectors.toList());
    }
}