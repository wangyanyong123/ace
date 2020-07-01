package com.github.wxiaoqi.security.jinmao.vo.passlog;

import lombok.Data;

import java.io.Serializable;

@Data
public class QrPassListVo implements Serializable {
    private static final long serialVersionUID = 3527035827441766062L;

    //查询条件：通行时间段、用户名称/用户电话/通行地址等模糊查询
    //列表字段：用户名称、用户电话、通行地址、通行状态、通行时间
    //操作：导出excel
    private String projectName;

    private String name;

    private String passAddr;

    private String phone;

    private String passDesc;

    private String createTime;


}
