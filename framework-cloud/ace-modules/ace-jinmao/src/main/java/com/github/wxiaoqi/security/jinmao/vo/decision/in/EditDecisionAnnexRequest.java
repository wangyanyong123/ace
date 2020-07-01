package com.github.wxiaoqi.security.jinmao.vo.decision.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionAnnexInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author: guohao
 * @date: 2020-06-04 19:16
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class EditDecisionAnnexRequest extends BaseIn {
    private static final long serialVersionUID = 2895233254407911947L;

    private String decisionId;

    private List<DecisionAnnexInfo> annexList;


    @Override
    public void check() {
        Assert.hasLength(decisionId,"decisionId 为空");
        if(CollectionUtils.isNotEmpty(annexList)){
            for (int i = 0; i <annexList.size() ; i++) {
                DecisionAnnexInfo annexInfo = annexList.get(i);
                Assert.hasLength(annexInfo.getName()," annexList["+i+"].name 为空");
                Assert.hasLength(annexInfo.getUrl()," annexList["+i+"].url 为空");
            }
        }
    }
}
