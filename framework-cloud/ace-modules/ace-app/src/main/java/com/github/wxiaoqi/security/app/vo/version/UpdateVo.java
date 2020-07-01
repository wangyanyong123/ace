package com.github.wxiaoqi.security.app.vo.version;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateVo implements Serializable {
    private static final long serialVersionUID = 604185431667514948L;

    private String isUpdate;

    private String isForce;

    private String updateContent;

}
