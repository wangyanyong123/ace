package com.github.wxiaoqi.security.jinmao.vo.visitsign;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultVisitInfoVo implements Serializable {


    private static final long serialVersionUID = -1941118545207035088L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "访客姓名")
    private String visitName;
    @ApiModelProperty(value = "访客电话")
    private String phone;
    @ApiModelProperty(value = "访客人数")
    private Integer visitNum;
    @ApiModelProperty(value = "到访时间")
    private String visitTime;
    @ApiModelProperty(value = "到访地址")
    private String visitAddr;
    @ApiModelProperty(value = "到访理由")
    private String visitReason;
    @ApiModelProperty(value = "0-未开车1-开车")
    private Integer isDrive;
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;
    @ApiModelProperty(value = "通行有效开始时间")
    private String visitEffectTime;
    @ApiModelProperty(value = "通行有效结束时间")
    private String visitEndTime;
    @ApiModelProperty(value = "项目")
    private String projectName;
    @ApiModelProperty(value = "邀请人")
    private String name;
    @ApiModelProperty(value = "受邀人")
    private String visitorName;
    private String visitPhotoStr;
    @ApiModelProperty(value = "访客人脸")
    private List<ImgInfo> visitPhoto;
}
