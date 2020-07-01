package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetLogVo implements Serializable {
    private static final long serialVersionUID = -2991720436076415774L;

    private String projectId;
    private String startDate;
    private String endDate;
}
