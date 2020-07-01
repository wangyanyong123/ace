package com.github.wxiaoqi.security.app.vo.group.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupIntegralVo implements Serializable {

    @ApiModelProperty(value = "小组ID")
    private String groupId;
    @ApiModelProperty(value = "小组名称")
    private String name;
    @ApiModelProperty(value = "logo")
    private String logoImage;
    @ApiModelProperty(value = "logo地址(从这获取)")
    private List<ImgInfo> logoImageList;
    @ApiModelProperty(value = "小组积分")
    private int creditsValue;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public List<ImgInfo> getLogoImageList() {
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

    public void setLogoImageList(List<ImgInfo> logoImageList) {
        this.logoImageList = logoImageList;
    }

    public int getCreditsValue() {
        return creditsValue;
    }

    public void setCreditsValue(int creditsValue) {
        this.creditsValue = creditsValue;
    }
}
