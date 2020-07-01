package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopicTag;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicTagInfo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicTagVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 议事厅话题标签表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizChamberTopicTagMapper extends CommonMapper<BizChamberTopicTag> {


    /**
     * 查询议事厅话题标签列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ChamberTopicTagVo> selectChamberTopicTagList(@Param("projectId") String projectId,@Param("searchVal") String searchVal
                                              , @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询议事厅话题标签列表数量
     * @param projectId
     * @param searchVal
     * @return
     */
    int selectChamberTopicTagCount(@Param("projectId") String projectId,@Param("searchVal") String searchVal);

    /**
     * 查询话题标签详情
     * @param id
     * @return
     */
    ChamberTopicTagInfo selectChamberTopicTagInfo(String id);

    /**
     * 删除话题标签
     * @param id
     * @return
     */
    int delTopicTagById(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询议事厅话题是否关联标签
     * @param tagId
     * @return
     */
    int selectIsRelateByTagId(String tagId);
}
