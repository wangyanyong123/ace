package com.github.wxiaoqi.security.common.msg;

import lombok.Data;

/**
 * 自定义的响应参数
 * @author liuam
 * @date 2019-07-15 10:58
 */
@Data
public class ResponseEntity<E> {

    /**
     * 返回码，200表示成功，其他值为失败
     */
    private String code;

    /**
     * 描述，描述信息
     */
    private String describe;

    /**
     * 数据信息
     */
    private E data;

}
