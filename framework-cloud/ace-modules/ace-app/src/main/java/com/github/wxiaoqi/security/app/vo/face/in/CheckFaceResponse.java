package com.github.wxiaoqi.security.app.vo.face.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CheckFaceResponse implements Serializable {

    private String code;
    private String describe;
    private List<CheckFaceVo> data;

}
