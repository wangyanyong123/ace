package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCommunityTopicProject;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;

import java.util.List;

/**
 * 社区话题项目关联表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizCommunityTopicProjectMapper extends CommonMapper<BizCommunityTopicProject> {

    /**
     * 根据话题id查询所属项目
     * @param topicId
     * @return
     */
    List<String> selectProjectNameById(String topicId);


    /**
     * 查询所属项目列表
     * @param topicId
     * @return
     */
    List<ResultProjectVo> selectProjectInfoById(String topicId);

    /**
     * 根据话题id删除关联项目
     * @param topicId
     * @return
     */
    int deleteCommunityTopicById(String topicId);
	
}
