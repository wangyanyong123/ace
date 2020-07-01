package com.github.wxiaoqi.security.jinmao.vo.grade.in;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/9 19:33
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class GradeParams implements Serializable {
    private static final long serialVersionUID = 4078367480534510829L;

    private String id;

    private String gradeTitle;

    private Integer integral;

    private String imgId;

    private List<ImgInfo> gradeImg;


}
