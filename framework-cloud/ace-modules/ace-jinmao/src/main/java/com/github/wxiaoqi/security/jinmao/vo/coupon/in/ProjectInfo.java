package com.github.wxiaoqi.security.jinmao.vo.coupon.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectInfo implements Serializable {

    private static final long serialVersionUID = 7326507888958847146L;
    private List<String> projectId;
}
