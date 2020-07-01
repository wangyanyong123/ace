package com.github.wxiaoqi.security.app.vo.goodvisit.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GoodVisitReaderVo implements Serializable {

    /**
     *      cr.id id,
     * 		cr.content_id contentId,
     * 		cr.view_num viewNum,
     *      cr.user_num userNum
     */
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "内容ID")
    private String contentId;
    @ApiModelProperty(value = "阅读量")
    private Integer viewNum;
    @ApiModelProperty(value = "用户量")
    private Integer userNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }
}
