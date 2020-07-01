package com.github.wxiaoqi.security.app.vo.intergral.out;

import com.github.wxiaoqi.security.app.entity.BizUserGradeRule;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/12 11:53
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class UserGradeVo implements Serializable {
    private static final long serialVersionUID = 3370983983907128705L;

    @ApiModelProperty(value = "等级图片")
    private String gradeImg;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;
    @ApiModelProperty(value = "下一等级升级差值积分")
    private Integer integral;
    @ApiModelProperty(value = "总积分")
    private Integer totalIntegral;
    @ApiModelProperty(value = "每日随机任务")
    private List<TaskVo> task;
}
