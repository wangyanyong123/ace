package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.integralproduct.CashVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.IntegralProductInfo;
import com.github.wxiaoqi.security.app.vo.integralproduct.IntegralProductVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.ScreenVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.in.BuyIntegralProductParam;
import com.github.wxiaoqi.security.app.vo.intergral.in.UserSignIn;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 积分商品表
 *
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
@Service
public class BizIntegralProductBiz extends BusinessBiz<BizIntegralProductMapper,BizIntegralProduct> {
    private Logger log = LoggerFactory.getLogger(BizIntegralProductBiz.class);

    @Autowired
    private BizIntegralProductMapper bizIntegralProductMapper;
    @Autowired
    private BizProductLabelMapper bizProductLabelMapper;
    @Autowired
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizCashProductMapper bizCashProductMapper;
    @Autowired
    private BizUserIntegralMapper bizUserIntegralMapper;


    /**
     * 查询积分商品列表
     * @param projectId
     * @param startIntegral
     * @param endIntegral
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<IntegralProductVo>> getIntegralProductList(String isRecommend,String projectId,String startIntegral,String endIntegral,
                                                                              Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String a = "-1";
        if( a.equals(startIntegral) || a.equals(endIntegral)){
            startIntegral = null;
            endIntegral = null;
        }
        List<IntegralProductVo> integralProductVoList = bizIntegralProductMapper.selectProductList(BaseContextHandler.getUserID(),isRecommend,
                startIntegral,endIntegral,projectId,startIndex,limit);
        if(integralProductVoList != null && integralProductVoList.size() > 0){
            for (IntegralProductVo productVo : integralProductVoList){
                //商品标签
                List<String> labelList = bizProductLabelMapper.selectLabelList(productVo.getId());
                productVo.setLabel(labelList);
            }
        }
        return ObjectRestResponse.ok(integralProductVoList);
    }


    /**
     * 查询兑换记录
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<CashVo>> getCashList(String projectId,Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<CashVo> cashVoList = bizIntegralProductMapper.selectCashList(BaseContextHandler.getUserID(),projectId,startIndex,limit);
        if(cashVoList == null || cashVoList.size() == 0){
            cashVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(cashVoList);
    }


    /**
     * 查询积分筛选范围
     * @return
     */
    public ObjectRestResponse<List<ScreenVo>> getScreenList(){
        List<ScreenVo> screenVoList =  bizIntegralProductMapper.selectIntegralScreenList();
        if(screenVoList == null || screenVoList.size() == 0){
            screenVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(screenVoList);
    }


    /**
     * 查询当前用户积分
     * @return
     */
    public ObjectRestResponse getCurrentIntegral(){
        ObjectRestResponse msg = new ObjectRestResponse();
        String currIntegral = bizIntegralProductMapper.selectCurrentIntegralByUserId(BaseContextHandler.getUserID());
        if(currIntegral == null || StringUtils.isEmpty(currIntegral)){
            currIntegral = "0";
        }
        msg.setData(currIntegral);
        return msg;
    }


    /**
     * 查询积分商品详情
     * @param id
     * @return
     */
    public ObjectRestResponse<IntegralProductInfo> getIntegralProductInfo(String id){
        IntegralProductInfo integralProductInfo = bizIntegralProductMapper.selectProductInfo(BaseContextHandler.getUserID(),id);
        if(integralProductInfo != null){
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(id);
            integralProductInfo.setLabel(labelList);
        }
        return ObjectRestResponse.ok(integralProductInfo);
    }


    /**
     * 兑换商品
     * @param param
     * @return
     */
    public ObjectRestResponse cashIntegralProduct(BuyIntegralProductParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (param == null) {
            msg.setStatus(1001);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if (StringUtils.isAnyEmpty(param.getContactName(), param.getAddr(), param.getContactTel())) {
            msg.setStatus(1002);
            msg.setMessage("联系人/电话/地址为空!");
            return msg;
        }
        String currInteger = bizIntegralProductMapper.selectCurrentIntegralByUserId(BaseContextHandler.getUserID());
        if (Integer.parseInt(param.getIntegral()) > Integer.parseInt(currInteger)) {
            msg.setStatus(1001);
            msg.setMessage("当前积分不够兑换该商品!");
            return msg;
        }
        if (bizIntegralProductMapper.selectByPrimaryKey(param.getProductId()).getProductNum() > 0) {
            if (bizIntegralProductMapper.updateStockById(param.getProductId()) <= 0) {
                msg.setStatus(1001);
                msg.setMessage("该积分商品已告罄!");
                return msg;
            }
        }
            String cashCode = "";
            List<String> projectIds = new ArrayList<>();
            projectIds.add(param.getProjectId());
            List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(projectIds);
            String projectCode = "";
            String wcode = "";
            if (bizCrmProjectBizList != null && bizCrmProjectBizList.size() > 0) {
                projectCode = bizCrmProjectBizList.get(0).getProjectCode();
            }
            String[] projectCodeArray = projectCode.split("-");
            if (projectCodeArray != null && projectCodeArray.length >= 2) {
                wcode = projectCodeArray[1];
            }
            String subCode = wcode + "-" + DateTimeUtil.shortDateString() + "-";
            ObjectRestResponse response = codeFeign.getCode("Cash", subCode, "6", "0");
            log.info("生成兑换编码处理结果：" + response.toString());
            if (response.getStatus() == 200) {
                cashCode = (String) response.getData();
            }
            BizCashProduct product = new BizCashProduct();
            product.setId(UUIDUtils.generateUuid());
            product.setCashCode(cashCode);
            product.setUserId(BaseContextHandler.getUserID());
            product.setAddr(param.getAddr());
            product.setCashIntegral(Integer.parseInt(param.getIntegral()));
            product.setCashNum(1);
            product.setContactName(param.getContactName());
            product.setContactTel(param.getContactTel());
            product.setDescription(param.getRemark());
            product.setProductId(param.getProductId());
            product.setProjectId(param.getProjectId());
            product.setTimeStamp(new Date());
            product.setCreateBy(BaseContextHandler.getUserID());
            product.setCreateTime(new Date());
            if (bizCashProductMapper.insertSelective(product) <= 0) {
                msg.setStatus(1001);
                msg.setMessage("兑换失败!");
                return msg;
            } else {
                UserSignIn userSignIn = bizIntegralProductMapper.selectConsumePointByUser(BaseContextHandler.getUserID());
                if (userSignIn != null) {
                    BizUserIntegral integral = new BizUserIntegral();
                    integral.setId(userSignIn.getId());
                    integral.setConsumePoints((Integer.parseInt(param.getIntegral()) + userSignIn.getConsumePoints()));
                    integral.setModifyBy(BaseContextHandler.getUserID());
                    integral.setModifyTime(new Date());
                    bizUserIntegralMapper.updateByPrimaryKeySelective(integral);
                }
            }
            msg.setMessage("Operation succeed!");
            return msg;
        }


}