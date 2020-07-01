package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liuam
 * @date 2019-08-14 10:35
 */
@Data
@Table(name = "sync_table_user_info")
public class SyncTableUserInfo {

    @Id
    private String id;

    @Column(name = "status")
    private String status;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "operate_seq")
    private Integer operateSeq;

    @Column(name = "operate_type")
    private String operateType;

    @Column(name = "message")
    private String message;

    @Column(name = "message_type")
    private String messageType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "sync_time")
    private Date syncTime;
}
