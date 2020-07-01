package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizDict;
import com.github.wxiaoqi.security.app.vo.evaluate.out.DictValueVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 业务数据字典
 * 
 * @author huangxl
 * @Date 2019-02-14 15:53:33
 */
public interface BizDictMapper extends CommonMapper<BizDict> {

    List<DictValueVo> selectDictValueList(String code);
}
