package com.github.wxiaoqi.security.api.vo.order.out;


import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class TransactionLogVo implements Serializable {

    private static final long serialVersionUID = -6971351531195745466L;

    private String id;
    @ApiModelProperty(value = "当前节点名称")
    private String currStep;
    @ApiModelProperty(value = "操作描述")
    private String description;
    @ApiModelProperty(value = "联系人姓名")
    private String conName;
    @ApiModelProperty(value = "联系人电话")
    private String conTel;
    @ApiModelProperty(value = "操作时间")
    private String createTime;
    @ApiModelProperty(value = "评价分值（0~5分）,-1：非评分项日志")
    private int appraisalVal;

    //图片id,多张图片逗号分隔
    @ApiModelProperty(value = "图片路径用逗号分隔")
    private String imgId;
    //图片路径
    @ApiModelProperty(value = "图片路径列表")
    private List<String> imgList;

    public List<ImgInfo> getImgList(){
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(imgId)){
            String[] imgArrayIds =new String[] { imgId };
            if(imgId.indexOf(",")!=-1){
                imgArrayIds=imgId.split(",");
            }
            for (String url : imgArrayIds){
                ImgInfo imgInfo = new ImgInfo();
                imgInfo.setUrl(url);
                list.add(imgInfo);
            }
        }
        return list;
    }

}