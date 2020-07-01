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
 * @Date: Created in 11:07 2018/11/14
 * @Modified By:
 */
@Table(name = "sys_module_project")
@Data
public class SysModuleProject implements Serializable {

    private static final long serialVersionUID = 6161857102586633092L;
    //ID
    @Id
    private String id;

    //模块id
    @Column(name = "module_id")
    private String moduleId;

    //项目id
    @Column(name = "project_id")
    private String projectId;

    //排序
    @Column(name = "sort")
    private Integer sort;

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
