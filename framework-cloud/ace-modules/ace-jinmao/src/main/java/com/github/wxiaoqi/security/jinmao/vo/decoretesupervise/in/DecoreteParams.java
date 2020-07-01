package com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DecoreteParams implements Serializable {
    private static final long serialVersionUID = 3509544965529354071L;

    private String id;

    private String projectId;

    private String servicePrice;

    private String costPrice;

    private List<ImgInfo> promoImge;

    private String serviceIntro;
}
