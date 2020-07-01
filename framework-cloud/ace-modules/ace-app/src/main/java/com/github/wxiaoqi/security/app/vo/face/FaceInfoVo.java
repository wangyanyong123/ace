package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FaceInfoVo implements Serializable {

    private String userId;
    private String photo;
    private List<String> facePhoto;

}
