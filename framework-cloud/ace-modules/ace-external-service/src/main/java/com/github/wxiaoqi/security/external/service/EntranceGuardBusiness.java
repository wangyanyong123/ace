package com.github.wxiaoqi.security.external.service;
import com.github.wxiaoqi.security.external.vo.PassportInfo;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:42 2019/1/2
 * @Modified By:
 */
public interface EntranceGuardBusiness {
	/**
	 * 门禁通行、返回最新的二维码信息
	 * @param passportCode 二维码值
	 */
	PassportInfo doPassEvent(String passportCode , String facilitiesCode);

	/**
	 * 门禁开门
	 * @param machineCode  机器编码
	 * @param passportCode 二维码值
	 * @return 1：成功，0：失败 2：无权限
	 */
	String doOpenDoorNew(String machineCode, String passportCode);
	/**
	 * 获取最新的通行证
	 * @param token
	 * @return
	 */
	PassportInfo getLastPassportByToken(String token, String userId);

	/**
	 * 更新二维码值(共码)
	 * @param objectId
	 * @return
	 */
	void updatePublicQr( String objectId);

	String getFacilitiesTypeByCode(String facilitiesCode);
}
