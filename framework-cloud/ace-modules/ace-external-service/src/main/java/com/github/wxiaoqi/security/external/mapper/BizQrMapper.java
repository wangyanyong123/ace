package com.github.wxiaoqi.security.external.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.external.entity.BizQr;
import com.github.wxiaoqi.security.external.vo.BizQrVo;
import com.github.wxiaoqi.security.external.vo.QrExamineBean;
import com.github.wxiaoqi.security.external.vo.UserEnclosedVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 二维码
 * 
 * @author zxl
 * @Date 2019-01-02 18:21:45
 */
public interface BizQrMapper extends CommonMapper<BizQr> {

	List<BizQrVo> getQrByVal(String passPort);

	List<QrExamineBean> getNConventionalPassExamine(@Param("userId") String userId,@Param("machineCode") String machineCode,@Param("encloseList") List<String> encloseList);

	Integer updateByUserId(BizQr qr);

	BizQr getQrInfoByUserId(@Param("userId") String userId);

	Integer updateQrByUserId(BizQr qr);

	List<UserEnclosedVo> getUserEnclosedList(String userId);

	List<String> getAllEncloseList(String enclosedId);

	QrExamineBean getQrByUserId(String userId);

	QrExamineBean getQrByQrVal(String qrVal);

	BizQr isHasEffectiveFormalPassQr(String userId);

	BizQr isHasEffectiveTempPassQr(@Param("userId") String userId, @Param("tel") String tel,@Param("enclosedId")String enclosedId);

	BizQr getInfoByUserId(String userId);

	int isHasHouse(String userId);

	int isHasEffectivePassQr(String userId);

	int deleteByUserId(@Param("modifyUserId")String modifyUserId, @Param("userId")String userId);
}
