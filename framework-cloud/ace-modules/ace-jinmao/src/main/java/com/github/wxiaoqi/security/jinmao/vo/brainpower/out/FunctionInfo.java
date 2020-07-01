package com.github.wxiaoqi.security.jinmao.vo.brainpower.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FunctionInfo implements Serializable {

    @ApiModelProperty(value = "功能点id")
    private String id;
    @ApiModelProperty(value = "功能点")
    private String functionPoint;
    @ApiModelProperty(value = "功能点内容")
    private String description;
    @ApiModelProperty(value = "功能编码")
    private String code;
    @ApiModelProperty(value = "功能点集合")
    private List<ResultFunctionDictList> functionDictList;
    @ApiModelProperty(value = "图片")
    private String picture;
    @ApiModelProperty(value = "图片集合")
    private List<ImgInfo> pictureList;
    @ApiModelProperty(value = "链接页面编码")
    private String jumpCode;
    @ApiModelProperty(value = "跳转链接页面")
    private String jumpLink;
    @ApiModelProperty(value = "跳转链接集合")
    private List<ResultJumpLinkList> jumpLinkList;
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

    public String getFunctionPoint() {
        return functionPoint;
    }

    public void setFunctionPoint(String functionPoint) {
        this.functionPoint = functionPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<ResultFunctionDictList> getFunctionDictList() {
        return functionDictList;
    }

    public void setFunctionDictList(List<ResultFunctionDictList> functionDictList) {
        this.functionDictList = functionDictList;
    }
}
