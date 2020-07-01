package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizUserSignRule;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizUserSignRuleMapper;
import com.github.wxiaoqi.security.jinmao.vo.sign.in.SignParams;
import com.github.wxiaoqi.security.jinmao.vo.sign.out.SignList;
import com.github.wxiaoqi.security.jinmao.vo.sign.out.StatDataVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 运营服务-签到规则表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@Service
public class BizUserSignRuleBiz extends BusinessBiz<BizUserSignRuleMapper,BizUserSignRule> {

    @Autowired
    private BizUserSignRuleMapper signRuleMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private OssExcelFeign ossExcelFeign;

    public List<SignList> getSignList(String searchVal,String signType, Integer page, Integer limit) {

        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        int startIndex = (page - 1) * limit;
        List<SignList> signLists =  signRuleMapper.getSignList(searchVal,signType, startIndex, limit);
        if (signLists.size()==0) {
            signLists = new ArrayList<>();
        }
        return signLists;
    }


    public int getSignListTotal(String searchVal,String signType) {
        return signRuleMapper.getSignListTotal(searchVal,signType);
    }

    public ObjectRestResponse createSign(SignParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        int signCount =  signRuleMapper.getSignType();
        int signDay =  signRuleMapper.getSignDay(params.getSignDay());
        if ("1".equals(params.getSignType()) && signCount > 0) {
            response.setStatus(101);
            response.setMessage("正常签到数据已存在");
            return response;
        }
        if (params.getSignDay() != null) {
            if ("2".equals(params.getSignType()) && signDay > 0) {
                response.setStatus(101);
                response.setMessage("连续签到"+params.getSignDay()+"天已经存在");
                return response;
            }
            if("2".equals(params.getSignType()) && params.getSignDay()<=1){
                response.setStatus(101);
                response.setMessage("连续签到天数需要大于1");
                return response;
            }
        }
        BizUserSignRule signRule = new BizUserSignRule();
        ObjectRestResponse<String> code = codeFeign.getCode("sign", "SG", "6", "0");
        if (code.getStatus() == 200) {
            signRule.setCode(code.getData() == null ? "" : code.getData());
        }
        signRule.setId(UUIDUtils.generateUuid());
        signRule.setSignType(params.getSignType());
        signRule.setSignDay(params.getSignDay());
        signRule.setIntegral(params.getIntegral());
        signRule.setCreateBy(BaseContextHandler.getUserID());
        signRule.setCreateTime(new Date());
        signRule.setTimeStamp(new Date());
        signRuleMapper.insertSelective(signRule);
        return response;
    }

    public ObjectRestResponse updateSign(SignParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizUserSignRule signRule = signRuleMapper.selectByPrimaryKey(params.getId());
        if (!params.getSignType().equals(signRule.getSignType()) && "1".equals(params.getSignType())) {
            int signCount =  signRuleMapper.getSignType();
            if (signCount > 0) {
                response.setStatus(101);
                response.setMessage("正常签到数据已存在");
                return response;
            }
        }
        if (params.getSignDay() != null) {
            if (!params.getSignDay().equals(signRule.getSignDay()) && "2".equals(params.getSignType())) {
                int signDay =  signRuleMapper.getSignDay(params.getSignDay());
                if (signDay > 0) {
                    response.setStatus(101);
                    response.setMessage("连续签到"+params.getSignDay()+"天已经存在");
                    return response;
                }
            }
            if("2".equals(params.getSignType()) && params.getSignDay()<=1){
                response.setStatus(101);
                response.setMessage("连续签到天数需要大于1");
                return response;
            }
        }
        BizUserSignRule userSignRule = new BizUserSignRule();
        BeanUtils.copyProperties(signRule,userSignRule);
        userSignRule.setSignType(params.getSignType());
        if ("1".equals(params.getSignType())) {
            userSignRule.setSignDay(null);
        }else {
            userSignRule.setSignDay(params.getSignDay());
        }
        userSignRule.setIntegral(params.getIntegral());
        userSignRule.setModifyBy(BaseContextHandler.getUserID());
        userSignRule.setModifyTime(new Date());
        signRuleMapper.updateByPrimaryKey(userSignRule);
        return response;
    }

    public ObjectRestResponse getSignDetail(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizUserSignRule signRule = signRuleMapper.selectByPrimaryKey(id);
        if (signRule == null) {
            response.setStatus(101);
            response.setMessage("ID出现错误");
            return response;
        }
        response.setData(signRule);
        return response;
    }

    public ObjectRestResponse deleteSign(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (signRuleMapper.deleteById(id) == 0) {
            response.setStatus(201);
            response.setMessage("删除失败");
            return response;
        }
        return response;
    }


    /**
     * 获取签到统计数据列表
     * @param projectId
     * @param dataType
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    public List<StatDataVo> getStatSignList(String projectId,String dataType,String startTime,String endTime
            ,Integer page, Integer limit){
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StatDataVo> statDataVoList = null;
        String t1 = "1";//日
        String t2 = "2";//周
        String t3 = "3";//月
        if(startTime == null || StringUtils.isEmpty(startTime) || endTime == null || StringUtils.isEmpty(endTime)) {
            endTime = sdf.format(new Date());
            if(t1.equals(dataType)){
                //天 推一个月
                startTime = getPastDate(1,"2",dataType);
            }else if(t2.equals(dataType) || t3.equals(dataType)){
                startTime = getPastDate(1,"3",dataType);
            }
        }
        if(t1.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByDay(projectId, startTime, endTime, startIndex, limit);
        }else if(t2.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByWeek(projectId, startTime, endTime, startIndex, limit);
        }else if(t3.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByMouth(projectId, startTime, endTime, startIndex, limit);
        }
        return statDataVoList;
    }


    /**
     * 获取签到统计数据列表数量
     * @param projectId
     * @param dataType
     * @param startTime
     * @param endTime
     * @return
     */
    public int selectStatSignCount(String projectId,String dataType,String startTime,String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StatDataVo> statDataVoList = null;
        String t1 = "1";//日
        String t2 = "2";//周
        String t3 = "3";//月
        if(startTime == null || StringUtils.isEmpty(startTime) || endTime == null || StringUtils.isEmpty(endTime)) {
            endTime = sdf.format(new Date());
            if(t1.equals(dataType)){
                //天 推一个月
                startTime = getPastDate(1,"2",dataType);
            }else if(t2.equals(dataType) || t3.equals(dataType)){
                startTime = getPastDate(1,"3",dataType);
            }
        }
        if(t1.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByDay(projectId, startTime, endTime, null, null);
        }else if(t2.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByWeek(projectId, startTime, endTime, null, null);
        }else if(t3.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByMouth(projectId, startTime, endTime, null, null);
        }
        return statDataVoList.size();
    }


    /**
     * 导出统计数据列表
     * @param projectId
     * @param dataType
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse exportDataSignExcel(String projectId,String dataType,String startTime,String endTime){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<Map<String, Object>> dataList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StatDataVo> statDataVoList = null;
        String t1 = "1";//日
        String t2 = "2";//周
        String t3 = "3";//月
        if(startTime == null || StringUtils.isEmpty(startTime) || endTime == null || StringUtils.isEmpty(endTime)) {
            endTime = sdf.format(new Date());
            if(t1.equals(dataType)){
                //天 推一个月
                startTime = getPastDate(1,"2",dataType);
            }else if(t2.equals(dataType) || t3.equals(dataType)){
                startTime = getPastDate(1,"3",dataType);
            }
        }
        if(t1.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByDay(projectId, startTime, endTime, null, null);
        }else if(t2.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByWeek(projectId, startTime, endTime, null, null);
        }else if(t3.equals(dataType)){
            statDataVoList = signRuleMapper.selectStatSignByMouth(projectId, startTime, endTime, null, null);
        }
        if(statDataVoList == null || statDataVoList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("没有数据，导出失败！");
            return msg;
        }
        ObjectMapper mapper = new ObjectMapper();
        if (statDataVoList != null && statDataVoList.size() > 0) {
            for (int i = 0; i < statDataVoList.size(); i++) {
                StatDataVo temp = mapper.convertValue(statDataVoList.get(i), StatDataVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"数据类型","时间范围","签到数"};
        String[] keys = {"dataType","createTime","count"};
        String fileName = "数据统计.xlsx";
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);
        return msg;
    }



    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past,String type,String dataType) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        Calendar c = Calendar.getInstance();
        String result = "";
        //减天数
        String a1 = "1";
        //减月
        String a2 = "2";
        //减年
        String a3 = "3";
        Date d = null;
        if(a1.equals(type)){
            c.setTime(new Date());
            c.add(Calendar.DATE, - past);
            d = c.getTime();
        }else if(a2.equals(type)){
            c.setTime(new Date());
            c.add(Calendar.MONTH, -past);
            d = c.getTime();
        }else if(a3.equals(type)){
            c.setTime(new Date());
            c.add(Calendar.YEAR, -past);
            d = c.getTime();
        }
        if(a1.equals(dataType)){
            result = sdf.format(d);
        }else{
            result = format.format(d);
        }
        return result;
    }






}