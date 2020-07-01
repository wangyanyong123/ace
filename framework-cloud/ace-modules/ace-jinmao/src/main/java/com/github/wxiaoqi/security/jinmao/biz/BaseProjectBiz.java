package com.github.wxiaoqi.security.jinmao.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.jinmao.entity.BaseProject;
import com.github.wxiaoqi.security.jinmao.mapper.BaseProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizCrmProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目表
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-14 18:55:48
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BaseProjectBiz extends BusinessBiz<BaseProjectMapper, BaseProject> {

     @Autowired
     private BizCrmProjectMapper bizCrmProjectMapper;

    /**
     * 为PC端获取项目
     * @return
     */
    public List<ProjectListVo> getProjectListForPC(){
        List<ProjectListVo> projectList =  bizCrmProjectMapper.selectProjectName();
        return projectList;
    }

}