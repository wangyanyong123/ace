package com.github.wxiaoqi.security.jinmao.vo.group.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultGroupInfoVo implements Serializable {

    @ApiModelProperty(value = "小组ID")
    private String id;
    @ApiModelProperty(value = "组名")
    private String name;
    @ApiModelProperty(value = "logo")
    private String logoImage;
    @ApiModelProperty(value = "logo地址")
    private List<ImgInfo> logo;
    @ApiModelProperty(value = "简介")
    private String summary;
    @ApiModelProperty(value = "项目")
    private List<ResultProject> projectInfo;
    @ApiModelProperty(value = "分类")
    private List<ResultClassify> classifyInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public List<ImgInfo> getLogo() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(logoImage)){
            String[] imArrayIds = new String[]{logoImage};
            if(logoImage.indexOf(",")!= -1){
                imArrayIds = logoImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setLogo(List<ImgInfo> logo) {
        this.logo = logo;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<ResultProject> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<ResultProject> projectInfo) {
        this.projectInfo = projectInfo;
    }

    public List<ResultClassify> getClassifyInfo() {
        return classifyInfo;
    }

    public void setClassifyInfo(List<ResultClassify> classifyInfo) {
        this.classifyInfo = classifyInfo;
    }
}
