package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCrmBlock;
import com.github.wxiaoqi.security.app.vo.city.out.BlockInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地块表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
public interface BizCrmBlockMapper extends CommonMapper<BizCrmBlock> {

	List<BizCrmBlock> getByIds(@Param("ids")List<String> blockIds);

	List<BlockInfoVo> getBlockInfoListByProjectId(@Param("projectId")String projectId,@Param("type") int type);
}
