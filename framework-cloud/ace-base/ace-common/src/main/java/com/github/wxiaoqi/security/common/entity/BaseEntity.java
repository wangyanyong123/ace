package com.github.wxiaoqi.security.common.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 基础entity
 *
 * @author ace
 * @version 2018/1/13.
 */
@Data
public class BaseEntity implements Serializable {
    @Id
    private Object id;
    private Boolean deleteFlag;
}
