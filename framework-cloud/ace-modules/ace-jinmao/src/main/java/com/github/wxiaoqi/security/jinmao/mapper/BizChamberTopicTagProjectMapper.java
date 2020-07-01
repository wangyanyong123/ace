package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopicTagProject;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;

import java.util.List;

/**
 * 议事厅话题标签项目关联表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizChamberTopicTagProjectMapper extends CommonMapper<BizChamberTopicTagProject> {

    /**
     * 根据话题标签id查询所属项目
     * @param tagId
     * @return
     */
    List<String> selectProjectNameById(String tagId);

    /**
     * 查询所属项目列表
     * @param tagId
     * @return
     */
    List<ResultProjectVo> selectProjectInfoById(String tagId);

    /**
     * 根据话题标签id删除关联项目
     * @param tagId
     * @return
     */
    int deleteTopicTagById(String tagId);
}
