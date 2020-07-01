package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizUserHouseBatchAuth;
import com.github.wxiaoqi.security.app.vo.userhouse.out.UserHoseInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 批量认证表
 * 
 * @author zxl
 * @Date 2019-09-25 15:19:18
 */
public interface BizUserHouseBatchAuthMapper extends CommonMapper<BizUserHouseBatchAuth> {

	List<UserHoseInfo> getUserHoseInfos();

}
