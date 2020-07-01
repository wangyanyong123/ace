package com.github.wxiaoqi.security.app.vo.decision.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

/**
 * 提交决策投票
 * @author: guohao
 * @date: 2020-06-05 09:21
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SubmitVoteRequest extends BaseIn {
    private static final long serialVersionUID = 1000145122581359780L;

    private String decisionId;

    private Integer voteStatus;

    private String remark;

    @Override
    public void check() {
        Assert.hasLength(decisionId,"decisionId 为空");
        Assert.notNull(voteStatus,"voteStatus 为空");
        Assert.isTrue(AceDictionary.VOTE_STATUS.containsKey(voteStatus),"非法的voteStatus");
    }
}
