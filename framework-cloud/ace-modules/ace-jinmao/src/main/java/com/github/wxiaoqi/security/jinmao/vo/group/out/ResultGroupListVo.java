package com.github.wxiaoqi.security.jinmao.vo.group.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultGroupListVo implements Serializable {

    @ApiModelProperty(value = "小组id")
    private String id;
    @ApiModelProperty(value = "组名")
    private String name;
    @ApiModelProperty(value = "logo")
    private String logoImage;
    @ApiModelProperty(value = "logo地址")
    private List<ImgInfo> logo;
    @ApiModelProperty(value = "组长")
    private String leader;
    @ApiModelProperty(value = "等级")
    private String grade;
    @ApiModelProperty(value = "状态(1-草稿，2-已发布，3-已撤回,4-全部)")
    private String enableStatus;

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


    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}
