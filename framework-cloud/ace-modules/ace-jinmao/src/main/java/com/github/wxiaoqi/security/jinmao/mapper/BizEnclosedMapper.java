package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizEnclosed;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.EnclosedInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.EnclosedTreeVo;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.FacilitiesInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 围合表
 * 
 * @author zxl
 * @Date 2018-12-24 11:45:40
 */
public interface BizEnclosedMapper extends CommonMapper<BizEnclosed> {

    /**
     * 查询围合树
     * @param projectId
     * @return
     */
    List<EnclosedTreeVo> selectEnclosedTreeList(@Param("projectId") String projectId);

    /**
     * 根据ID查询围合详情
     * @param id
     * @return
     */
    EnclosedInfoVo selectEnclosedById(@Param("id") String id);

    /**
     * 获取顶级围合
     * @return
     */
    List<EnclosedInfoVo> selectTopEnclosedInfo();

    int deleteEnclosedById(@Param("id") String id);

    int deleteFacilitiesById(@Param("id") String id);

    /**
     * 查询道闸列表
     * @return
     */
    List<FacilitiesInfoVo> selectFacilitiesList(@Param("enclosedId")String enclosedId,@Param("searchVal") String searchVal,@Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 删除围合关联单元
     * @param enclosedId
     * @return
     */
    int deleteEnclosedOnUnit(@Param("enclosedId") String enclosedId);

    int selectUnitEnclosedIsDelete(@Param("enclosedId") String enclosedId);
}
