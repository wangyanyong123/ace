package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BaseProject;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;

import java.util.List;
/**
 * 项目表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-14 18:55:48
 */
public interface BaseProjectMapper extends CommonMapper<BaseProject> {

    /**
     * 获取项目列表
     * @return
     */
    List<ProjectListVo> selectProjectName();
	
}
