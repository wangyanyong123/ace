package com.github.wxiaoqi.security.jinmao.vo.AppResultModuleVo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAppModule  implements Serializable {

    @ApiModelProperty(value = "模块id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父节点id")
    private String pid;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "模块logo")
    private String logo;

    @ApiModelProperty(value = "对应系统(1-客户端APP,2-服务端APP)")
    private String system;

    @ApiModelProperty(value = "跳转类型(1-原生app,2-公司内部H5,3-外部接入H5)")
    private String showType;

    @ApiModelProperty(value = "ios最低支持版本")
    private String iosVersion;

    @ApiModelProperty(value = "android最低支持版本")
    private String androidVersion;
    @ApiModelProperty(value = "业务ID")
    private String busId;
    @ApiModelProperty(value = "页面展现形式(0-默认,1-小茂推荐,2-好物探访,3-优选商城,4-团购)")
    private String pageShowType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPageShowType() {
        return pageShowType;
    }

    public void setPageShowType(String pageShowType) {
        this.pageShowType = pageShowType;
    }
}
