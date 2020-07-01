package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizHotHomeServiceProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangxl
 * @Date 2020-04-14 19:34:50
 */
public interface BizHotHomeServiceProjectMapper extends CommonMapper<BizHotHomeServiceProject> {

    /**
     * 根据热门到家服务ID获取项目ID集合
     *
     * @param hhsId : 热门到家服务ID
     * @return java.util.List<java.lang.String>
     * @Author guohao
     * @Date 2020/4/15 13:33
     */
    List<String> selectProjectIdListByHhsId(@Param("hhsId") String hhsId);

    int deleteByHhsIdAndPids(@Param("hhsId") String hhsId, @Param("deleteList") List<String> deleteList,
                             @Param("modifyBy") String modifyBy);
}
