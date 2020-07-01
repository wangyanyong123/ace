package com.github.wxiaoqi.security.jinmao.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultGroupMemberInfoVo implements Serializable {

    @ApiModelProperty("组员信息")
    private List<ResultGroupMemberVo> member;
    @ApiModelProperty(value = "组长信息")
    private List<ResultLeaderInfoVo> leader;


    public List<ResultGroupMemberVo> getMember() {
        return member;
    }

    public void setMember(List<ResultGroupMemberVo> member) {
        this.member = member;
    }

    public List<ResultLeaderInfoVo> getLeader() {
        return leader;
    }

    public void setLeader(List<ResultLeaderInfoVo> leader) {
        this.leader = leader;
    }
}
