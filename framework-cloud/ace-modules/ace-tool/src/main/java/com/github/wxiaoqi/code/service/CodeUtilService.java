package com.github.wxiaoqi.code.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description: 编码、随机码服务接口
 * @Date: Created in 10:21 2018/11/12
 * @Modified By:
 */
public interface CodeUtilService {

    /**
     * 生成编码 【调用存储过程方式】
     * @param searchKey		编码类型
     * @param prefixCode	前缀编码
     * @param codeSize		返回编码长度
     * @param sequenceType	自增序列类型
     * @return result		存储过程生成的编码
     */
    String getAutoCode(String searchKey, String prefixCode, String codeSize, String sequenceType);

}
