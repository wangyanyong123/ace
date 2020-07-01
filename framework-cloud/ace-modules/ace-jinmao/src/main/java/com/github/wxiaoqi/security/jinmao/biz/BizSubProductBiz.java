package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizSubProduct;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductClassifyMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizSubProductMapper;
import com.github.wxiaoqi.security.jinmao.vo.account.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单产品表
 *
 * @author huangxl
 * @Date 2019-03-25 10:52:39
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizSubProductBiz extends BusinessBiz<BizSubProductMapper,BizSubProduct> {

    @Autowired
    private BizSubProductMapper bizSubProductMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizProductProjectMapper bizProductProjectMapper;
    @Autowired
    private BizProductClassifyMapper bizProductClassifyMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;

    /**
     * 查询商品销售明细列表
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    public List<AccountDetailVo> getAccountDetailList(String searchVal, String projectId, String startTime, String endTime, Integer page, Integer limit){
        //ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
        List<AccountDetailVo> accountDetailList = null;
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
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        accountDetailList = bizSubProductMapper.selectAccountDetailList(type,BaseContextHandler.getTenantID(),searchVal,projectId,
                startTime,endTime,startIndex,limit);
        for (AccountDetailVo detailVo : accountDetailList){
            List<String> projectNames = bizProductProjectMapper.selectProjectIdById(detailVo.getId());
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String project:projectNames){
                    resultEva.append(project + ",");
                }
                projectName = resultEva.substring(0, resultEva.length() - 1);
                detailVo.setProjectName(projectName);
            }
            List<String> classifyNames = bizProductClassifyMapper.selectClassifyNameById(detailVo.getId());
            if(classifyNames != null && classifyNames.size()>0){
                String classifyName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String classify:classifyNames){
                    resultEva.append(classify + ",");
                }
                classifyName = resultEva.substring(0, resultEva.length() - 1);
                detailVo.setClassifyName(classifyName);
            }

            detailVo.setOrderCost(df.format(Double.parseDouble(detailVo.getOrderCost())));
        }
        return accountDetailList;
    }



   public int selectAccountDetailCount(String searchVal,String projectId,String startTime,String endTime){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        return bizSubProductMapper.selectAccountDetailCount(type,BaseContextHandler.getTenantID(),searchVal,projectId,startTime,endTime);
    }



    /**
     * 导出商品销售明细列表
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse exportAccountDetail(String searchVal,String projectId,String startTime,String endTime){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        //商品销售明细列表
        List<AccountDetailVo> detailVoList = bizSubProductMapper.selectExportAccountDetail(type,BaseContextHandler.getTenantID(),searchVal,projectId,startTime,endTime);
        for (AccountDetailVo detailVo : detailVoList){
            List<String> projectNames = bizProductProjectMapper.selectProjectIdById(detailVo.getId());
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String project:projectNames){
                    resultEva.append(project + ",");
                }
                projectName = resultEva.substring(0, resultEva.length() - 1);
                detailVo.setProjectName(projectName);
            }
            List<String> classifyNames = bizProductClassifyMapper.selectClassifyNameById(detailVo.getId());
            if(classifyNames != null && classifyNames.size()>0){
                String classifyName = "";
                StringBuilder resultEva = new StringBuilder();
                for (String classify:classifyNames){
                    resultEva.append(classify + ",");
                }
                classifyName = resultEva.substring(0, resultEva.length() - 1);
                detailVo.setClassifyName(classifyName);
            }
            detailVo.setOrderCost(df.format(Double.parseDouble(detailVo.getOrderCost())));
        }
        if(detailVoList == null || detailVoList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (detailVoList != null && detailVoList.size() > 0) {
            for (int i = 0; i < detailVoList.size(); i++) {
                AccountDetailVo temp = mapper.convertValue(detailVoList.get(i), AccountDetailVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"商品编码","商品名称","所属分类","所属商家","发布项目","单价","销量","订单金额","发布时间","商品状态"};
        String[] keys = {"productCode","productName","classifyName","tenantName","projectName","price","sales","orderCost","publishTime","busStatus"};
        String fileName ="商品销售明细.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }



    /**
     * 销量与订单总金额的占比
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse<BusAccountInfo> getBusInfo(String searchVal, String projectId, String startTime, String endTime){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(DateUtils.daysBetween(sdf.parse(startTime),sdf.parse(endTime)) > 60){
                msg.setStatus(501);
                msg.setMessage("最多查询60天的数据");
                return msg;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BusAccountInfo busAccountInfo = new BusAccountInfo();

        DecimalFormat df =new  DecimalFormat("0.00");
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<BusInfo> busInfoList = bizSubProductMapper.selectBusInfo(type,BaseContextHandler.getTenantID(),
                searchVal,projectId,startTime,endTime);
        List<BusInfo> classifyInofList = bizSubProductMapper.selectClassifyInfo(type,BaseContextHandler.getTenantID(),
                searchVal,projectId,startTime,endTime);
        List<Map<String,String>> bSalesMap = new ArrayList<>();
        List<Map<String,String>> bOrderMap = new ArrayList<>();

        if(busInfoList != null && busInfoList.size() > 0){
            //业务销量
            for(BusInfo info: busInfoList){
                Map<String,String> salesInfo = new LinkedHashMap<>();
                salesInfo.put("name",info.getName());
                salesInfo.put("value",info.getSalesTotal());
                bSalesMap.add(salesInfo);
            }
            //业务订单总金额
            for(BusInfo info: busInfoList){
                Map<String,String> orderCostInfo = new LinkedHashMap<>();
                orderCostInfo.put("name",info.getName());
                orderCostInfo.put("value",df.format(Double.parseDouble(info.getOderCostTotal())));
                bOrderMap.add(orderCostInfo);
            }
        }

        List<Map<String,String>> cSalesMap = new ArrayList<>();
        List<Map<String,String>> cOrderMap = new ArrayList<>();

        if(classifyInofList != null && classifyInofList.size() > 0){
            //分类销量
            for(BusInfo info: classifyInofList){
                Map<String,String> csalesInfo = new LinkedHashMap<>();
                csalesInfo.put("name",info.getName());
                csalesInfo.put("value",info.getSalesTotal());
                cSalesMap.add(csalesInfo);
            }
            //分类订单总金额
            for(BusInfo info: classifyInofList){
                Map<String,String> corderCostInfo = new LinkedHashMap<>();
                corderCostInfo.put("name",info.getName());
                corderCostInfo.put("value",df.format(Double.parseDouble(info.getOderCostTotal())));
                cOrderMap.add(corderCostInfo);
            }
        }

        busAccountInfo.setBusSalesAccount(bSalesMap);
        busAccountInfo.setBusOrderAccount(bOrderMap);
        busAccountInfo.setClassifySalesAccount(cSalesMap);
        busAccountInfo.setClassifyOrderAccount(cOrderMap);
        return  ObjectRestResponse.ok(busAccountInfo);
    }






    /**
     * 查询项目销量与订单总金额的占比
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse<ProjectAccountInfo> getProjectInfo(String searchVal, String projectId, String startTime, String endTime){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(DateUtils.daysBetween(sdf.parse(startTime),sdf.parse(endTime)) > 60){
                msg.setStatus(501);
                msg.setMessage("最多查询60天的数据");
                return msg;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ProjectAccountInfo projectAccountInfo = new ProjectAccountInfo();

        DecimalFormat df =new  DecimalFormat("0.00");
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<BusInfo> projectInfoList = bizSubProductMapper.selectProjectInfo(type,BaseContextHandler.getTenantID(),
                searchVal,projectId,startTime,endTime);
        List<Map<String,String>> pSalesMap = new ArrayList<>();
        List<Map<String,String>> pOrderMap = new ArrayList<>();

        if(projectInfoList != null && projectInfoList.size() > 0){
            //业务销量
            for(BusInfo info: projectInfoList){
                Map<String,String> psalesInfo = new LinkedHashMap<>();
                psalesInfo.put("name",info.getName());
                psalesInfo.put("value",info.getSalesTotal());
                pSalesMap.add(psalesInfo);
            }
            //业务订单总金额
            for(BusInfo info: projectInfoList){
                Map<String,String> porderCostInfo = new LinkedHashMap<>();
                porderCostInfo.put("name",info.getName());
                porderCostInfo.put("value",df.format(Double.parseDouble(info.getOderCostTotal())));
                pOrderMap.add(porderCostInfo);
            }
        }
        projectAccountInfo.setPsalesAccount(pSalesMap);
        projectAccountInfo.setPorderAccount(pOrderMap);

        return  ObjectRestResponse.ok(projectAccountInfo);
    }



    /**
     * 查询起止时间销量与订单总金额的占比
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse<TimeAccountInfo> getTimeInfo(String searchVal, String projectId, String startTime, String endTime){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(DateUtils.daysBetween(sdf.parse(startTime),sdf.parse(endTime)) > 60){
                msg.setStatus(501);
                msg.setMessage("最多查询60天的数据");
                return msg;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeAccountInfo timeAccountInfo = new TimeAccountInfo();

        DecimalFormat df =new  DecimalFormat("0.00");
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<BusInfo> projectInfoList = bizSubProductMapper.selectTimeInfo(type,BaseContextHandler.getTenantID(),
                searchVal,projectId,startTime,endTime);
        List<Map<String,String>> tSalesMap = new ArrayList<>();
        List<Map<String,String>> tOrderMap = new ArrayList<>();

        if(projectInfoList != null && projectInfoList.size() > 0){
            //销量
            for(BusInfo info: projectInfoList){
                Map<String,String> psalesInfo = new LinkedHashMap<>();
                psalesInfo.put("name",info.getName());
                psalesInfo.put("value",info.getSalesTotal());
                tSalesMap.add(psalesInfo);
            }
            //订单总金额
            for(BusInfo info: projectInfoList){
                Map<String,String> porderCostInfo = new LinkedHashMap<>();
                porderCostInfo.put("name",info.getName());
                porderCostInfo.put("value",df.format(Double.parseDouble(info.getOderCostTotal())));
                tOrderMap.add(porderCostInfo);
            }

        }
        timeAccountInfo.setTsalesAccount(tSalesMap);
        timeAccountInfo.setTorderAccount(tOrderMap);

        return  ObjectRestResponse.ok(timeAccountInfo);
    }


    /**
     * 查询商家对账列表
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    public List<CheckAccountVo> getCheckAccountList(String tenantId, String startTime, String endTime, Integer page, Integer limit){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
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
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(tenantId == null || "".equals(tenantId)){
            tenantId = BaseContextHandler.getTenantID();
        }else{
            if("3".equals(type)){
                type = "2";
            }
        }
        List<CheckAccountVo> accountVoList =bizSubProductMapper.selectCheckAccountList(type,tenantId,
                startTime,endTime,startIndex,limit);
        if(accountVoList == null || accountVoList.size() == 0){
            accountVoList = new ArrayList<>();
        }
        return accountVoList;
    }


    public int selectCheckAccountCount(String tenantId,String startTime, String endTime){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(tenantId == null || "".equals(tenantId)){
            tenantId = BaseContextHandler.getTenantID();
        }else{
            if("3".equals(type)){
                type = "2";
            }
        }
        return bizSubProductMapper.selectCheckAccountCount(type,tenantId,startTime,endTime);
    }



    /**
     * 导出商家对账列表
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse exportCheckAccount(String tenantId,String startTime, String endTime){
        ObjectRestResponse msg = new ObjectRestResponse();
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(tenantId == null || "".equals(tenantId)){
            tenantId = BaseContextHandler.getTenantID();
        }else{
            if("3".equals(type)){
                type = "2";
            }
        }
        //商家对账列表
        List<CheckAccountVo> accountVoList = bizSubProductMapper.selectExportCheckAccount(type,tenantId,startTime,endTime);
        if(accountVoList == null || accountVoList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (accountVoList != null && accountVoList.size() > 0) {
            for (int i = 0; i < accountVoList.size(); i++) {
                CheckAccountVo temp = mapper.convertValue(accountVoList.get(i), CheckAccountVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"账单编号","商户名称","结算金额","结算周期","收款账号","账期","结算状态"};
        String[] keys = {"billNumber","tenantName","balanceMoney","settlementCycle","accountNumber","paymentDay","balanceStatus"};
        String fileName ="商家对账明细.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }


    /**
     * 查询结算状态占比与周期结算金额占比
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse<BalanceStatusInfo> getBalanceStatusInfo(String tenantId,String startTime, String endTime){
        BalanceStatusInfo balanceStatusInfo = new BalanceStatusInfo();
        DecimalFormat df =new  DecimalFormat("0.00");

        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(tenantId == null || "".equals(tenantId)){
            tenantId = BaseContextHandler.getTenantID();
        }else{
            if("3".equals(type)){
                type = "2";
            }
        }
        List<BusInfo> settlementInfoList = bizSubProductMapper.selectSettlementInfo(type,tenantId,startTime,endTime);
        List<BusInfo> balanceStatusInfoList = bizSubProductMapper.selectBalanceStatusInfo(type,tenantId,startTime,endTime);

        List<Map<String,String>> balanceStatusMap = new ArrayList<>();
        List<Map<String,String>> settlementMap = new ArrayList<>();

        if(balanceStatusInfoList != null && balanceStatusInfoList.size() > 0){
            //结算状态
            for(BusInfo info: balanceStatusInfoList){
                Map<String,String> balanceInfo = new LinkedHashMap<>();
                balanceInfo.put("name",info.getName());
                balanceInfo.put("value",info.getSalesTotal());
                balanceStatusMap.add(balanceInfo);
            }
        }
        if(settlementInfoList != null && settlementInfoList.size() > 0){
            //周期结算金额
            for(BusInfo info: settlementInfoList){
                Map<String,String> settlementInfo = new LinkedHashMap<>();
                settlementInfo.put("name",info.getName());
                settlementInfo.put("value",df.format(Double.parseDouble(info.getSalesTotal())));
                settlementMap.add(settlementInfo);
            }
        }

        balanceStatusInfo.setBalanceStatusAccount(balanceStatusMap);
        balanceStatusInfo.setSettlementAccount(settlementMap);

        return  ObjectRestResponse.ok(balanceStatusInfo);
    }













}