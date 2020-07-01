package com.github.wxiaoqi.security.generator.mapper;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author Mr.AG
 * @version 2017年08月25日
 * @email 463540703@qq.com
 */
public interface GeneratorMapper {

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
