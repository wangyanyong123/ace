package com.github.wxiaoqi.security.app.vo.face.in;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CheckFaceVo implements Serializable {

    private String appUserId;
    private Date passTime;
    private String passStatus;
    private String passAddr;

}
