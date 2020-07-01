package com.github.wxiaoqi.security.app.vo.group.out;

import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultGroupListVo implements Serializable {
    private static final long serialVersionUID = -7906436853075572336L;

    @ApiModelProperty(value = "小组ID")
    private String groupId;
    @ApiModelProperty(value = "小组名字")
    private String name;
    @ApiModelProperty(value = "logo")
    private String logoImage;
    @ApiModelProperty(value = "logo地址")
    private List<ImgeInfo> logo;

    private String classifyName;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public List<ImgeInfo> getLogo() {
        List<ImgeInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(logoImage)){
            String[] imArrayIds = new String[]{logoImage};
            if(logoImage.indexOf(",")!= -1){
                imArrayIds = logoImage.split(",");
            }
            for (String url:imArrayIds){
                ImgeInfo info = new ImgeInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setLogo(List<ImgeInfo> logo) {
        this.logo = logo;
    }
}
