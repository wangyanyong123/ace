package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.SysMsgNotice;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeList;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息通知
 * 
 * @author huangxl
 * @Date 2019-02-27 11:58:04
 */
public interface SysMsgNoticeMapper extends CommonMapper<SysMsgNotice> {

    List<SmsNoticeList> getSmsNoticeList(@Param("userId") String userId,@Param("page") Integer page,@Param("limit") Integer limit);


	List<String> getSmsNoticeSign(@Param("userId") String userId);

    int updateSmsNotice(@Param("id") String id);
}
