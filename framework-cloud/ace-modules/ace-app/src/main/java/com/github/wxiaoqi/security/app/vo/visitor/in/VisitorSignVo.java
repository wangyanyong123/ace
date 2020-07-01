package com.github.wxiaoqi.security.app.vo.visitor.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class VisitorSignVo implements Serializable {
    private static final long serialVersionUID = -3059321876947551923L;

    @ApiModelProperty(value = "主键id(保存不用传)")
    private String id;
    @ApiModelProperty(value = "项目ID",required = true)
    private String projectId;
    @ApiModelProperty(value = "访客姓名")
    private String name;
    @ApiModelProperty(value = "访客电话")
    private String phone;
    @ApiModelProperty(value = "到访时间(yyyy-MM-dd HH:mm)")
    private String visitTime;
    @ApiModelProperty(value = "来访事由")
    private String visitReason;
    @ApiModelProperty(value = "是否驾车(0-未驾车1-驾车)")
    private String isDrive;
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;
    @ApiModelProperty(value = "人脸图片")
    private String visitPhoto;

}
