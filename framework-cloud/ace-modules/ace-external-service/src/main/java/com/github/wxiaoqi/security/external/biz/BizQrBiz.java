package com.github.wxiaoqi.security.external.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.*;
import com.github.wxiaoqi.security.external.config.QrConfiguration;
import com.github.wxiaoqi.security.external.constant.EntranceGuardConstant;
import com.github.wxiaoqi.security.external.constant.QRCodeConstant;
import com.github.wxiaoqi.security.external.entity.BizFacilities;
import com.github.wxiaoqi.security.external.entity.BizQr;
import com.github.wxiaoqi.security.external.entity.BizQrEnclosed;
import com.github.wxiaoqi.security.external.feign.UserIntegralFeign;
import com.github.wxiaoqi.security.external.mapper.BizQrMapper;
import com.github.wxiaoqi.security.external.service.PassEventService;
import com.github.wxiaoqi.security.external.service.QrCodeService;
import com.github.wxiaoqi.security.external.util.MD5;
import com.github.wxiaoqi.security.external.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 二维码
 *
 * @author zxl
 * @Date 2019-01-02 18:21:45
 */
@Service
@Slf4j
public class BizQrBiz extends BusinessBiz<BizQrMapper,BizQr> {

	@Autowired
	private BizQrMapper qrMapper;

	@Autowired
	private BizFacilitiesBiz facilitiesBiz;

	@Autowired
	private PassEventService passEventService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private QrCodeService qrCodeService;

	@Autowired
	private BizQrEnclosedBiz qrEnclosedBiz;

	@Autowired
	private QrConfiguration qrConfiguration;

	@Autowired
	private UserIntegralFeign userIntegralFeign;

	public List<BizQrVo> getQrByVal(String passPort) {
		return qrMapper.getQrByVal(passPort);
	}

	//1：成功，0：失败 2：无权限
	public String openDoorNew(String machineCode, String passport) {
		if (StringUtils.isAnyoneEmpty(passport , machineCode)) {
			return "0";
		}

		boolean flag = false;
		boolean isFormal = false;
		QrExamineBean qrExamineBean = new QrExamineBean();
		String userId = "";
		String qrCode = passport;
		String appType = "1";
		if(passport.length() != 66 && passport.length() != 32){
			return "0";
		}
		if(passport.length() == 66) {
			//0000000121798506CDCBF2161F579036485480206ABEA5F4AEB3DFE8Ad064d6a63e6266a201.00.00
			// 对通行码进行解密
			QrHexBean qrHexBean = decryptionOutQrCode(passport);
			if (StringUtils.isNotEmpty(qrHexBean.getUserId())) {
				userId = qrHexBean.getUserId();
				qrCode = qrHexBean.getQrCode();
				appType = qrHexBean.getAppType();
				isFormal = true;
			}

		}

		if (isFormal) {
			// 常规新版通行方案
			qrExamineBean = formalPassQrExamination(userId, machineCode, qrCode,appType);
			if(qrExamineBean.getrType().equals("1")){
				flag = true;
			}
			if (flag) {
				// 更新通行次数及当前版本号
				updateConventionalUserQr(userId,qrCode);
				// 记录通行成功
				passEventService.passLog(userId,
						qrExamineBean.getQrType(), passport , machineCode,
						qrExamineBean.getPassType(),
						qrExamineBean.getPassAddr());
			} else {
				// 记录通行失败
				passEventService.passFalureLog(userId,
						qrExamineBean.getQrType(), passport, machineCode,
						qrExamineBean.getPassType(),
						qrExamineBean.getrType(), qrExamineBean.getRdesc(),
						qrExamineBean.getPassAddr());
			}
		}else {
			qrExamineBean = tempPassQrExamination(machineCode, qrCode);
			flag = "1".equals( qrExamineBean.getrType());
			if (flag) {
				// 更新通行次数
				updateTempQr(qrExamineBean);
//				if(StringUtils.isEmpty(userId)){
//					userId = qrExamineBean.getCreateBy();
//				}
//				passEventService.passLog(userId,
//						qrExamineBean.getQrType(), passport , machineCode,
//						qrExamineBean.getPassType(),
//						qrExamineBean.getPassAddr());
			} else {
				if(StringUtils.isEmpty(userId)){
					userId = qrExamineBean.getCreateBy();
				}
				// 记录通行失败
				passEventService.passFalureLog(userId,
						qrExamineBean.getQrType(), qrCode, machineCode,
						qrExamineBean.getPassType(),
						qrExamineBean.getrType(), qrExamineBean.getRdesc(),
						qrExamineBean.getPassAddr());
			}
		}
		if(StringUtils.isNotEmpty(userId)){
			redisTemplate.opsForValue().set(EntranceGuardConstant.PUBQR_USER + passport, userId,3600, TimeUnit.SECONDS);
		}
//		if(StringUtils.isNotEmpty(qrExamineBean.getId())){
//			setQrAndUserCache(qrExamineBean , userId);
//		}
//		redisTemplate.opsForValue().set(EntranceGuardConstant.PUBQR_USER + passport, userId,600, TimeUnit.SECONDS);

		if("1".equals( qrExamineBean.getrType())){
			try {
				log.info("开始调用完成每日任务接口！");
				userIntegralFeign.finishDailyTask("task_104",userId);
				log.info("调用完成每日任务接口成功！");
			}catch (Exception e){
				log.error("调用完成每日任务接口失败！");
				e.printStackTrace();
			}
		}
		return qrExamineBean.getrType();
	}

	/**
	 * 刷码成功更新通行码
	 * @param qrExamineBean
	 * @return
	 */
	private int updateTempQr(QrExamineBean qrExamineBean){
		BizQr bizQr = new BizQr();
		String userId = "";
		bizQr.setId(qrExamineBean.getId());
		if (qrExamineBean.getQrType().equals("1") && qrExamineBean.getValidTims()!= 0) {
			bizQr.setUseTimes(qrExamineBean.getUseTimes() + 1);
			bizQr.setSurTimes(qrExamineBean.getSurTimes() - 1);
		}
		bizQr.setTimeStamp(new Date());
		bizQr.setModifyTime(new Date());
		userId = qrExamineBean.getCreateBy();
		bizQr.setModifyBy(userId);

		return qrMapper.updateByPrimaryKeySelective(bizQr);
	}

	/**
	 * 设置用户权限缓存
	 * @param qrExamineBean
	 * @param userId
	 */
	private void setQrAndUserCache(QrExamineBean qrExamineBean , String userId){
		String userQrCacheKey = EntranceGuardConstant.USER_QR_ENCLOSEDS + userId;
		List<BizQr> list = new ArrayList<BizQr>();
		BizQr bizQr = new BizQr();
		bizQr.setId(qrExamineBean.getId());
		bizQr.setQrType(qrExamineBean.getQrType());
		bizQr.setQrNum(qrExamineBean.getId());
		bizQr.setQrVal(qrExamineBean.getId());
		bizQr.setEffTime(qrExamineBean.getEffTime());
		bizQr.setLoseTime(qrExamineBean.getLoseTime());
		bizQr.setValidTims(qrExamineBean.getValidTims());
		bizQr.setUseTimes(qrExamineBean.getUseTimes());
		bizQr.setSurTimes(qrExamineBean.getSurTimes());
		bizQr.setPreUseTime(qrExamineBean.getPreUseTime());
		bizQr.setPrivateNum(qrExamineBean.getPrivateNum());
		bizQr.setPrivateVal(qrExamineBean.getPrivateVal());
		bizQr.setPrivateIssuedTime(qrExamineBean.getPrivateIssuedTime());
		bizQr.setStatus(qrExamineBean.getStatus());
		bizQr.setCreateBy(qrExamineBean.getCreateBy());
		list.add(bizQr);
		redisTemplate.opsForValue().set(userQrCacheKey, JSON.toJSONString(list),3600, TimeUnit.SECONDS);
	}

	/**
	 * 对外版二维码解密
	 *1、 二维码的组成包括（UserID长度是32+通行码流水号长度是8+通行码类型长度是1+ App版本号长度可以忽略）
	 * 2、 二维码加密只加密通行码流水号，采用MD5 16位加密
	 * 3、 通行码流水号分解按规率插入到UserID中
	 * 二维码加密前：9B1AA88199D64DB3850DDEBD2E95A27B0000000113.1.3
	 * 通行码流水号00000001加密后是3e51e06e01dc44c3 9f e1 a8 5f 866359733b6c7340 dda02cf9
	 * 二维码加密后：9B1A00A8810099D6004DB301850DDEBD2E95A27B3e51e06e01dc44c313.1.3
	 *
	 * 对外接口提供生成的长度固定为73位，00000006 C 8E69 00 B089 00 2E2C 00 4F26 06 92FCA8DEBE7C3E32f4 ea5a329bf0879d 01.00.00
	 * 通行码流水号(8)+位置类型(1)+UserID长度是(32)+通行码流水号(8)+Md5(userId)(16位)+版本号(8)
	 * 位置类型(A-60秒页面，B-正常页面，C-对外接口提供的)
	 *
	 * @param qrCode
	 * @return
	 */
	private QrHexBean decryptionOutQrCode(String qrCode) {
		QrHexBean qrHex = new QrHexBean();
		qrHex.setQrType(qrCode.substring(0,1));
		qrHex.setAppType(qrCode.substring(1,2));
		qrHex.setQrCode(qrCode.substring(6, 8)
				+ qrCode.substring(12, 14) + qrCode.substring(18, 20)
				+ qrCode.substring(24, 26));

		qrCode = qrCode.substring(2);
		String npassport = qrCode.substring(0, 4)
				+ qrCode.substring(6, 10) + qrCode.substring(12, 16)
				+ qrCode.substring(18, 22)
				+ qrCode.substring(24, qrCode.length());
		String hex = MD5.MD5Encode(npassport.substring(0, 32)+qrHex.getQrType()).substring(8,24);
		Integer pos = npassport.indexOf(hex);
		if (pos >= 0) {
			qrHex.setUserId(npassport.substring(0, pos));
			qrHex.setCurrversion(npassport.substring(pos + 16, npassport.length()));
		}
		return qrHex;
	}

	/**
	 * 常规新版通行刷码校验
	 * @param userId
	 * @param machineCode
	 * @param qrCode
	 * @return
	 */
	private QrExamineBean formalPassQrExamination(String userId, String machineCode, String qrCode, String appType){
		log.info("二维码校验参数: userId:{}, machineCode:{}, qrCode:{}, appType:{}", userId,  machineCode,  qrCode,  appType);
		QrExamineBean qrExamineBean = new QrExamineBean();
		if(qrCode.length() == 8){
			if("1".equals(appType)){
				List<UserEnclosedVo> userEnclosedVos = qrMapper.getUserEnclosedList(userId);
				if(userEnclosedVos.size() > 0){
					List<String> allEncloseList = new ArrayList<>();
					for (UserEnclosedVo userEnclosedVo: userEnclosedVos) {
						if(!"-1".equals(userEnclosedVo.getEnclosedPid())){
							List<String> encloseList = qrMapper.getAllEncloseList(userEnclosedVo.getEnclosedId());
							allEncloseList.addAll(encloseList);
						}else {
							allEncloseList.add(userEnclosedVo.getEnclosedId());
						}
					}
					List<String> distinctList = allEncloseList.stream().distinct().collect(Collectors.toList());

					BizFacilities bizFacilities = facilitiesBiz.hasAuthority(machineCode,distinctList);
					if(null != bizFacilities){
						QrExamineBean qeb = qrMapper.getQrByUserId(userId);
						if(null != qeb){
							if (Integer.parseInt(qrCode) < Integer.parseInt(qeb.getQrNum()) && qeb.getPreUseTime() != null) {
								qeb.setrType("0");
								qeb.setRdesc("5：非法二维码");
								qrExamineBean = qeb;
							} else {
								if (Integer.parseInt(qrCode) == Integer.parseInt(qeb.getQrNum())) {
									boolean isOverTime = false;
									if(null != qeb.getPreUseTime()){
										if(null == qrConfiguration){
											qrConfiguration = BeanUtils.getBean(QrConfiguration.class);
										}
										long second = (new Date().getTime()-qeb.getPreUseTime().getTime()) / 1000;
										if (second >= (long)qrConfiguration.getRefreshTime()) {
											isOverTime = true;
										}
									}
									if (isOverTime) {
										qeb.setrType("0");
										qeb.setRdesc("6：非法二维码");
										qrExamineBean = qeb;
									} else {
										qeb.setrType("1");
										qeb.setRdesc("成功");
										qrExamineBean = qeb;
									}
								} else {
									qeb.setrType("1");
									qeb.setRdesc("成功");
									qrExamineBean = qeb;
								}
							}
						}else {
							qrExamineBean.setrType("2");
							qrExamineBean.setRdesc("1:通行码不存在或没有开通此通行权限");
							return qrExamineBean;
						}
					}else {
						qrExamineBean.setrType("2");
						qrExamineBean.setRdesc("1:通行码不存在或没有开通此通行权限");
						return qrExamineBean;
					}
				}else {
					qrExamineBean.setrType("2");
					qrExamineBean.setRdesc("1:通行码不存在或没有开通此通行权限");
					return qrExamineBean;
				}
			} else {
				qrExamineBean.setrType("2");
				qrExamineBean.setRdesc("1:通行码不存在或没有开通此通行权限");
				return qrExamineBean;
			}
		}else {
			qrExamineBean.setrType("3");
			qrExamineBean.setRdesc("3:错误二维码");
			return qrExamineBean;
		}
		return qrExamineBean;
	}

	private QrExamineBean tempPassQrExamination(String machineCode,String qrCode){
		QrExamineBean qrExamineBean = new QrExamineBean();
		if(qrCode.length() == 32){
			QrExamineBean qeb = qrMapper.getQrByQrVal(qrCode);
			if(null != qeb){
				String encloseId = qrEnclosedBiz.getEncloseIdByQrId(qeb.getId());
				if(StringUtils.isEmpty(encloseId)){
					qrExamineBean.setrType("4");
					qrExamineBean.setRdesc("4:没有开通此通行权限");
					return qrExamineBean;
				}else {
					List<String> encloseList = qrMapper.getAllEncloseList(encloseId);
					BizFacilities bizFacilities = facilitiesBiz.hasAuthority(machineCode,encloseList);
					if(null != bizFacilities){
						qeb.setrType("1");
						qeb.setRdesc("成功");
						qrExamineBean = qeb;
					}else {
						qrExamineBean.setrType("4");
						qrExamineBean.setRdesc("4:没有开通此通行权限");
						return qrExamineBean;
					}
				}
			}else {
				qrExamineBean.setrType("2");
				qrExamineBean.setRdesc("1:通行码不存在或没有开通此通行权限");
				return qrExamineBean;
			}
		}else {
			qrExamineBean.setrType("3");
			qrExamineBean.setRdesc("3:错误二维码");
			return qrExamineBean;
		}
		return qrExamineBean;
	}

	/**
	 * 刷码成功更新,更新新版常规通行码的使用次数及时间
	 * @return
	 */
	private Integer updateConventionalUserQr(String userId, String qrCode){
		BizQr qr = new BizQr();
		qr.setQrNum(qrCode);
		qr.setUserId(userId);
		qr.setPreUseTime(DateTimeUtil.getLocalTime());
		qr.setModifyBy(userId);
		qr.setModifyTime(DateTimeUtil.getLocalTime());
		qr.setTimeStamp(new Date());
		Integer n = qrMapper.updateByUserId(qr);
		return n;
	}

	/**
	 * 获取最新的通行证
	 * @param token
	 * @return
	 */

	public PassportInfo getLastPassportByToken(String token,String userId) {
		PassportInfo passportInfo = new PassportInfo();
		BizQr qr = qrCodeService.getQrCodeInfo(userId);

		if (qr != null) {
			log.info("lastpassport token:{},qr:{},qr.userID:{}",token,qr.getId(),qr.getUserId());
			passportInfo.setQrId(qr.getId());
//			passportInfo.setNewQrVal(qr.getQrVal());
			passportInfo.setNewQrNum(qr.getQrNum());
//			passportInfo.setNewPrivateQrNum(qr.getPrivateNum());
//			passportInfo.setNewPrivateQrVal(qr.getPrivateVal());
//			passportInfo.setQrType(qr.getQrType());
			passportInfo.setLoseTime(DateUtils.dateToString(qr.getLoseTime(),
					DateUtils.DATETIME_FORMAT));

//			redisTemplate.opsForValue().set(EntranceGuardConstant.PUBQR_USER + qr.getQrVal(), userId,3600, TimeUnit.SECONDS);

			redisTemplate.opsForValue().set(EntranceGuardConstant.USER_PUBLIC_QR + qr.getUserId(), JSON.toJSONString(passportInfo),3600, TimeUnit.SECONDS);

		}else{
			log.error("lastpassport qr 不存在 token:{}",token);
			redisTemplate.opsForValue().set(EntranceGuardConstant.USER_PUBLIC_QR+ "token", token,3600, TimeUnit.SECONDS);
		}
		return passportInfo;
	}

	public void updatePublicQr(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return;
		}
		String qrNum = StringUtils.generateRandomNumber(8);
		String newQrVal = UUIDUtils.generateUuid() + qrNum;
		BizQr qr = new BizQr();
		qr.setQrVal(newQrVal);
		qr.setUserId(userId);
		qrMapper.updateQrByUserId(qr);
	}

	public BizQr generateFormalPassQr(String userId) {
		if(StringUtils.isEmpty(userId)){
			userId = BaseContextHandler.getUserID();
			BizQr qr = isHasEffectiveFormalPassQr();
			if(null != qr){
				return qr;
			}else {
				int num = isHasHouse();
				if(num < 1){
					return null;
				}
			}
		}else {
			BizQr qr = qrMapper.isHasEffectiveFormalPassQr(userId);
			if(null != qr){
				return qr;
			}else {
				int num = qrMapper.isHasHouse(userId);
				if(num < 1){
					return null;
				}
			}
		}

		BizQr bizQr = new BizQr();
		bizQr.setEffTime(DateTimeUtil.getLocalTime());
		bizQr.setLoseTime(DateTimeUtil.getLoseTime(QRCodeConstant.QRCODE_LOSE_TIME_FOREVER));
		bizQr.setId(UUIDUtils.generateUuid());
		bizQr.setUserId(userId);
		bizQr.setQrType(QRCodeConstant.PUB_QR_TYPE_FORMAL);
		bizQr.setCreateBy(userId);
		bizQr.setCreateTime(DateTimeUtil.getLocalTime());
		bizQr.setTimeStamp(new Date());
		bizQr.setQrNum("00000001");
		bizQr.setStatus(QRCodeConstant.PUB_QR_STATUS_USED);
		qrMapper.insertSelective(bizQr);
		return bizQr;
	}

	private int isHasHouse() {
		String userId = BaseContextHandler.getUserID();
		int num = qrMapper.isHasHouse(userId);
		return num;
	}

	public BizQr isHasEffectiveFormalPassQr() {
		String userId = BaseContextHandler.getUserID();
		BizQr qr = qrMapper.isHasEffectiveFormalPassQr(userId);
		return qr;
	}
	public BizQr isHasEffectiveTempPassQr(String tel,String enclosedId) {
		String userId = BaseContextHandler.getUserID();
		BizQr qr = qrMapper.isHasEffectiveTempPassQr(userId,tel,enclosedId);
		return qr;
	}

	public ObjectRestResponse generateTempPassQr(Date effTime, Date loseTime, String enclosedId, String tel, String name, int number) {
		ObjectRestResponse response = new ObjectRestResponse();
		//不校验二维码，随便生成
//		BizQr qr = isHasEffectiveTempPassQr(tel,enclosedId);
//		if(null != qr){
//			response.setStatus(501);
//			response.setData(qr);
//			response.setMessage("存在有效的二维！");
//			return response;
//		}
		String userId = BaseContextHandler.getUserID();
		BizQr bizQr = new BizQr();
		bizQr.setQrType(QRCodeConstant.PUB_QR_TYPE_TEMP);
		//生效时间
		bizQr.setEffTime(effTime);
		bizQr.setLoseTime(loseTime);
		bizQr.setValidTims(number);
		bizQr.setUseTimes(0);
		bizQr.setSurTimes(number);
		bizQr.setId(UUIDUtils.generateUuid());
		bizQr.setCreateBy(userId);
		bizQr.setUserId(tel);
		bizQr.setUserName(name);
		bizQr.setTel(tel);
		bizQr.setCreateTime(DateTimeUtil.getLocalTime());
		bizQr.setTimeStamp(new Date());
		bizQr.setQrVal(UUIDUtils.generateUuid());
		bizQr.setStatus(QRCodeConstant.PUB_QR_STATUS_USED);
		qrMapper.insertSelective(bizQr);
		BizQrEnclosed qrEnclosed = new BizQrEnclosed();
		qrEnclosed.setId(UUIDUtils.generateUuid());
		qrEnclosed.setQrId(bizQr.getId());
		qrEnclosed.setStatus("1");
		qrEnclosed.setCreateBy(userId);
		qrEnclosed.setEnclosedId(enclosedId);
		qrEnclosed.setCreateTime(new Date());
		qrEnclosedBiz.insertSelective(qrEnclosed);
		response.setData(bizQr);
		response.setMessage("生成成功！");
		return response;
	}

	public BizQr refreshFormalPassQr(String qrNum) {
		String userId=BaseContextHandler.getUserID();
		BizQr qr = qrMapper.getInfoByUserId(userId);
		if(null == qr){
			return null;
		}
		if(Integer.valueOf(qrNum).intValue() - Integer.valueOf(qr.getQrNum()).intValue() < 0){
			return null;
		}
		qr.setQrNum(qrNum);
		qr.setPreUseTime(new Date());
		qr.setModifyBy(userId);
		qr.setModifyTime(new Date());
		qrMapper.updateByPrimaryKeySelective(qr);
		return qr;
	}

	public QrExamineBean getQrByQrVal(String passPort) {
		return qrMapper.getQrByQrVal(passPort);
	}

	public String getLastQrNum() {
		return generateFormalPassQr(null).getQrNum();
	}

	public void deletePassQr(String userId) {
		if(isHasEffectivePassQr()>0){
			if(isHasHouse()<1){
				qrMapper.deleteByUserId(BaseContextHandler.getUserID(),userId);
			}
		}
	}

	private int isHasEffectivePassQr() {
		String userId = BaseContextHandler.getUserID();
		int qr = qrMapper.isHasEffectivePassQr(userId);
		return qr;
	}
}