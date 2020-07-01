package com.github.wxiaoqi.security.app.rpc;

import com.github.wxiaoqi.security.api.vo.order.in.CreateWoInVo;
import com.github.wxiaoqi.security.api.vo.order.in.DoOperateVo;
import com.github.wxiaoqi.security.api.vo.order.out.*;
import com.github.wxiaoqi.security.app.config.VrobotConfig;
import com.github.wxiaoqi.security.app.entity.BizSubscribeWo;
import com.github.wxiaoqi.security.app.mapper.BizSubProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubscribeWoMapper;
import com.github.wxiaoqi.security.app.mapper.BizWoMapper;
import com.github.wxiaoqi.security.app.vo.crm.out.SyncWorkOrderStateRespose;
import com.github.wxiaoqi.security.app.vo.order.CreateWoResponseEntity;
import com.github.wxiaoqi.security.app.vo.order.ResponseEntity;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 与调度引擎系统接口同步
 */
@Service
@Slf4j
public class WorkOrderSyncBiz extends HttpBaseRobotBiz{

	@Autowired
	private VrobotConfig vrobotConfig;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
	@Autowired
    private BizSubProductMapper bizSubProductMapper;
	@Autowired
	private BizWoMapper bizWoMapper;


	/**
	 * 开线程生成投诉报修工单
	 * @param createWoInVo
	 */
	public void createHomeWorkOrderThread(final CreateWoInVo createWoInVo){
		new Thread(new Runnable() {
			@Override
			public void run() {
				createHomeWorkOrder(createWoInVo);
			}
		}).start();
	}

	/**
	 * 生成投诉报修工单
	 * @param createWoInVo
	 * @return
	 * @throws Exception
	 */
	public ObjectRestResponse createHomeWorkOrder(CreateWoInVo createWoInVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		String woOpenFlag = vrobotConfig.getWoOpenFlag();
		if("true".equals(woOpenFlag)){
			String urlParams = "wo/createHomeWorkOrder";
			result = this.requestPost(urlParams,createWoInVo);
			if(result!=null && result.getStatus()==200){
				CreateWoResponseEntity responseEntity = (CreateWoResponseEntity)JSONObject.toBean(JSONObject.fromObject(result.getData()), CreateWoResponseEntity.class);
                if(responseEntity!=null && "200".equals(responseEntity.getCode())){
					if(responseEntity.getData()!=null){
						Map map = (HashMap)responseEntity.getData();
						if(map!=null){
							String vrobotWoId = map.get("woId")==null ? "" : (String)map.get("woId");
							BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
							bizSubscribeWo.setId(createWoInVo.getId());
							bizSubscribeWo.setVrobotWoId(vrobotWoId);
							bizSubscribeWoMapper.updateByPrimaryKeySelective(bizSubscribeWo);
						}

					}
				}

            }
		}else{
			result.setMessage("未开启工单同步接口，无需同步");
		}
		return result;
	}

	/**
	 * 开线程生成商品工单
	 * @param subId
	 */
	public void createProductWorkOrderThread(final String subId){
		new Thread(new Runnable() {
			@Override
			public void run() {
				createProductWorkOrder(subId);
			}
		}).start();
	}

	/**
	 * 生成商品工单
	 * @param subId
	 * @return
	 * @throws Exception
	 */
	public ObjectRestResponse createProductWorkOrder(String subId) {
		ObjectRestResponse result = new ObjectRestResponse();
		//1.获取订单详情
		SubToRobotVo detail = bizSubscribeWoMapper.getSubWoToRobotDetail(subId);
		if(detail==null){
			log.error("该ID获取不到详情:"+subId);
			return result;
		}

		//2.获取订单产品信息
		List<SubToRobotProductInfo> subProductInfoList = bizSubProductMapper.getSubToRobotProductInfo(subId);
		if(subProductInfoList==null || subProductInfoList.size()==0){
			subProductInfoList = new ArrayList<>();
		}
		detail.setSubProductInfoList(subProductInfoList);

		String urlParams = "wo/createProductWorkOrder";
		result = this.requestPost(urlParams,detail);
		if(result!=null && result.getStatus()==200){
			CreateWoResponseEntity responseEntity = (CreateWoResponseEntity)JSONObject.toBean(JSONObject.fromObject(result.getData()), CreateWoResponseEntity.class);
			if(responseEntity!=null && "200".equals(responseEntity.getCode())){
				if(responseEntity.getData()!=null){
					Map map = (HashMap)responseEntity.getData();
					if(map!=null){
						String vrobotWoId = map.get("woId")==null ? "" : (String)map.get("woId");
						BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
						bizSubscribeWo.setId(subId);
						bizSubscribeWo.setVrobotWoId(vrobotWoId);
						bizSubscribeWoMapper.updateByPrimaryKeySelective(bizSubscribeWo);
					}

				}
			}

		}
		return result;
	}

	/**
	 * 开线程生成预约服务工单
	 * @param woId
	 */
	public void createReservationWorkOrderThread(final String woId){
		new Thread(new Runnable() {
			@Override
			public void run() {
				createReservationWorkOrder(woId);
			}
		}).start();
	}

	/**
	 * 生成预约服务工单
	 * @param woId
	 * @return
	 * @throws Exception
	 */
	public ObjectRestResponse createReservationWorkOrder(String woId) {
		ObjectRestResponse result = new ObjectRestResponse();

		//1.获取工单详情
		WoToRobotVo detail = bizSubscribeWoMapper.getWoToRobotDetail(woId);
		if(detail==null){
			log.error("该ID获取不到详情:"+woId);
			return result;
		}
		String resrrevaName = bizWoMapper.seelctResrrevaNameById(woId);
		if(StringUtils.isNotEmpty(resrrevaName)){
			detail.setName(resrrevaName);
		}
		//2.获取订单产品信息
		List<SubToRobotProductInfo> subProductInfoList = bizSubProductMapper.getSubToRobotProductInfo(woId);
		if(subProductInfoList==null || subProductInfoList.size()==0){
			subProductInfoList = new ArrayList<>();
		}
		detail.setSubProductInfoList(subProductInfoList);

		String urlParams = "wo/createReservationWorkOrder";
		result = this.requestPost(urlParams,detail);
		if(result!=null && result.getStatus()==200){
			CreateWoResponseEntity responseEntity = (CreateWoResponseEntity)JSONObject.toBean(JSONObject.fromObject(result.getData()), CreateWoResponseEntity.class);
			if(responseEntity!=null && "200".equals(responseEntity.getCode())){
				if(responseEntity.getData()!=null){
					Map map = (HashMap)responseEntity.getData();
					if(map!=null){
						String vrobotWoId = map.get("woId")==null ? "" : (String)map.get("woId");
						BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
						bizSubscribeWo.setId(woId);
						bizSubscribeWo.setVrobotWoId(vrobotWoId);
						bizSubscribeWoMapper.updateByPrimaryKeySelective(bizSubscribeWo);
					}
				}
			}
		}
		return result;
	}

    /**
     * 开线程工单评价状态同步
     * @param doOperateVo
     */
    public void syncWoEvaluateThread(final DoOperateVo doOperateVo){
        new Thread(new Runnable() {
            @Override
            public void run() {
				String urlParams = "wo/syncWoEvaluate";
				syncWoStatus(doOperateVo,urlParams);
            }
        }).start();
    }

	/**
	 * 开线程工单取消状态同步
	 * @param doOperateVo
	 */
	public void syncWoCancelThread(final DoOperateVo doOperateVo){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String urlParams = "wo/syncWoCancel";
				syncWoStatus(doOperateVo,urlParams);
			}
		}).start();
	}


	/**
	 * 开线程订单售后审核
	 * @param doOperateVo
	 */
	public void syncOrderAuditThread(final DoOperateVo doOperateVo){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String urlParams = "wo/syncWoAudit";
				syncWoStatus(doOperateVo,urlParams);
			}
		}).start();
	}

	/**
	 * 开线程订单签收
	 * @param doOperateVo
	 */
	public void syncOrderWoSignThread(final DoOperateVo doOperateVo){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String urlParams = "wo/syncWoSign";
				syncWoStatus(doOperateVo,urlParams);
			}
		}).start();
	}


	/**
	 * 开线程订单关闭
	 * @param doOperateVo
	 */
	public void syncOrderWoCloseThread(final DoOperateVo doOperateVo){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String urlParams = "wo/syncWoClose";
				syncWoStatus(doOperateVo,urlParams);
			}
		}).start();
	}

	/**
	 * 开线程订单退款成功
	 * @param doOperateVo
	 */
	public void syncOrderWoRefundSucceeedThread(final DoOperateVo doOperateVo){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String urlParams = "wo/syncWoRefundSucceed";
				syncWoStatus(doOperateVo,urlParams);
			}
		}).start();
	}


	/**
	 * 开线程订单退款拒绝
	 * @param doOperateVo
	 */
	public void syncOrderWoRefuseThread(final DoOperateVo doOperateVo){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String urlParams = "wo/syncWoRefundFailed";
				syncWoStatus(doOperateVo,urlParams);
			}
		}).start();
	}


	/**
     * 工单评价状态同步
     * @param doOperateVo
     * @return
     */
    private ObjectRestResponse syncWoStatus(DoOperateVo doOperateVo,String url) {
        ObjectRestResponse result = new ObjectRestResponse();
        result = this.requestPost(url,doOperateVo);
        return result;
    }

}
