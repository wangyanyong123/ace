package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UploadVo implements Serializable {

    private List<String> photoArray;
    private String projectId;


}
