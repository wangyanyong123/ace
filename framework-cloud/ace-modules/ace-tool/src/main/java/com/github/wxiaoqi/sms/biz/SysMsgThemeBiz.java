package com.github.wxiaoqi.sms.biz;

import com.github.wxiaoqi.sms.entity.SysMsgTheme;
import com.github.wxiaoqi.sms.mapper.SysMsgThemeMapper;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 
 *
 * @author zxl
 * @Date 2018-11-20 14:56:24
 */
@Service
public class SysMsgThemeBiz extends BusinessBiz<SysMsgThemeMapper,SysMsgTheme> {


//	public SysMsgTheme getMsgTheme(String theme){
//		SysMsgThemeMapper
//		SysMsgTheme msgTheme = msgThemeDao.selectThemeByProInterface(theme);
//			if(msgTheme != null){
//				msgTheme.setMsgTempletList(msgTempletDao.selectMsgTempletListByThemeId(msgTheme.getId()));
//			}
//		return msgTheme;
//	}

}