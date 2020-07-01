package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopicSelect;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.SelContentVo;

import java.util.List;

/**
 * 议事厅话题投票选项表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizChamberTopicSelectMapper extends CommonMapper<BizChamberTopicSelect> {

    /**
     * 查询投票的投票选项列表
     * @param topicId
     * @return
     */
    List<SelContentVo> selectSelContentList(String topicId);
}
