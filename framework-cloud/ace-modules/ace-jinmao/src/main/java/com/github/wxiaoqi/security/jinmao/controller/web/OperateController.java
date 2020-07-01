package com.github.wxiaoqi.security.jinmao.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizServiceHotlineBiz;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizWoMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.wo.ExcelParams;
import com.github.wxiaoqi.security.jinmao.vo.wo.date.ResultDateData;
import com.github.wxiaoqi.security.jinmao.vo.wo.date.ResultWoOnDate;
import com.github.wxiaoqi.security.jinmao.vo.wo.date.WoOnDateCount;
import com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype.DataSet;
import com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype.IncidentType;
import com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype.IncidentTypeCount;
import com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype.ResultIncidentType;
import com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify.*;
import com.github.wxiaoqi.security.jinmao.vo.wo.woaging.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("web/operateAnalyze")
@CheckClientToken
@CheckUserToken
@Api(tags="运营分析")
public class OperateController {

    @Autowired
    private BizWoMapper bizWoMapper;
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private BizServiceHotlineBiz bizServiceHotlineBiz;

    @RequestMapping(value = "/getIncidentTypeCount" ,method = RequestMethod.GET)
    @ApiOperation(value = "获取工单统计(运营人员)--运营分析", notes = "获取工单统计(运营人员)--运营分析",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cityId",value="城市ID",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="起止时间",dataType="String",paramType = "query",example="yyyy-MM-dd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="yyyy-MM-dd")
    })
    public ObjectRestResponse<ResultIncidentType> getIncidentTypeCount(String projectId,String cityId, String startTime,String endTime,Integer page,Integer limit) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(endTime)) {
            endTime = sdf.format(Calendar.getInstance().getTime());
        }
        if (compareTime(startTime, endTime,"1").getStatus() == 101) {
            return compareTime(startTime, endTime,"1");
        }
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
        List<IncidentTypeCount> oldList = bizWoMapper.getIncidentTypeCount(projectId, cityId, startTime, endTime,startIndex,limit);
        int total = bizWoMapper.getIncidentTypeTotal(projectId, cityId, startTime, endTime);
        List<IncidentType> newList = new ArrayList<>();
        //组装表格信息
        Map<String,List<IncidentTypeCount>> map = oldList.stream().collect(Collectors.groupingBy(IncidentTypeCount::getCityName));
        map.keySet().forEach(key -> {
                    IncidentType incidentType = new IncidentType();
                    incidentType.setCity(key);
                    incidentType.setWoInfo(map.get(key));
                    newList.add(incidentType);
                }
        );
        //组装柱形图数据
        DataSet dataSet = new DataSet();
        String[] type = Stream.of("project", "cmplain", "repair", "plan").toArray(String[]::new);
        dataSet.setType(type);
        List<Map<String, String>> source = new ArrayList<>();
        Collections.sort(oldList,Comparator.comparing(IncidentTypeCount::getProjectName).reversed());
        for (IncidentTypeCount incidentTypeCount : oldList) {
            Map<String, String> dataMap = new LinkedHashMap<>();
            dataMap.put("project",incidentTypeCount.getProjectName());
            dataMap.put("cmplain",incidentTypeCount.getCmplainCount());
            dataMap.put("repair",incidentTypeCount.getRepairCount());
            dataMap.put("plan",incidentTypeCount.getPlanCount());
            source.add(dataMap);
        }
        dataSet.setSource(source);
        ResultIncidentType resultIncidentType = new ResultIncidentType();
        resultIncidentType.setInfo(newList);
        resultIncidentType.setDataSet(dataSet);
        resultIncidentType.setTotal(total);

        response.setData(resultIncidentType);
        return response;
    }

    private ObjectRestResponse compareTime(String startTime, String endTime,String type) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (StringUtils.isEmpty(startTime)) {
                response.setStatus(101);
                response.setMessage("请先选择开始时间");
                return response;
            }
//            if (StringUtils.isNotEmpty(startTime) && StringUtils.isEmpty(endTime)) {
//                int j = DateUtils.daysBetween(sdf.parse(startTime), Calendar.getInstance().getTime());
//                if (j != 0) {
//                    response.setStatus(101);
//                    response.setMessage("请先选择结束时间");
//                    return response;
//                }
//            }
            boolean count = DateTimeUtil.compare_date(sdf.parse(startTime), sdf.parse(endTime));
            if (count) {
                response.setStatus(101);
                response.setMessage("查询日期结束时间不能小于开始时间");
                return response;
            }
            int i = DateUtils.daysBetween(sdf.parse(startTime), sdf.parse(endTime));
            if (i > 60) {
                response.setStatus(101);
                if (type.equals("1")) {
                    response.setMessage("最多查询60天的数据");
                }else {
                    response.setMessage("最多导出60天的数据");
                }
                return response;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/getClassifyName", method = RequestMethod.GET)
    @ApiOperation(value = "获取工单类型分类名称--运营分析", notes = "获取工单类型分类名称--运营分析", httpMethod = "GET")
    @ApiImplicitParam(name = "incidentType", value = "工单类型(报修-repair 投诉-cmplain 计划-plan)", dataType = "String", paramType = "query", example = "4")
    public ObjectRestResponse getClassifyName(String incidentType) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(incidentType)) {
            response.setStatus(101);
            response.setMessage("请先选择工单类型");
            return response;
        }
        List<String> classifyName = bizWoMapper.getClassifyName(incidentType);
        if (classifyName == null || classifyName.size() == 0) {
            classifyName = new ArrayList<>();
        }
        response.setData(classifyName);
        return response;
    }
    @RequestMapping(value = "/getClassifyTypeCount" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取工单类型分类统计--运营分析", notes = "获取工单类型分类统计--运营分析",httpMethod = "POST")
    public ObjectRestResponse<ResultClassifyType> getClassifyTypeCount(@RequestBody @ApiParam SearchParams searchParams) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(searchParams.getEndTime())) {
            searchParams.setEndTime(sdf.format(Calendar.getInstance().getTime()));
        }
        ObjectRestResponse result = compareTime(searchParams.getStartTime(), searchParams.getEndTime(),"1");
        if (result.getStatus() == 101) {
            return result;
        }
        if (StringUtils.isEmpty(searchParams.getIncidentType()) && StringUtils.isNotEmpty(searchParams.getClassifyType())) {
            response.setStatus(101);
            response.setMessage("工单类型不能为空");
            return response;
        }
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                searchParams.setProjectId( bizServiceHotlineBiz.getProjectList().get(0).getId());
            }
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("cityId", searchParams.getCityId());
        paramMap.put("projectId", searchParams.getProjectId());
        if (searchParams.getIncidentType().equals("repair")){
            paramMap.put("incidentType", "报修");
        }else if(searchParams.getIncidentType().equals("cmplain")){
            paramMap.put("incidentType", "投诉");
        }else if (searchParams.getIncidentType().equals("plan")){
            paramMap.put("incidentType", "计划");
        }
        paramMap.put("classifyType", searchParams.getClassifyType());
        paramMap.put("startTime", searchParams.getStartTime());
        paramMap.put("endTime", searchParams.getEndTime());
        List<ClassifyTypeCount> oldList = bizWoMapper.getClassifyTypeCount(paramMap);
        if (oldList == null || oldList.size() == 0) {
            response.setStatus(101);
            response.setMessage("未查询到数据");
            return response;
        }
        List<BusType> newList = new ArrayList<>();
        //组装表格信息
        Map<String,List<ClassifyTypeCount>> map = oldList.stream().collect(Collectors.groupingBy(ClassifyTypeCount::getType));
        map.keySet().forEach(key -> {
            BusType busType = new BusType();
            busType.setBusName(key);
            busType.setClassifyInfo(map.get(key));
            newList.add(busType);
        }
        );

        //组装饼图数据
        ResultData busData = new ResultData();
        ResultData repairData = new ResultData();
        ResultData cmplainData = new ResultData();
        ResultData planData = new ResultData();
        List<Map<String, String>> busSource = new ArrayList<>();
        //业务数量占比饼图
        for (BusType busType : newList) {
            int sum = 0;
            for (ClassifyTypeCount classifyTypeCount : busType.getClassifyInfo()) {
                sum +=Integer.parseInt(classifyTypeCount.getSum());
            }
            Map<String, String> dataMap = new LinkedHashMap<>();
            dataMap.put("name", busType.getBusName());
            dataMap.put("value",String.valueOf(sum));
            busSource.add(dataMap);
        }
        busData.setSource(busSource);
        //报修分类数量占比饼
        List<Map<String, String>> repairSource = new ArrayList<>();
        for (BusType repairType : newList) {
            if (repairType.getBusName().equals("报修")) {
                for (ClassifyTypeCount repair : repairType.getClassifyInfo()) {
                    Map<String, String> dataMap = new LinkedHashMap<>();
                    dataMap.put("name", repair.getName());
                    dataMap.put("value",repair.getSum());
                    repairSource.add(dataMap);
                }
            }
        }
        repairData.setSource(repairSource);
        //投诉分类数量占比饼图
        List<Map<String, String>> cmplainSource = new ArrayList<>();
        for (BusType cmplainType : newList) {
            if (cmplainType.getBusName().equals("投诉")) {
                for (ClassifyTypeCount cmplain : cmplainType.getClassifyInfo()) {
                    Map<String, String> dataMap = new LinkedHashMap<>();
                    dataMap.put("name", cmplain.getName());
                    dataMap.put("value",cmplain.getSum());
                    cmplainSource.add(dataMap);
                }
            }
        }
        cmplainData.setSource(cmplainSource);
       //计划分类数量占比饼图
       List<Map<String, String>> planSource = new ArrayList<>();
        for (BusType planType : newList) {
            if (planType.getBusName().equals("计划")) {
                for (ClassifyTypeCount plan : planType.getClassifyInfo()) {
                    Map<String, String> dataMap = new LinkedHashMap<>();
                    dataMap.put("name", plan.getName());
                    dataMap.put("value",plan.getSum());
                    planSource.add(dataMap);
                }
            }
        }
        planData.setSource(planSource);
        //返回数据
        ResultClassifyType resultClassifyType = new ResultClassifyType();
        resultClassifyType.setInfo(newList);
        resultClassifyType.setBusData(busData);
        resultClassifyType.setRepairData(repairData);
        resultClassifyType.setCmplainData(cmplainData);
        resultClassifyType.setPlanData(planData);
        response.setData(resultClassifyType);
        return response;
    }

    @RequestMapping(value = "/getWoTypeCountByProperty" ,method = RequestMethod.GET)
    @ApiOperation(value = "获取工单统计(物业人员)--运营分析", notes = "获取工单统计(物业人员)--运营分析",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true,paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="起止时间",dataType="String",paramType = "query",example="yyyy-MM-dd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="yyyy-MM-dd")
    })
    public ObjectRestResponse<ResultWoOnDate> getWoTypeCountByProperty(String projectId, String startTime, String endTime,Integer page,Integer limit) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(endTime)) {
           endTime = sdf.format(Calendar.getInstance().getTime());
        }
        if (compareTime(startTime, endTime,"1").getStatus() == 101) {
            return compareTime(startTime, endTime,"1");
        }
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                projectId = bizServiceHotlineBiz.getProjectList().get(0).getId();
            }
        }
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
        List<WoOnDateCount> woCount = bizWoMapper.getWoTypeCountByProperty(projectId, startTime, endTime,null,null);
        int total = bizWoMapper.getWoTypeByPropertyTotal(projectId, startTime, endTime);
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("startTime", startTime);
        dateMap.put("endTime", endTime);
        List<WoCountByProject> newList = new ArrayList();
        Map<String,List<WoOnDateCount>> map = woCount.stream().collect(Collectors.groupingBy(WoOnDateCount::getProjectName));
        map.keySet().forEach(key -> {
                    WoCountByProject woCountByProject = new WoCountByProject();
                    woCountByProject.setProjectName(key);
                    woCountByProject.setWoOnDateCount(map.get(key));
                    newList.add(woCountByProject);
                }
        );
        List<WoOnDateCount> resultList = new ArrayList<>();
        for (WoCountByProject countByProject : newList) {
            List<WoOnDateCount> data = getDataCompletion(countByProject.getWoOnDateCount(), dateMap);
            for (WoOnDateCount datum : data) {
                resultList.add(datum);
            }
        }

        Collections.sort(resultList,Comparator.comparing(WoOnDateCount::getDate).reversed());
        ResultDateData resultDateData = new ResultDateData();
        if (woCount != null && woCount.size() > 0) {
            List<String> dateList = new ArrayList<>();
            dateList.add("date");
            List<String> repairList = new ArrayList<>();
            repairList.add("报修");
            List<String> cmplainList = new ArrayList<>();
            cmplainList.add("投诉");
            List<String> planList = new ArrayList<>();
            planList.add("计划");
            for (WoOnDateCount woOnDateCount : resultList) {
                dateList.add(woOnDateCount.getDate());
                repairList.add(woOnDateCount.getRepairCount());
                cmplainList.add(woOnDateCount.getCmplainCount());
                planList.add(woOnDateCount.getPlanCount());
            }
            List list = new ArrayList();
            list.add(dateList);
            list.add(repairList);
            list.add(cmplainList);
            list.add(planList);
            resultDateData.setSource(list);
        }else {
            resultDateData.setSource(new ArrayList<>());
            woCount = new ArrayList<>();
        }
        ResultWoOnDate resultWoOnDate = new ResultWoOnDate();
        resultWoOnDate.setDataSet(resultDateData);
        resultWoOnDate.setInfo(resultList);
        resultWoOnDate.setTotal(resultList.size());
        response.setData(resultWoOnDate);
        return response;
    }


    @RequestMapping(value = "/getWoAgingCount" ,method = RequestMethod.GET)
    @ApiOperation(value = "获取工单时效统计--运营分析", notes = "获取工单时效统计--运营分析",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cityId",value="城市ID",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="startTime",value="起止时间",dataType="String",paramType = "query",example="yyyy-MM-dd"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="yyyy-MM-dd")
    })
    public ObjectRestResponse<ResultWoAging> getWoAgingCount(String projectId, String cityId, String startTime, String endTime,String incidentType) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(endTime)) {
            endTime = sdf.format(Calendar.getInstance().getTime());
        }
        if (compareTime(startTime, endTime,"1").getStatus() == 101) {
            return compareTime(startTime, endTime,"1");
        }
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                projectId = bizServiceHotlineBiz.getProjectList().get(0).getId();
            }
        }
        List<WoAgingCount> oldList = bizWoMapper.getWoAgingCount(projectId, cityId, startTime, endTime,incidentType);
        List<AgingType> newList = new ArrayList<>();
        //组装表格信息
        Map<String,List<WoAgingCount>> map = oldList.stream().collect(Collectors.groupingBy(WoAgingCount::getProjectName));
        map.keySet().forEach(key -> {
                    AgingType agingType = new AgingType();
                    agingType.setProjectName(key);
                    agingType.setWoAgingInfo(map.get(key));
                    newList.add(agingType);
                }
        );
        Collections.sort(newList,Comparator.comparing(AgingType::getProjectName).reversed());
        //组装饼图数据
        ResultAgingData createToReceiveData = new ResultAgingData();
        ResultAgingData receiveToFinishData = new ResultAgingData();
        ResultAgingData createToFinishData = new ResultAgingData();
        //下单到接单占比饼图
        List<WoAgingData> createToReceiveSource = getWoAgingData(oldList,"下单到接单");
        createToReceiveData.setSource(createToReceiveSource);
        //接单到完成占比饼图
        List<WoAgingData> receiveToFinishSource = getWoAgingData(oldList,"接单到完成");
        receiveToFinishData.setSource(receiveToFinishSource);
        //下单到完成占比饼图
        List<WoAgingData> createToFinishSource = getWoAgingData(oldList,"下单到完成");
        createToFinishData.setSource(createToFinishSource);
        ResultWoAging woAging = new ResultWoAging();
        woAging.setInfo(newList);
        woAging.setCreateToReceiveData(createToReceiveData);
        woAging.setReceiveToFinishData(receiveToFinishData);
        woAging.setCreateToFinishData(createToFinishData);
        response.setData(woAging);
        return response;
    }
    //组装返回饼图公共方法
    private List<WoAgingData> getWoAgingData(List<WoAgingCount> oldList,String type) {
        List<WoAgingData> source = new ArrayList<>();
        int count5 = 0;
        int count515 = 0;
        int count1530 = 0;
        int count3060 = 0;
        int count12 = 0;
        int count2 = 0;
        for (WoAgingCount woAgingCount : oldList) {
            if (woAgingCount.getType().equals(type)) {
                count5 +=Integer.parseInt(woAgingCount.getLessThanFive());
                count515 += Integer.parseInt(woAgingCount.getFiveToFifteen());
                count1530 += Integer.parseInt(woAgingCount.getFifToThirty());
                count3060 += Integer.parseInt(woAgingCount.getThdToSixty());
                count12 += Integer.parseInt(woAgingCount.getOneToTwo());
                count2 += Integer.parseInt(woAgingCount.getMoreThanTwo());
            }
        }
        String[] woAgingType = {"5分钟之内","5到15分钟","15分钟到30分钟","30分钟到60分钟","1小时到2小时","超过2小时"};
        int[] woAgingValue = {count5,count515,count1530,count3060,count12,count2};
        for (int i = 0; i < woAgingType.length; i++) {
            WoAgingData woAgingData = new WoAgingData();
            woAgingData.setName(woAgingType[i]);
            woAgingData.setValue(String.valueOf(woAgingValue[i]));
            source.add(woAgingData);
        }
        return source;
    }
    //补全日期并设置为0的方法
    public static List<WoOnDateCount> getDataCompletion (List<WoOnDateCount> beforeList, Map<String, String> paraMap) {

        List<WoOnDateCount> dateResult = new ArrayList<>();
        Date dateBegin, dateEnd;
        int days= 0;
        Calendar calendar10 = Calendar.getInstance();
        Calendar calendar5 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String projectId = "";
        String projectName = "";
        for (WoOnDateCount woOnDateCount : beforeList) {
            projectId = woOnDateCount.getProjectId();
            projectName = woOnDateCount.getProjectName();
        }
        try {
            dateBegin = sdf.parse(paraMap.get("startTime"));
            dateEnd = sdf.parse(paraMap.get("endTime"));
            /*
             * 计算开始时间和结束时间之间有几天
             * 如果想显示 01, 02, 03 三天的数据 结束日期需要传04, 因为01日 00:00 -- 04日 00:00 并不包括04
             */
            days = (int) ((dateEnd.getTime() - dateBegin.getTime()) / (1000*3600*24)) + 1;
            calendar10.setTime(dateBegin);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        //循环处理日期数据，把缺失的日期补全。days是时间段内的天数, beforList.size()是要处理的日期集合的天数
        for (int curr = 0; curr < days; curr++) {
            boolean dateExist = false;
            int index = 0;

            for(int i  = 0 ; i < beforeList.size() ; i++){

                try {
                    WoOnDateCount testaa = beforeList.get(i);
                    Date date2 = sdf.parse(testaa.getDate());
                    calendar5.setTime(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(calendar10.compareTo(calendar5) == 0){
                    dateExist  = true;
                    index = i;
                    break;
                }
            }
            if(dateExist){
                WoOnDateCount afterDate = beforeList.get(index);
                dateResult.add(afterDate);
            }else{
                WoOnDateCount complateData= new WoOnDateCount();

                complateData.setProjectId(projectId);
                complateData.setProjectName(projectName);
                complateData.setDate(sdf.format(calendar10.getTime()));
                complateData.setCmplainCount("0");
                complateData.setRepairCount("0");
                complateData.setPlanCount("0");
                complateData.setTotal("0");
                dateResult.add(complateData);
            }
            //修改外层循环变量, 是calendar10 +1天, 一天后的日期
            calendar10.add(Calendar.DAY_OF_MONTH, 1 );
        }


        return dateResult;
    }

    @RequestMapping(value = "/getWoAgingExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出工单时效Excel", notes = "导出工单时效Excel",httpMethod = "POST")
    public ObjectRestResponse getWoAgingExcel(@RequestBody @ApiParam ExcelParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(params.getEndTime())) {
            params.setEndTime(sdf.format(Calendar.getInstance().getTime()));
        }
        if (compareTime(params.getStartTime(), params.getEndTime(),"").getStatus() == 101) {
            return compareTime(params.getStartTime(), params.getEndTime(),"");
        }
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                params.setProjectId(bizServiceHotlineBiz.getProjectList().get(0).getId());
            }
        }
        List<WoAgingCount> oldList = bizWoMapper.getWoAgingCount(params.getProjectId(), params.getCityId(), params.getStartTime(), params.getEndTime(),params.getIncidentType());
        if (oldList.size() == 0 || oldList == null) {
            response.setStatus(102);
            response.setMessage("没有数据，导出失败！");
            return response;
        }
        Collections.sort(oldList,Comparator.comparing(WoAgingCount::getProjectName).reversed());
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (WoAgingCount woAgingCount : oldList) {
            if (woAgingCount.getType().equals("createToReceive")) {
                woAgingCount.setType("下单到接单");
            } else if (woAgingCount.getType().equals("receiveToFinish")) {
                woAgingCount.setType("接单到完成");
            } else if(woAgingCount.getType().equals("createToFinish")) {
                woAgingCount.setType("下单到完成");
            }
        }
        if (oldList != null && oldList.size() > 0) {
            for (int i = 0; i < oldList.size(); i++) {
                WoAgingCount temp = mapper.convertValue(oldList.get(i), WoAgingCount.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"小区","类型","5分钟内","5到15分钟","15到30分钟","30到60分钟","1到2小时","超过2小时"};
        String[] keys = {"projectName","type","lessThanFive","fiveToFifteen","fifToThirty","thdToSixty","oneToTwo","moreThanTwo"};
        String fileName = "工单时效统计.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        response = ossExcelFeign.uploadExcel(excelInfoVo);

        return response;
    }

    @RequestMapping(value = "/getWoTypeExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出工单分类Excel", notes = "导出工单时效Excel",httpMethod = "POST")
    public ObjectRestResponse getWoTypeExcel(@RequestBody @ApiParam SearchParams searchParams) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(searchParams.getEndTime())) {
            searchParams.setEndTime(sdf.format(Calendar.getInstance().getTime()));
        }
        if (compareTime(searchParams.getStartTime(), searchParams.getEndTime(),"").getStatus() == 101) {
            return compareTime(searchParams.getStartTime(), searchParams.getEndTime(),"");
        }
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                searchParams.setProjectId(bizServiceHotlineBiz.getProjectList().get(0).getId());
            }
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("cityId", searchParams.getCityId());
        paramMap.put("projectId", searchParams.getProjectId());
        paramMap.put("incidentType", searchParams.getIncidentType());
        paramMap.put("classifyType", searchParams.getClassifyType());
        paramMap.put("startTime", searchParams.getStartTime());
        paramMap.put("endTime", searchParams.getEndTime());
        if (searchParams.getIncidentType().equals("repair")){
            paramMap.put("incidentType", "报修");
        }else if(searchParams.getIncidentType().equals("cmplain")){
            paramMap.put("incidentType", "投诉");
        }else if (searchParams.getIncidentType().equals("plan")){
            paramMap.put("incidentType", "计划");
        }
        List<ClassifyTypeCount> oldList = bizWoMapper.getClassifyTypeCount(paramMap);
        if (oldList.size() == 0 || oldList == null) {
            response.setStatus(102);
            response.setMessage("没有数据，导出失败！");
            return response;
        }
        Collections.sort(oldList,Comparator.comparing(ClassifyTypeCount::getType).reversed());
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (oldList != null && oldList.size() > 0) {
            for (int i = 0; i < oldList.size(); i++) {
                ClassifyTypeCount temp = mapper.convertValue(oldList.get(i), ClassifyTypeCount.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"业务","分类名称","订单个数"};
        String[] keys = {"type","name","sum"};
        String fileName = "工单分类统计.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        response = ossExcelFeign.uploadExcel(excelInfoVo);

        return response;
    }


//    public static void main(String[] args) throws Exception {
//        File inputFile = new File("D:\\工单.xlsx");
//        InputStream is = new FileInputStream(inputFile);
//        Workbook wb = new XSSFWorkbook(is);
//        Sheet sheet = wb.getSheetAt(0);
//
//
//
//        for (int i = 0; i < (sheet.getLastRowNum() / 4); i++) {
//            sheet.addMergedRegion(new CellRangeAddress(1+(i*4), 4+ (i*4), 0, 0));
//        }
//
//        is.close();
//        System.out.println(sheet.getLastRowNum());
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream("D:\\工单合并.xlsx");
//            wb.write(out);
//        } catch (IOException e) {
//            System.out.println(e.toString());
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                System.out.println(e.toString());
//            }
//        }
//
//    }


    @RequestMapping(value = "/getWoCountExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出工单运营统计Excel", notes = "导出工单统计Excel",httpMethod = "POST")
    public ObjectRestResponse getWoCountExcel(@RequestBody @ApiParam ExcelParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(params.getEndTime())) {
            params.setEndTime(sdf.format(Calendar.getInstance().getTime()));
        }
        if (compareTime(params.getStartTime(), params.getEndTime(),"").getStatus() == 101) {
            return compareTime(params.getStartTime(), params.getEndTime(),"");
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String[] titles;
        String[] keys;
        String fileName = "运营工单统计.xlsx";
        List<IncidentTypeCount> typeCount = bizWoMapper.getIncidentTypeCount(params.getProjectId(),params.getCityId(), params.getStartTime(), params.getEndTime(),null,null);
        if (typeCount.size() == 0 || typeCount == null) {
            response.setStatus(102);
            response.setMessage("没有数据，导出失败！");
            return response;
        }
        Collections.sort(typeCount,Comparator.comparing(IncidentTypeCount::getProjectName).reversed());
        if (typeCount != null && typeCount.size() > 0) {
            for (int i = 0; i < typeCount.size(); i++) {
                IncidentTypeCount temp = mapper.convertValue(typeCount.get(i), IncidentTypeCount.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        titles = new String[]{"城市","小区","报修工单数","投诉工单数","计划工单数","总数"};
        keys = new String[]{"cityName","projectName","repairCount","cmplainCount","planCount","total"};




        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        response = ossExcelFeign.uploadExcel(excelInfoVo);
        return response;
    }

    @RequestMapping(value = "/getWoCountPropertyExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出工单物业统计Excel", notes = "导出工单物业统计Excel",httpMethod = "POST")
    public ObjectRestResponse getWoCountPropertyExcel(@RequestBody @ApiParam ExcelParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(params.getEndTime())) {
            params.setEndTime(sdf.format(Calendar.getInstance().getTime()));
        }
        if (compareTime(params.getStartTime(), params.getEndTime(),"").getStatus() == 101) {
            return compareTime(params.getStartTime(), params.getEndTime(),"");
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String[] titles;
        String[] keys;
        String fileName = "物业工单统计.xlsx";
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                params.setProjectId(bizServiceHotlineBiz.getProjectList().get(0).getId());
            }
        }
        List<WoOnDateCount> woCount = bizWoMapper.getWoTypeCountByProperty(params.getProjectId(), params.getStartTime(), params.getEndTime(),null,null);
        if (woCount.size() == 0 || woCount == null) {
            response.setStatus(102);
            response.setMessage("没有数据，导出失败！");
            return response;
        }
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("startTime", params.getStartTime());
        dateMap.put("endTime", params.getEndTime());
        List<WoCountByProject> newList = new ArrayList();
        Map<String,List<WoOnDateCount>> map = woCount.stream().collect(Collectors.groupingBy(WoOnDateCount::getProjectName));
        map.keySet().forEach(key -> {
                    WoCountByProject woCountByProject = new WoCountByProject();
                    woCountByProject.setProjectName(key);
                    woCountByProject.setWoOnDateCount(map.get(key));
                    newList.add(woCountByProject);
                }
        );
        List<WoOnDateCount> resultList = new ArrayList<>();
        for (WoCountByProject countByProject : newList) {
            List<WoOnDateCount> data = getDataCompletion(countByProject.getWoOnDateCount(), dateMap);
            for (WoOnDateCount datum : data) {
                resultList.add(datum);
            }
        }

        Collections.sort(resultList,Comparator.comparing(WoOnDateCount::getDate).reversed());
        if (resultList != null && resultList.size() > 0) {
            for (int i = 0; i < resultList.size(); i++) {
                WoOnDateCount temp = mapper.convertValue(resultList.get(i), WoOnDateCount.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        titles = new String[]{"小区","日期","报修工单数","投诉工单数","计划工单数","总数"};
        keys = new String[]{"projectName","date","repairCount","cmplainCount","planCount","total"};


        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        response = ossExcelFeign.uploadExcel(excelInfoVo);
        return response;
    }

    @RequestMapping(value = "/getWoAgingList" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出工单物业统计Excel", notes = "导出工单物业统计Excel",httpMethod = "POST")
    public TableResultResponse<WoAgingList> getWoAgingList(@RequestBody @ApiParam WoAgingDetail woAgingDetail) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(woAgingDetail.getEndTime())) {
            woAgingDetail.setEndTime(sdf.format(Calendar.getInstance().getTime()));
        }
        if (compareTime(woAgingDetail.getStartTime(), woAgingDetail.getEndTime(),"").getStatus() == 101) {
            ObjectRestResponse response = compareTime(woAgingDetail.getStartTime(), woAgingDetail.getEndTime(), "");
            TableResultResponse tableResultResponse = new TableResultResponse();
            tableResultResponse.setMessage(response.getMessage());
            tableResultResponse.setStatus(101);
            return tableResultResponse;
        }
        int page = woAgingDetail.getPage();
        int limit = woAgingDetail.getLimit();
        if (page < 0 ) {
            page = 1;
        }
        if (limit < 0) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        Map<String,Object> paramMap = new HashMap<>();
        if (page != 0 && limit != 0) {
            paramMap.put("page",startIndex);
            paramMap.put("limit",limit);
        }
        if (woAgingDetail.getAgingType().equals("下单到接单")) {
            paramMap.put("agingType", "createToReceive");
        } else if (woAgingDetail.getAgingType().equals("下单到完成")) {
            paramMap.put("agingType", "createToFinish");
        }else {
            paramMap.put("agingType", "receiveToFinish");
        }
        paramMap.put("projectId", woAgingDetail.getProjectId());
        paramMap.put("type", woAgingDetail.getType());
        paramMap.put("startTime", woAgingDetail.getStartTime());
        paramMap.put("endTime", woAgingDetail.getEndTime());
        paramMap.put("minute", woAgingDetail.getMinute());

        List<WoAgingList> woAgingList = bizWoMapper.getWoAgingListByMinute(paramMap);
        int total = bizWoMapper.getWoAgingListCount(paramMap);
        if (woAgingList.size() == 0) {
            woAgingList = new ArrayList<>();
        }
        return new TableResultResponse<WoAgingList>(total,woAgingList);
    }

    @RequestMapping(value = "/getWoAgingDetailExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出工单时效详情Excel", notes = "导出工单时效详情Excel",httpMethod = "POST")
    public ObjectRestResponse getWoAgingDetailExcel(@RequestBody @ApiParam WoAgingDetail woAgingDetail) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(woAgingDetail.getEndTime())) {
            woAgingDetail.setEndTime(sdf.format(Calendar.getInstance().getTime()));
        }
        if (compareTime(woAgingDetail.getStartTime(), woAgingDetail.getEndTime(),"").getStatus() == 101) {
            return compareTime(woAgingDetail.getStartTime(), woAgingDetail.getEndTime(), "");
        }
        Map<String,Object> paramMap = new HashMap<>();
        if (woAgingDetail.getAgingType().equals("下单到接单")) {
            paramMap.put("agingType", "createToReceive");
        } else if (woAgingDetail.getAgingType().equals("下单到完成")) {
            paramMap.put("agingType", "createToFinish");
        }else {
            paramMap.put("agingType", "receiveToFinish");
        }
        paramMap.put("projectId", woAgingDetail.getProjectId());
        paramMap.put("type", woAgingDetail.getType());
        paramMap.put("startTime", woAgingDetail.getStartTime());
        paramMap.put("endTime", woAgingDetail.getEndTime());
        paramMap.put("minute", woAgingDetail.getMinute());

        List<WoAgingList> woAgingList = bizWoMapper.getWoAgingListByMinute(paramMap);
        if (woAgingList.size() == 0 || woAgingList == null) {
            response.setStatus(102);
            response.setMessage("没有数据，导出失败！");
            return response;
        }
        ObjectMapper mapper = new ObjectMapper();
        String[] titles;
        String[] keys;
        String fileName = "工单时效具体统计.xlsx";
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (woAgingList != null && woAgingList.size() > 0) {
            for (int i = 0; i < woAgingList.size(); i++) {
                WoAgingList temp = mapper.convertValue(woAgingList.get(i), WoAgingList.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }

        titles = new String[]{"工单编码","工单类型","标题","处理人","下单时间","接单时间","完成时间","状态"};
        keys = new String[]{"woCode","incidentType","title","handleBy","createTime","receiveTime","finishTime","woStatusStr"};


        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        response = ossExcelFeign.uploadExcel(excelInfoVo);
        return response;
    }
}
