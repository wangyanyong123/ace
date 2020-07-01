package com.github.wxiaoqi.security.external.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.external.biz.BizFacilitiesBiz;
import com.github.wxiaoqi.security.external.biz.BizPassBiz;
import com.github.wxiaoqi.security.external.biz.BizQrBiz;
import com.github.wxiaoqi.security.external.constant.EntranceGuardConstant;
import com.github.wxiaoqi.security.external.entity.BizFacilities;
import com.github.wxiaoqi.security.external.entity.BizPass;
import com.github.wxiaoqi.security.external.entity.BizQr;
import com.github.wxiaoqi.security.external.service.PassEventService;
import com.github.wxiaoqi.security.external.util.Utils;
import com.github.wxiaoqi.security.external.vo.PassportInfo;
import com.github.wxiaoqi.security.external.vo.BizQrVo;
import com.github.wxiaoqi.security.external.vo.QrExamineBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:19 2019/1/2
 * @Modified By:
 */
@Service
@Slf4j
public class PassEventServiceImpl implements PassEventService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private BizQrBiz bizQrBiz;

	@Autowired
	private BizPassBiz bizPassBiz;

	@Autowired
	private BizFacilitiesBiz facilitiesBiz;

	//设备类型缓存数据正确性依赖相关基础数据不修改类型 如果后续设备数据有改动此处需要刷新缓存
	private static Map<String, String> machineTypeMapCache = new HashMap<>();

	@Override
	public PassportInfo handle(String passPort , String machineCode) {
		PassportInfo passportInfo = new PassportInfo();
		String userId = redisTemplate.opsForValue().get(EntranceGuardConstant.PUBQR_USER + passPort);
		if(passPort.length() == 66){
			log.info("临时日志:开始接收通行成功请求，passport:{},machineCode:{},userId:{}",passPort,machineCode,userId);

			List<BizQrVo> qrList = null;
			String qrVal = null;
			BizQrVo qr = null;

			log.info("临时日志:通行成功请求更新状态开始，passport:{},machineCode:{},userId:{}",passPort,machineCode,userId);
			updatePassLog(passPort);
			log.info("临时日志:通行成功请求更新状态成功，passport:{},machineCode:{},userId:{}",passPort,machineCode,userId);


			if(StringUtils.isNotEmpty(userId) ){//正式码
				passportInfo = JSON.parseObject(redisTemplate.opsForValue().get(EntranceGuardConstant.USER_PUBLIC_QR + userId), PassportInfo.class);
				if(passportInfo == null){
					log.info("临时日志:通行找不到passportInfo1，passport:{},machineCode:{},userId:{}",passPort,machineCode,userId);
					return new PassportInfo();
				}
			}

		}else if(passPort.length() == 32){
			log.info("临时日志:邀请访客通行成功请求更新状态，passport:{},machineCode:{}",passPort,machineCode);
//			updatePassLog(passPort);
			QrExamineBean qeb = bizQrBiz.getQrByQrVal(passPort);
			passSuccessLog(qeb.getCreateBy(), qeb.getQrType(), qeb.getQrVal(), machineCode, "1","1","2","通行并开门成功", null);
			log.info("临时日志:邀请访客通行成功请求更新状态成功，passport:{},machineCode:{}",passPort,machineCode);
			handleVisitorPass(passPort , machineCode);
		}

		return passportInfo;
	}

	private void passSuccessLog(final String userId , final String qrType ,
						 final String qrVal , final String facilitiesCode,
						 final String passType,final String passStatus,final String openStatus,final String passDesc,final String passAddr){
		Utils.execute(
				new Runnable() {

					@Override
					public void run() {
						insertPassLog(userId, qrType, qrVal, facilitiesCode, passType, passStatus,openStatus, passDesc, passAddr);
					}
				}
		);

	}
	private void handleVisitorPass(String passPort , String machineCode){
		String facilitiesType = getFacilitiesTypeByCode(machineCode);//01060405 01060406
	}

	/**
	 * 异步记录通行成功记录
	 */
	public void passLog(final String userId , final String qrType ,
						final String qrVal , final String facilitiesCode,final String passType,final String passAddr){
		passLog(userId, qrType, qrVal, facilitiesCode,passType,"1","1","通行成功",passAddr);
	}

	/**
	 * 异步记录通行失败记录
	 */
	public void passFalureLog(final String userId , final String qrType ,
							  final String qrVal , final String facilitiesCode,final String passType,String passStatus,String passDesc,final String passAddr){
		passLog(userId, qrType, qrVal, facilitiesCode,passType,passStatus,"1",passDesc,passAddr);
	}


	private void passLog(final String userId , final String qrType ,
						 final String qrVal , final String facilitiesCode,
						 final String passType,final String passStatus,final String openStatus,final String passDesc,final String passAddr){
		Utils.execute(
				new Runnable() {
					@Override
					public void run() {
						insertPassLog(userId, qrType, qrVal, facilitiesCode, passType, passStatus,openStatus, passDesc, passAddr);
					}
				}
		);

	}

	private void updatePassLog(final String qrVal ){
		log.info("临时日志:新版通行状态更新开始-数据库，passport:{},Time:{}",qrVal,DateUtils.dateToString(new Date(), "HH:mm:ss.SSS"));
		Utils.execute(
				new Runnable() {
					@Override
					public void run() {
						Map<String, Object> passData = new HashMap<String, Object>();
						passData.put("qrVal", qrVal);
						bizPassBiz.updatePassLog(passData);
						log.info("临时日志:新版通行状态更新成功-数据库，passport:{},Time:{}",qrVal,DateUtils.dateToString(new Date(), "HH:mm:ss.SSS"));
					}
				});
	}

	private void insertPassLog(final String userId , final String qrType ,
							   final String qrVal , final String facilitiesCode,
							   final String passType,final String passStatus, String openStatus,final String passDesc,final String passAddr){
		Map<String, Object> passData = new HashMap<String, Object>();
		BizPass pass = new BizPass();
		pass.setId(UUIDUtils.generateUuid());
		pass.setUserId(userId);
		pass.setQrVal(qrVal);
		pass.setPassType(passType);
		pass.setQrType(qrType);
		pass.setCreateBy(userId);
		pass.setCreateTime(new Date());
		pass.setFacilitiesCode(facilitiesCode);
		pass.setPassStatus(passStatus);
		pass.setPassDesc(passDesc);
		pass.setOpenStatus(openStatus);

		if(StringUtils.isEmpty(passAddr)){
			BizFacilities bizFacilities = facilitiesBiz.selectByCode(facilitiesCode);
			if(bizFacilities!=null){
				pass.setPassAddr(bizFacilities.getFacilitiesName());
			}else{
				pass.setPassAddr(facilitiesCode);
			}
		}else{
			pass.setPassAddr(passAddr);
		}
		String facilitiesType = getFacilitiesTypeByCode(facilitiesCode);
		if(StringUtils.isAnyoneEmpty(passType )){
			pass.setPassType(facilitiesType);
		}else{
			pass.setPassType(passType);
		}
		pass.setFacilitiesType(facilitiesType);
		bizPassBiz.insertSelective(pass);
	}


	/**
	 * 根据设备编码查询设备类型
	 * @param facilitiesCode
	 * @return
	 */
	@Override
	public String getFacilitiesTypeByCode(String facilitiesCode){

		String type = machineTypeMapCache.get(facilitiesCode);
		if(StringUtils.isNotEmpty(type)){
			return type;
		}
		List<String> sortCodes = facilitiesBiz.getFacilitiesTypeByCode(facilitiesCode);
		if(sortCodes == null || sortCodes.size() == 0){
			return "";
		}
		return sortCodes.get(0);
	}

}

