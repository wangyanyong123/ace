package com.github.wxiaoqi.security.jinmao.vo.face;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/10/14 14:12
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class UserFaceInfo implements Serializable {

    private static final long serialVersionUID = 8993444822583703770L;
    private String userId;

    private String unitId;
}
