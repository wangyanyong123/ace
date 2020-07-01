package com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.group.out.ResultProject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DecoreteDetailVo implements Serializable {
    private static final long serialVersionUID = -939675244591800642L;

    private String id;

    private String projectId;

    private String projectName;

    private List<ResultProject> projectInfo;

    private String servicePrice;

    private String costPrice;

    private String promoImgeStr;

    private List<ImgInfo> promoImge;

    private String serviceIntro;


}
