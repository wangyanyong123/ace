package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.*;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.api.vo.order.out.SubListVo;
import com.github.wxiaoqi.security.app.biz.BizSubBiz;
import com.github.wxiaoqi.security.app.biz.BizSubscribeWoBiz;
import com.github.wxiaoqi.security.app.biz.PaySuccessBiz;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubscribeMapper;
import com.github.wxiaoqi.security.app.vo.product.out.ProductInfo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品下单模块
 *
 * @author huangxl
 * @Date 2018-12-03 10:00:01
 */
@Slf4j
@RestController
@RequestMapping("commodity")
@CheckClientToken
@CheckUserToken
@Api(tags="商品下单模块")
public class CommodityController {

	@Autowired
	private BizSubBiz bizSubBiz;
	@Autowired
	private BizSubscribeWoBiz bizSubscribeWoBiz;
	@Autowired
	private BizSubscribeMapper bizSubscribeMapper;
	@Autowired
	private BizProductMapper bizProductMapper;
	@Autowired
	private ToolFegin toolFegin;
	@Autowired
	private PaySuccessBiz paySuccessBiz;

	/**
	 * 优选商城产品下单
	 * @return
	 */
	@RequestMapping(value = "/buyCompanyProduct" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "优选商城产品下单", notes = "商品下单模块---优选商城产品下单",httpMethod = "POST")
	public ObjectRestResponse<BuyProductOutVo> buyCompanyProduct(@RequestBody @ApiParam BuyProductInfo buyProductInfo) throws Exception{
		ObjectRestResponse result = new ObjectRestResponse();
		if(buyProductInfo==null) {
			result.setStatus(101);
			result.setMessage("参数为空");
			return result;
		}
		if(StringUtils.isAnyoneEmpty(buyProductInfo.getContactName(),buyProductInfo.getContactTel(),buyProductInfo.getAddr(),
				buyProductInfo.getProjectId(),buyProductInfo.getSource(),buyProductInfo.getProcCode())) {
			result.setStatus(101);
			result.setMessage("参数为空");
			return result;
		}
		List<CompanyProduct> companyProductList = buyProductInfo.getCompanyProductList();
		if(companyProductList==null || companyProductList.size()==0){
			result.setStatus(101);
			result.setMessage("参数为空");
			return result;
		}
		int total = 0;
		String busId = BusinessConstant.getShoppingBusId();
		for(CompanyProduct companyProduct : companyProductList){
			List<SubProduct> subProductList = companyProduct.getSubProductList();
			for(SubProduct subProduct : subProductList){
				total = subProduct.getSubNum()+total;
				ProductInfo productInfo = bizProductMapper.selectProductInfoById(subProduct.getProductId(),BaseContextHandler.getUserID());
				if(productInfo != null){
					if(productInfo.getLimitNum() != -1){
						int num = bizProductMapper.getUserBuyNumById(subProduct.getProductId(),BaseContextHandler.getUserID());
						if (num > 0){
							total = total + num;
						}
						if(productInfo.getLimitNum() < total){
							result.setStatus(104);
							result.setMessage("你购买的数量,已达到购买上限!");
							return result;
						}
					}
					if(productInfo.getProductNum() != -1 && productInfo.getProductNum() != 0){
						if(productInfo.getProductNum() < subProduct.getSubNum()){
							result.setStatus(104);
							result.setMessage("你购买的数量，已超过当前库存量");
							return result;
						}
						//直接判断库存是否够并减库存
						int stockResult = bizProductMapper.updateStockById(subProduct.getProductId(), subProduct.getSubNum());
						if (stockResult<=0) {
							result.setStatus(104);
							result.setMessage("商品已售罄");
							return result;
						}
					}else {
						bizProductMapper.addStockById(subProduct.getProductId(), subProduct.getSubNum());
					}
				}
				busId = bizSubscribeMapper.selectBusIdByProductId(subProduct.getProductId());
			}
		}
		return bizSubBiz.buyProduct(buyProductInfo,busId);
	}

	/**
	 * 团购产品下单
	 * @return
	 */
	@RequestMapping(value = "/buyGroupProduct" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "团购产品下单", notes = "商品下单模块---团购产品下单",httpMethod = "POST")
	public ObjectRestResponse<BuyProductOutVo> buyGroupProduct(@RequestBody @ApiParam BuyProductInfo buyProductInfo) throws Exception {
		ObjectRestResponse result = new ObjectRestResponse();
		if(buyProductInfo==null) {
			result.setStatus(101);
			result.setMessage("参数为空");
			return result;
		}
		if(StringUtils.isAnyoneEmpty(buyProductInfo.getContactName(),buyProductInfo.getContactTel(),buyProductInfo.getAddr(),
				buyProductInfo.getProjectId(),buyProductInfo.getSource(),buyProductInfo.getProcCode())) {
			result.setStatus(101);
			result.setMessage("参数为空");
			return result;
		}
		List<CompanyProduct> companyProductList = buyProductInfo.getCompanyProductList();
		if(companyProductList==null || companyProductList.size()==0){
			result.setStatus(101);
			result.setMessage("参数为空");
			return result;
		}
		if(companyProductList.size()>1){
			result.setStatus(101);
			result.setMessage("团购产品只能下一个公司的订单");
			return result;
		}
		List<SubProduct> subProductList = companyProductList.get(0).getSubProductList();
		if(subProductList==null || subProductList.size()!=1){
			result.setStatus(101);
			result.setMessage("团购产品只能下一个产品的订单");
			return result;
		}

		String busId = BusinessConstant.getGroupBuyingBusId();
		return bizSubBiz.buyProduct(buyProductInfo,busId);
	}

		/**
         * 客户端APP查询我的订单列表
         * @param searchSubOrderIn 参数
         * @return
         */
	@RequestMapping(value = "/getSubListForClientApp" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "客户端APP查询我的订单列表", notes = "订单工单模块---客户端APP查询我的订单列表",httpMethod = "POST")
	public ObjectRestResponse<List<SubListVo>> getSubListForClientApp(@RequestBody @ApiParam SearchSubOrderIn searchSubOrderIn) {
		ObjectRestResponse objectRestResponse = new ObjectRestResponse();
		String userId = BaseContextHandler.getUserID();
		if(StringUtils.isEmpty(userId)){
			objectRestResponse.setStatus(101);
			objectRestResponse.setMessage("用户未登陆，请登陆系统");
			return objectRestResponse;
		}

		int page = searchSubOrderIn.getPage();
		int limit = searchSubOrderIn.getLimit();
		if (page<1) {
			page = 1;
		}
		if (limit<1) {
			limit = 10;
		}
		//分页
		int startIndex = (page - 1) * limit;
		List<SubListVo> subList = null;
		Map<String,Object> paramMap = new HashMap<>();
		if("99".equals(searchSubOrderIn.getSubscribeStatus())){
			searchSubOrderIn.setSubscribeStatus(null);
		}
		if("4".equals(searchSubOrderIn.getSubscribeStatus())){
			//待收货
			searchSubOrderIn.setSubscribeStatus("5");
		}
		if("3".equals(searchSubOrderIn.getSubscribeStatus())){
			//待评价
			searchSubOrderIn.setSubscribeStatus("4");
			paramMap.put("commentStatus","1");
		}
		paramMap.put("subscribeStatus",searchSubOrderIn.getSubscribeStatus());
		paramMap.put("userId",userId);
		paramMap.put("page",startIndex);
		paramMap.put("limit",limit);
		subList = bizSubscribeMapper.getSubListForClientApp(paramMap);
		if(subList==null || subList.size()==0){
			subList = new ArrayList<>();
		}
		objectRestResponse.setData(subList);
		return objectRestResponse;
	}

	/**
	 * 支付宝、微信回调通知成功后业务处理
	 * @param payOrderFinishIn 参数
	 * @return
	 */
	@RequestMapping(value = "/doPayOrderFinish" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "支付宝、微信回调通知成功后业务处理", notes = "订单工单模块---支付宝、微信回调通知成功后业务处理",httpMethod = "POST")
	@IgnoreUserToken
	@IgnoreClientToken
	public ObjectRestResponse doPayOrderFinish(@RequestBody @ApiParam PayOrderFinishIn payOrderFinishIn) {
		return paySuccessBiz.doPayOrderFinish(payOrderFinishIn);
	}


	@RequestMapping(value = "/finishGroupProduct" ,method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "团购商品手动成团", notes = "团购商品手动成团",httpMethod = "GET")
	public ObjectRestResponse finishGroupProduct(String id) {
		return bizSubscribeWoBiz.finishGroupProduct(id);
	}

    @RequestMapping(value = "/generateUnifiedOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "支付宝/微信 app支付接口", notes = "支付宝/微信 app支付接口", httpMethod = "POST")
    public ObjectRestResponse generateUnifiedOrder(@RequestBody @ApiParam GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        generateUnifiedOrderIn.check();
        if (AceDictionary.PAY_TYPE_ALIPAY.equals(generateUnifiedOrderIn.getPayType())) {
            return toolFegin.aliAppPay(generateUnifiedOrderIn);
        } else {
            ObjectRestResponse objectRestResponse = new ObjectRestResponse();
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("尚不支持微信支付");
            return objectRestResponse;
        }
    }

}