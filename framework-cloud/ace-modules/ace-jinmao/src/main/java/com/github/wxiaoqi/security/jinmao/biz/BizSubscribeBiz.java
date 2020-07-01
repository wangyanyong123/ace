package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.api.vo.order.in.SearchSubInWeb;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.jinmao.entity.BizSubscribe;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.ProjectInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.BillDateVo;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.BillDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.UserAllBillList;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.UserBillVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单表
 *
 * @Date 2019-02-18 16:37:08
 */
@Service
public class BizSubscribeBiz extends BusinessBiz<BizSubscribeMapper, BizSubscribe> {

    @Autowired
    private BizSubscribeMapper bizSubscribeMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
    private BizProductMapper bizProductMapper;

    @Autowired
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Autowired
    private BizRefundAuditMapper bizRefundAuditMapper;
    @Autowired
    private BizTransactionLogMapper bizTransactionLogMapper;
    @Autowired
    private BizSubProductMapper bizSubProductMapper;

    /**
     * 后台物业缴费订单列表查询
     * 查询字段：项目、用户、订单号
     * 功能：查询、导出excel、详情
     * @return
     */
    public List<UserBillVo> getPropertyBillList(String projectId,String subStatus,String syncStatus,String searchVal ,Integer page,Integer limit) {

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

        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<UserBillVo> propertyBillList = bizSubscribeMapper.getPropertyBillList(projectId, subStatus,syncStatus, searchVal, startIndex, limit,BaseContextHandler.getTenantID(),type);
        for (UserBillVo userBillVo : propertyBillList) {
            String roomName = bizSubscribeMapper.getRoomNameBySubId(userBillVo.getRoomId());
            userBillVo.setRoomName(roomName);
        }
        if (propertyBillList == null || propertyBillList.size() == 0) {
            propertyBillList = new ArrayList<>();
        }

        return propertyBillList;
    }

    public int getPropertyBillCount(String projectId,String subStatus,String searchVal) {
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        return bizSubscribeMapper.getPropertyBillCount(projectId,subStatus,searchVal,BaseContextHandler.getUserID(),type);
    }

    /**
     * 查询物业缴费详情
     * @param id
     * @return
     */
    public ObjectRestResponse getPropertyDetailById(String id) {
        ObjectRestResponse response = new ObjectRestResponse();

        //获取物业缴费详情
        BillDetailVo detail = bizSubscribeMapper.getPropertyBillDetail(id);
        String roomName = bizSubscribeMapper.getRoomNameBySubId(detail.getRoomId());
        detail.setRoomName(roomName);
        //获取费用详情
        List<BillDateVo> fee = bizSubscribeMapper.getPropertyFeeById(id);
        if (fee.size() == 0 || fee == null) {
            fee = new ArrayList<>();
        }else{
            for (BillDateVo vo : fee){
                vo.setYear(vo.getShouldDate().substring(0,4));
                vo.setMouth(vo.getShouldDate().substring(4,6));
            }
        }
        detail.setBillFee(fee);

        //app缴费明细
        List<UserAllBillList> allBillList = bizSubscribeMapper.selectBillListFeeById(id);
        if(allBillList != null){
            for (UserAllBillList vo :allBillList ){
                List<BillDateVo> billDateVoList = bizSubscribeMapper.selectPayBillFeeById(id,vo.getShouldDate());
                vo.setShouldDateList(billDateVoList);
                vo.setYear(vo.getShouldDate().substring(0,4));
                vo.setMouth(vo.getShouldDate().substring(4,6));
            }
        }
        detail.setUserAllBillLists(allBillList);

        response.setData(detail);
        return response;
    }


    public ObjectRestResponse getSubExcel(String projectId,String subStatus,String syncStatus,String searchVal,Integer page,Integer limit) throws Exception {
        ObjectRestResponse msg = new ObjectRestResponse();
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        String[] titles;
        String[] keys;
        if(userInfo!=null){
            if("3".equals(userInfo.getTenantType()) && "超级管理员".equals(userInfo.getUserName())) {
                //admin
                titles = new String[]{"账单编号", "账单名称", "住户名称", "联系方式","所属中心城市","公司名称","居住房屋", "总金额", "所属账期", "账单明细", "支付状态", "支付时间", "支付方式","是否开发票", "同步状态"};
                keys = new String[]{"subCode", "title", "name", "phone","centerCityName","companyName","roomName", "actualCostStr", "shouldDate", "shouldAmount", "payStatusStr", "payDate", "payTypeStr","invoiceType", "noticeStaStr"};
            }else{
                titles  = new String[]{"账单编号", "账单名称", "住户名称", "联系方式","所属中心城市","公司名称","居住房屋", "总金额", "所属账期", "账单明细", "支付状态", "支付时间", "支付方式","是否开发票"};
                keys = new String[]{"subCode", "title", "name", "phone","centerCityName","companyName", "roomName", "actualCostStr", "shouldDate", "shouldAmount", "payStatusStr", "payDate", "payTypeStr","invoiceType"};
            }
        }else{
            msg.setStatus(101);
            msg.setMessage("非法用户");
            return msg;
        }
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<UserBillVo> propertyBillList = bizSubscribeMapper.getPropertyBillList(projectId, subStatus,syncStatus, searchVal, page, limit,BaseContextHandler.getTenantID(),type);
        for (UserBillVo userBillVo : propertyBillList) {
            if("0".equals(userBillVo.getInvoiceType())){
                userBillVo.setInvoiceType("否");
            }else{
                userBillVo.setInvoiceType("是");
            }
            String roomName = bizSubscribeMapper.getRoomNameBySubId(userBillVo.getRoomId());
            userBillVo.setRoomName(roomName);
        }
        if (propertyBillList == null || propertyBillList.size() == 0) {
            msg.setStatus(102);
            msg.setMessage("没有数据，导出失败！");
            return msg;
        }
        for (UserBillVo userBillVo : propertyBillList) {
            List<BillDateVo> propertyFee = bizSubscribeMapper.getPropertyFeeById(userBillVo.getId());
            String shouldDate = "";
            String shouldAmount = "";
            for (BillDateVo billDateVo : propertyFee) {
                shouldDate += billDateVo.getShouldDate() + "/";
                shouldAmount += billDateVo.getShouldAmountStr()+"(元)/";
            }
            userBillVo.setShouldDate(shouldDate.substring(0,shouldDate.length()-1));
            userBillVo.setShouldAmount(shouldAmount.substring(0, shouldAmount.length() - 1));
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if(propertyBillList != null && propertyBillList.size() > 0 ){
            for (int i = 0; i < propertyBillList.size(); i++) {
                UserBillVo temp = mapper.convertValue(propertyBillList.get(i), UserBillVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
//        String[] titles = {"账单编号","账单名称","住户名称","联系方式","居住房屋","总金额","所属账期","账单明细","支付状态","支付时间","支付方式","同步状态"};
//        String[] keys = {"subCode","title","name","phone","roomName","actualCostStr","shouldDate","shouldAmount","payStatusStr","payDate","payTypeStr","noticeStaStr"};
        String fileName = "物业账单.xlsx";
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }

    public List<Map> querySubList(SearchSubInWeb searchSubInWeb, boolean isReservation) {
        List<String> busIdList = new ArrayList<>();
        if (isReservation) {
            busIdList.add(BusinessConstant.getReservationBusId());
        } else {
            busIdList.add(BusinessConstant.getShoppingBusId());
            busIdList.add(BusinessConstant.getGroupBuyingBusId());
            busIdList.add(BusinessConstant.getSeckillBusId());
            busIdList.add(BusinessConstant.getTravelBusId());
        }

        int page = searchSubInWeb.getPage();
        int limit = searchSubInWeb.getLimit();
        if (page<0) {
            page = 1;
        }
        if (limit<0) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        Map<String,Object> paramMap = new HashMap<>();
        if (page != 0 && limit != 0) {
            paramMap.put("page",startIndex);
            paramMap.put("limit",limit);
        }
        paramMap.put("busIdList",busIdList);
        paramMap.put("searchVal",searchSubInWeb.getSearchVal());
        paramMap.put("startDate",searchSubInWeb.getStartDate());
        paramMap.put("endDate",searchSubInWeb.getEndDate());

        paramMap.put("startExpectedServiceTime",searchSubInWeb.getStartExpectedServiceTime());
        paramMap.put("endExpectedServiceTime",searchSubInWeb.getEndExpectedServiceTime());
        paramMap.put("projectId",searchSubInWeb.getProjectId());
        paramMap.put("companyId",searchSubInWeb.getCompanyId());
        paramMap.put("subStatus",searchSubInWeb.getSubStatus());
        List<Map> woList = bizSubscribeMapper.querySubList(paramMap);

        List<Map> resList = new ArrayList<>();
        if(woList==null || woList.size()==0){
            return resList;
        }

        Map<String, String> projectIdNameMap = new HashMap<>();
        int size = woList.size();
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = woList.get(i);
            map.put("num", i+1);
            String subId = map.get("id").toString();
            String projectId = map.get("projectId").toString();
            String projectName = projectIdNameMap.get(projectId);
            if (projectName == null) {
                ProjectInfoVo projectInfoVo = bizCrmProjectMapper.selectProjectById(projectId);
                if (projectInfoVo != null) {
                    projectName = projectInfoVo.getProjectName();
                }
                if (projectName == null) {
                    projectName = "";
                }
                projectIdNameMap.put(projectId, projectName);
            }
            map.put("projectName", projectName);
            map.put("appType", "APP");

            String actualCostStr = "";
            BigDecimal actualCost = (BigDecimal) map.get("actualCost");
            if(actualCost != null){
                actualCostStr = (actualCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
            }else{
                actualCostStr = (new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
            }
            map.put("actualCostStr", actualCostStr);
            String refundTime = "", refundCostStr = "";
            String subStatus = map.get("subStatus").toString();
            if (subStatus.equals("6") || subStatus.equals("7")) {//退款中，退款完成
                refundCostStr = actualCostStr;
                if (subStatus.equals("7")) {
                    Map<String, Object> refundMap = bizRefundAuditMapper.selectBySubId(subId);
                    if (refundMap != null) {
                        refundTime = refundMap.get("modifyTime").toString();
                    }
                }
            }
            map.put("refundTime", refundTime);
            map.put("refundCostStr", refundCostStr);

            Integer appraisalVal = bizTransactionLogMapper.selectAppraisalValBySubId(subId);
            String appraisalValStr = appraisalVal == null ? "" : appraisalVal.toString();
            map.put("appraisalValStr", appraisalValStr);

            map.put("payWay", "支付宝");
            String subStatusStr = "";
            if("0".equals(subStatus)){
                subStatusStr = "已下单";
            }else if("1".equals(subStatus)){
                subStatusStr = "处理中";
            }else if("2".equals(subStatus)){
                subStatusStr = "待支付";
            }else if("3".equals(subStatus)){
                subStatusStr = "已取消";
            }else if("4".equals(subStatus)){
                subStatusStr = "已完成";
            }else if("5".equals(subStatus)){
                subStatusStr = "待确认";
            }else if("6".equals(subStatus)){
                subStatusStr = "退款中";
            }else if("7".equals(subStatus)){
                subStatusStr = "退款完成";
            }
            map.put("subStatusStr", subStatusStr);

            List<Map> productList = null;
            if (isReservation) {
                productList = bizSubProductMapper.selectReservationInfoBySubId(subId);
            } else {
                productList = bizSubProductMapper.selectProductInfoBySubId(subId);
            }
            if (productList == null) {
                continue;
            }
            int productListSize = productList.size();
            for (int j = 0; j < productListSize; j++) {
                Map<String, Object> tmpProductMap = productList.get(j);
                Map<String, Object> tmpResMap = new HashMap<>();
                if (j == 0) {
                    tmpResMap.putAll(map);
                } else {
                    tmpResMap.put("num", i+1);
                }
                tmpResMap.putAll(tmpProductMap);
                resList.add(tmpResMap);
            }
        }

        return resList;
    }

    public static InputStream exportExcelForSub(String[] titles, String [] keys, List<Map<String, Object>> dataList, String fileName, Integer mergeCol){
        ObjectRestResponse msg = new ObjectRestResponse();
        DataInputStream in = null;
        ByteArrayOutputStream os = null;
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        int listSize = 0;
        try {
//			XSSFWorkbook workbook = new XSSFWorkbook();
            SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
            sheet = workbook.createSheet();
            workbook.setSheetName(0,fileName);
            // 第一行标题
            row = sheet.createRow(0);
            for(int i = 0;i < titles.length;i++){
                cell = row.createCell(i);
                cell.setCellValue(titles[i]);
            }
            listSize = dataList.size();
            Map<String, Object> dataMap = null;
            Object object = null;
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localSimpleDateFormat.setLenient(false);
            for(int j = 0;j < listSize;j++){
                row = sheet.createRow(j+1);
                dataMap = dataList.get(j);
                for(int k = 0;k < titles.length;k++){
                    cell = row.createCell(k);
                    object = dataMap.get(keys[k]);
                    if(object==null) {
                        cell.setCellValue("");
                    }else {
                        cell.setCellValue(object.toString());
                    }
                }
                if (j > 0) {
                    int curNum = Integer.parseInt(dataMap.get("num").toString());
                    int lastNum = Integer.parseInt(dataList.get(j-1).get("num").toString());
                    if (lastNum != curNum) {
                        int endIndex = j-1;
                        int firstNum = lastNum;
                        int startIndex = 0;
                        for (int k = j-2; k >= 0; k--) {
                            firstNum = Integer.parseInt(dataList.get(k).get("num").toString());
                            if (firstNum != lastNum) {
                                startIndex = k+1;
                                break;
                            }
                        }
                        if (startIndex < endIndex) {
                            for (int k = 0; k < mergeCol; k++) {
                                sheet.addMergedRegion(new CellRangeAddress(startIndex+1, endIndex+1, k, k));
                            }
                        }
                    }
                }
            }

            os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] workbookBytes = os.toByteArray();
            // 把文件已流文件的方式 推入到url中
            in  = new DataInputStream(new ByteArrayInputStream(workbookBytes));
//			logger.info("开始上传文件到OSS:"+localSimpleDateFormat.format(new Date()));
//			msg = ImportUtil.uploadOssFile(in, excelUrl, fileName, ossClient, bucket);
//			logger.info("结束上传文件到OSS:"+localSimpleDateFormat.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in;
    }
}