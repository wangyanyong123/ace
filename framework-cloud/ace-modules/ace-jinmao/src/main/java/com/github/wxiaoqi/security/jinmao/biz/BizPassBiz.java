package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.jinmao.entity.BizPass;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizPassMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.passlog.QrPassListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 * @Date 2019-04-02 15:27:58
 */
@Service
public class BizPassBiz extends BusinessBiz<BizPassMapper,BizPass> {

    @Autowired
    private BizPassMapper bizPassMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;

    public List<QrPassListVo> getQrPassList(String projectId,String startDate,String endDate,String searchVal,Integer page,Integer limit) {
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

        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
            }
        }
        List<QrPassListVo> qrPassLogList = bizPassMapper.getQrPassLogList(projectId,startDate,endDate,searchVal, startIndex, limit);
        if (qrPassLogList == null || qrPassLogList.size() == 0) {
            qrPassLogList = new ArrayList<>();
        }
        Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
        for (QrPassListVo qrPassListVo : qrPassLogList) {
            Matcher matcher = pattern.matcher(qrPassListVo.getPassDesc());
            String passDesc = matcher.replaceAll("");
            qrPassListVo.setPassDesc(passDesc);
        }
        return qrPassLogList;
    }

    public int getQrPassCount(String projectId,String startDate,String endDate,String searchVal) {
        return bizPassMapper.getQrPassCount(projectId,startDate,endDate,searchVal);
    }

    public ObjectRestResponse doPassLogExcel(String projectId,String startDate, String endDate, String searchVal) {
        ObjectRestResponse response = new ObjectRestResponse();
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
            }
        }
        List<QrPassListVo> data = bizPassMapper.getQrPassLogList(projectId,startDate, endDate, searchVal, null, null);
        if (data == null || data.size() == 0) {
            response.setStatus(102);
            response.setMessage("没有数据，导出失败！");
            return response;
        }
        Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
        for (QrPassListVo qrPassListVo : data) {
            Matcher matcher = pattern.matcher(qrPassListVo.getPassDesc());
            String passDesc = matcher.replaceAll("");
            qrPassListVo.setPassDesc(passDesc);
        }

        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                QrPassListVo temp = mapper.convertValue(data.get(i), QrPassListVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"所属项目","用户名称","用户电话","道闸地址","通行状态","通行时间"};
        String[] keys = {"projectName","name","phone","passAddr","passDesc","createTime"};
        String fileName = "二维码通行记录.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        response = ossExcelFeign.uploadExcel(excelInfoVo);
        return response;
    }
}