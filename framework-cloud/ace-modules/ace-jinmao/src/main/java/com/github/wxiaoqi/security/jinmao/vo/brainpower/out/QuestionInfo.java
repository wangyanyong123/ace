package com.github.wxiaoqi.security.jinmao.vo.brainpower.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionInfo implements Serializable {


    @ApiModelProperty(value = "问题id")
    private String id;
    @ApiModelProperty(value = "问题标题")
    private String question;
    @ApiModelProperty(value = "所属分类")
    private String classify;
    @ApiModelProperty(value = "功能点id")
    private String functionId;
    @ApiModelProperty(value = "功能点名称")
    private String functionPoint;
    @ApiModelProperty(value = "链接页面编码")
    private String jumpCode;
    @ApiModelProperty(value = "跳转链接页面")
    private String jumpLink;
    @ApiModelProperty(value = "跳转链接集合")
    private List<ResultJumpLinkList> jumpLinkList;
    @ApiModelProperty(value = "图片")
    private String picture;
    @ApiModelProperty(value = "图片集合")
    private List<ImgInfo> pictureList;
    @ApiModelProperty(value = "答案")
    private String answer;
    @ApiModelProperty(value = "状态")
    private String enableStatus;



    public List<ImgInfo> getPictureList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(picture)){
            String[] imArrayIds = new String[]{picture};
            if(picture.indexOf(",")!= -1){
                imArrayIds = picture.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionPoint() {
        return functionPoint;
    }

    public void setFunctionPoint(String functionPoint) {
        this.functionPoint = functionPoint;
    }

    public String getJumpCode() {
        return jumpCode;
    }

    public void setJumpCode(String jumpCode) {
        this.jumpCode = jumpCode;
    }

    public String getJumpLink() {
        return jumpLink;
    }

    public void setJumpLink(String jumpLink) {
        this.jumpLink = jumpLink;
    }

    public List<ResultJumpLinkList> getJumpLinkList() {
        return jumpLinkList;
    }

    public void setJumpLinkList(List<ResultJumpLinkList> jumpLinkList) {
        this.jumpLinkList = jumpLinkList;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPictureList(List<ImgInfo> pictureList) {
        this.pictureList = pictureList;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}
