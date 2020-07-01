package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:06 2018/11/14
 * @Modified By:
 */
@Table(name = "sys_module")
@Data
public class SysModule implements Serializable {

    private static final long serialVersionUID = -2150200468012294307L;
    //ID
    @Id
    private String id;

    //父模块id
    @Column(name = "pid")
    private String pid;

    //模块编码
    @Column(name = "code")
    private String code;

    //对应系统(1-客户端APP,2-服务端APP)
    @Column(name = "system")
    private String system;

    //模块名
    @Column(name = "name")
    private String name;

    //模块英文
    @Column(name = "en_us")
    private String enUs;

    //跳转类型(1-原生app,2-公司内部H5,3-外部接入H5)
    @Column(name = "show_type")
    private String showType;

    //标记该模块属于哪个根模块下的(1-首页，2-优选商城，3-管家，4-业主圈，5-我的)
    @Column(name = "module_type")
    private String moduleType;

    //url地址
    @Column(name = "url")
    private String url;

    //ios最低支持版本
    @Column(name = "ios_version")
    private String iosVersion;

    //android最低支持版本
    @Column(name = "android_version")
    private String androidVersion;

    //状态(0-禁用,1-启用)
    @Column(name = "status")
    private String status;

    //时间戳
    @Column(name = "time_Stamp")
    private Date timeStamp;

    //创建人
    @Column(name = "create_By")
    private String createBy;

    //创建日期
    @Column(name = "create_Time")
    private Date createTime;

    //修改人
    @Column(name = "modify_By")
    private String modifyBy;

    //修改日期
    @Column(name = "modify_Time")
    private Date modifyTime;

}
