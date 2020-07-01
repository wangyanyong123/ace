package com.github.wxiaoqi.oss.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 文件信息
 * 
 * @author zxl
 * @Date 2018-12-03 13:59:20
 */
@Table(name = "file_info")
@Data
public class FileInfo implements Serializable {
	private static final long serialVersionUID = 5470770406428008139L;
	
	    //文件ID
    @Id
    private String id;
	
	    //文件类型:1、图片,2、文件,3、视频
    @Column(name = "type")
    private String type;
	
	    //文件详细类型：11:jpg、12:peng、21:excel、22:pdf、31mp3
    @Column(name = "file_type")
    private String fileType;
	
	    //服务器上的名字
    @Column(name = "real_file_name")
    private String realFileName;
	
	    //文件大小
    @Column(name = "size")
    private Long size;
	
	    //文件名称
    @Column(name = "file_name")
    private String fileName;
	
	    //图片描述
    @Column(name = "file_desc")
    private String fileDesc;
	
	    //来源系统 1:客户APP  2：服务APP  3;web端   4 服务端
    @Column(name = "system")
    private String system;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //上传状态 1原状态 2已上传 3上传失败 4未找到图片
    @Column(name = "loadStatus")
    private String loadstatus;
	
	    //大图
    @Column(name = "natrual_path")
    private String natrualPath;
	
	    //大图
    @Column(name = "large_path")
    private String largePath;
	
	    //中图
    @Column(name = "medium_path")
    private String mediumPath;
	
	    //小图
    @Column(name = "thumbnail_path")
    private String thumbnailPath;
	
	    //创建时间
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //创建人id
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //更新时间
    @Column(name = "upd_time")
    private Date updTime;
	
	    //更新人id
    @Column(name = "upd_user_id")
    private String updUserId;
	
	    //存储类型 1、oss
    @Column(name = "storage_type")
    private String storageType;

}
