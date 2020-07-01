package com.github.wxiaoqi.security.jinmao.vo.house;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/10/28 11:03
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class UserRegistryVo implements Serializable {

    private String projectName;

    private String blockName;

    private String buildingName;

    private String unitName;

    private String floorName;

    private String houseName;

    private String isRegistry;

}
