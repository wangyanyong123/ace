package com.github.wxiaoqi.pay.ali.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.wxiaoqi.feign.CommodityFeign;
import com.github.wxiaoqi.pay.ali.config.AlipayConfig;
import com.github.wxiaoqi.pay.ali.service.AliPayService;
import com.github.wxiaoqi.pay.mapper.AccountBookMapper;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.api.vo.to.AliRefundTO;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:00 2018/12/7
 * @Modified By:
 */
@RestController
@RequestMapping("/aliPay")
@Api(tags = "支付宝支付回调")
@Slf4j
@ApiIgnore
public class AliPayController {

	@Autowired
	private AliPayService aliPayService;
	@Autowired
	private CommodityFeign commodityFeign;
	@Autowired
	private AccountBookMapper AccountBookMapper;

	@RequestMapping(value = "/doNotify")
	@ApiOperation(value = "支付宝异步支付回调（app内支付）", notes = "支付宝异步支付回调（app内支付）", httpMethod = "POST")
	public String doNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("支付宝异步支付回调(app内支付)");
		if(request == null) {
			log.error("request为空！！");
			return "fail";
		}else {
			log.info("支付宝支付结果通知接口请求数据:{}"+request.getParameterMap().toString());
		}

		try {
			Enumeration enu=request.getParameterNames();
			while(enu.hasMoreElements()){
				String paraName=(String)enu.nextElement();
				System.out.println(paraName+": "+request.getParameter(paraName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}


		Map<String,String> receiveMap = getReceiveMap(request);
		log.info("支付宝支付回调参数:" + receiveMap);

		String appId = receiveMap.get("app_id");
		boolean signVerified = false;
		try{
			if(StringUtils.isNotEmpty(appId)){
				signVerified = aliPayService.paynotifyNew(receiveMap);
			}else{
				signVerified = aliPayService.paynotify(receiveMap);
			}
			log.info("支付宝支付结果通知接口响应数据json:" + signVerified);
		}catch(Exception e){
			e.printStackTrace();
			log.error("支付宝支付结果通知接口服务端异常,异常信息---" + e.getMessage(), e);
			return "fail";
		}
		if(signVerified){
			String outTradeNo = receiveMap.get("out_trade_no");
			String tradeNo = receiveMap.get("trade_no");
			String tradeStatus = receiveMap.get("trade_status");
			String totalFee = receiveMap.get("total_fee");
			String price = receiveMap.get("price");
			String mchId = receiveMap.get("seller_id");

			if(StringUtils.isNotEmpty(appId)){
				totalFee = receiveMap.get("receipt_amount");
			}

			String msg = "out_trade_no = " + outTradeNo + "\n trade_no = "
					+ tradeNo + "\n trade_status = " + tradeStatus+ "\n totalFee = " + totalFee+ "\n price = " + price;
			log.info(msg);

			//维护payId,防止直接调用业务处理接口
			HashMap<String,String> map = AccountBookMapper.selectAccountBookByActualId(outTradeNo);
			if(map!=null){
				String payId = map.get("payId") == null ? "" : (String)map.get("payId");
				String actualId = map.get("actualId") == null ? "" : (String)map.get("actualId");
				String accountPid = map.get("accountPid") == null ? "" : (String)map.get("accountPid");
				String payStatus = map.get("payStatus") == null ? "" : (String)map.get("payStatus");
				if("2".equals(payStatus)){
					log.info("该笔订单已支付过" );
					return "success";
				}

				if(outTradeNo.equals(accountPid)){
					//按accountPid维护payId
					AccountBookMapper.updatePayIdByActualPid(outTradeNo,tradeNo);
				}else if(outTradeNo.equals(actualId)){
					//按actualId维护payId
					AccountBookMapper.updatePayIdByActualId(outTradeNo,tradeNo);
				}else{
					log.info("虚假请求" );
					return "fail";
				}
			}

			PayOrderFinishIn payOrderFinishIn = new PayOrderFinishIn();
			payOrderFinishIn.setPayType("1");
			payOrderFinishIn.setActualId(outTradeNo);
			payOrderFinishIn.setPayId(tradeNo);
			payOrderFinishIn.setTotalFee(totalFee);
			payOrderFinishIn.setMchId(mchId);
			ObjectRestResponse objectRestResponse = commodityFeign.doPayOrderFinish(payOrderFinishIn);
			if(objectRestResponse.getStatus()==200){
				return "success";
			}else{
				return "fail";
			}
		}else{
			return "fail";
		}
	}

	private static Map<String,String> getReceiveMap(HttpServletRequest request){
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			valueStr = org.apache.commons.lang.StringEscapeUtils.unescapeHtml(valueStr);
			//乱码解决，这段代码在出现乱码时使用。
//            try {
//                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//            }catch (UnsupportedEncodingException e) {
//                log.error("支付宝支付回调解析参数异常:" + e.getMessage(), e);
//            }
			params.put(name, valueStr);
		}

		return params;
	}

    @PostMapping(value = "/refund")
    @ApiOperation(value = "支付宝退款", notes = "支付宝退款", httpMethod = "POST")
    @CheckUserToken
    public ObjectRestResponse refund(@RequestBody @ApiParam AliRefundTO aliRefundTO) {
        return aliPayService.refund(aliRefundTO);
    }

    @PostMapping(value = "/aliAppPay")
    @ApiOperation(value = "支付宝", notes = "支付宝app支付", httpMethod = "POST")
    @CheckUserToken
    public ObjectRestResponse aliAppPay(@RequestBody @ApiParam GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        generateUnifiedOrderIn.check();
        return aliPayService.aliAppPay(generateUnifiedOrderIn);
    }

    @RequestMapping(value = "/doNotifyByPubCert")
    @ApiOperation(value = "支付宝异步支付回调（app内支付）", notes = "支付宝异步支付回调（app内支付）", httpMethod = "POST")
    public String doNotifyByPubCert(HttpServletRequest request, HttpServletResponse response) {
        //获取支付宝POST过来反馈信息
        Map<String, String> receiveMap = getReceiveMap(request);
        boolean flag = false;
        try {
            flag = aliPayService.paynotifyByPubCert(receiveMap);
        } catch (Exception e) {
            log.error("支付宝回调验签失败：param:{}", JSON.toJSONString(receiveMap), e);
        }
        return flag ? "success" : "fail";
    }
}
