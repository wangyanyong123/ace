package com.github.wxiaoqi.security.jinmao.vo.AppResultModuleVo;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModelProperty;

public class AppModuleTree extends TreeNodeVO<AppModuleTree> {
    @ApiModelProperty(value = "模块id")
    private String id;
    @ApiModelProperty(value = "父节点id")
    private String pid;
    @ApiModelProperty(value = "名称")
    private String name;
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

    public AppModuleTree() {

    }

    public AppModuleTree(Object id, Object pid, String name, String code,String logo,String system
                    ,String showType,String iosVersion,String androidVersion,String busId,String pageShowType) {
        this.name=name;
        this.code=code;
        this.logo=logo;
        this.system=system;
        this.showType=showType;
        this.iosVersion=iosVersion;
        this.androidVersion=androidVersion;
        this.busId=busId;
        this.pageShowType=pageShowType;
        this.setId(id);
        this.setParentId(pid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
