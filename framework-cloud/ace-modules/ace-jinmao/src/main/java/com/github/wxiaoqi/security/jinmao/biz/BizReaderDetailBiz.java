package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizReaderDetail;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizCommunityTopicMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizFamilyPostsMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizReaderDetailMapper;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.GradeRuleVo;
import com.github.wxiaoqi.security.jinmao.vo.stat.out.StatDataVo;
import com.github.wxiaoqi.security.jinmao.vo.stat.out.StatTopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 阅读详情统计表
 *
 * @author huangxl
 * @Date 2019-08-14 11:25:32
 */
@Service
public class BizReaderDetailBiz extends BusinessBiz<BizReaderDetailMapper,BizReaderDetail> {

    @Autowired
    private BizReaderDetailMapper bizReaderDetailMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private BizCommunityTopicMapper bizCommunityTopicMapper;
    @Autowired
    private BizFamilyPostsMapper bizFamilyPostsMapper;

    /**
     * 获取统计数据列表
     * @param projectId
     * @param type
     * @param dataType
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    public List<StatDataVo> getStatDataList(String projectId,String type,String dataType,String startTime,String endTime
                       ,Integer page, Integer limit){
        ObjectRestResponse msg = new ObjectRestResponse();
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
        List<StatDataVo> statDataVoList = null
;        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        String a4 = "4";
        String t0 = "0";
        String t1 = "1";
        String t2 = "2";
        String t3 = "3";
        String t4 = "4";
        if(startTime == null || StringUtils.isEmpty(startTime) || endTime == null || StringUtils.isEmpty(endTime)){
            endTime = sdf.format(new Date());
            if(t1.equals(dataType)){
                //小时 推7天
                startTime = getPastDate(7,"1",dataType);
            }else if(t2.equals(dataType)){
                //天 推一个月
                startTime = getPastDate(1,"2",dataType);
            }else if(t3.equals(dataType) || t4.equals(dataType)){
                startTime = getPastDate(1,"3",dataType);
            }else{
                startTime = getPastDate(3,"1",dataType);
            }
        }
        if(t0.equals(dataType)){
            //分钟
            statDataVoList = bizReaderDetailMapper.selectStatDataByMinute(projectId, type, dataType, startTime, endTime, startIndex, limit);
        } else if(a1.equals(dataType)){
            //小时
            statDataVoList = bizReaderDetailMapper.selectStatDataByHours(projectId, type, dataType, startTime, endTime, startIndex, limit);
        }else if(a2.equals(dataType)){
            //天
            statDataVoList = bizReaderDetailMapper.selectStatDataByDay(projectId, type, dataType, startTime, endTime, startIndex, limit);
        }else if(a3.equals(dataType)){
            //周
            statDataVoList = bizReaderDetailMapper.selectStatDataByWeek(projectId, type, dataType, startTime, endTime, startIndex, limit);
        }else if(a4.equals(dataType)){
            //月
            statDataVoList = bizReaderDetailMapper.selectStatDataByMouth(projectId, type, dataType, startTime, endTime, startIndex, limit);
        }
        if(statDataVoList == null || statDataVoList.size() == 0){
            statDataVoList = new ArrayList<>();
        }else{
            for(StatDataVo dataVo: statDataVoList){
                if(a1.equals(type)){
                    dataVo.setTopicType("家里人");
                }else if(a2.equals(type)){
                    dataVo.setTopicType("议事厅");
                }else if(a3.equals(type)){
                    dataVo.setTopicType("社区话题");
                }else if(a4.equals(type)){
                    dataVo.setTopicType("业主圈");
                }
            }
        }
        return statDataVoList;
    }


    /**
     * 查询统计数据列表数量
     * @param projectId
     * @param type
     * @param dataType
     * @param startTime
     * @param endTime
     * @return
     */
    public int selectStatDataCount(String projectId,String type,String dataType,String startTime,String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StatDataVo> statDataVoList = null;
        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        String a4 = "4";
        String t0 = "0";
        String t1 = "1";
        String t2 = "2";
        String t3 = "3";
        String t4 = "4";
        if(startTime == null || StringUtils.isEmpty(startTime) || endTime == null || StringUtils.isEmpty(endTime)){
            endTime = sdf.format(new Date());
            if(t1.equals(dataType)){
                //分钟 推7天
                startTime = getPastDate(7,"1",dataType);
            }else if(t2.equals(dataType)){
                //天 推一个月
                startTime = getPastDate(1,"2",dataType);
            }else if(t3.equals(dataType) || t4.equals(dataType)){
                startTime = getPastDate(1,"3",dataType);
            }else{
                startTime = getPastDate(3,"1",dataType);
            }
        }
        if(t0.equals(dataType)){
            //分钟
            statDataVoList = bizReaderDetailMapper.selectStatDataByMinute(projectId, type, dataType, startTime, endTime, null, null);
        } else if(a1.equals(dataType)){
            statDataVoList = bizReaderDetailMapper.selectStatDataByHours(projectId, type, dataType, startTime, endTime, null, null);
        }else if(a2.equals(dataType)){
            //天
            statDataVoList = bizReaderDetailMapper.selectStatDataByDay(projectId, type, dataType, startTime, endTime, null, null);
        }else if(a3.equals(dataType)){
            //周
            statDataVoList = bizReaderDetailMapper.selectStatDataByWeek(projectId, type, dataType, startTime, endTime, null, null);
        }else if(a4.equals(dataType)){
            //月
            statDataVoList = bizReaderDetailMapper.selectStatDataByMouth(projectId, type, dataType, startTime, endTime, null, null);
        }
        if(statDataVoList == null || statDataVoList.size() == 0){
            statDataVoList = new ArrayList<>();
        }
        return statDataVoList.size();
    }

    /**
     * 导出统计数据列表
     * @param projectId
     * @param type
     * @param dataType
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse exportDataListExcel(String projectId,String type,String dataType,String startTime,String endTime){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<Map<String, Object>> dataList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StatDataVo> statDataVoList = null;
        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        String a4 = "4";
        String t0 = "0";
        String t1 = "1";
        String t2 = "2";
        String t3 = "3";
        String t4 = "4";
        if(startTime == null || StringUtils.isEmpty(startTime) || endTime == null || StringUtils.isEmpty(endTime)){
            endTime = sdf.format(new Date());
            if(t1.equals(dataType)){
                //分钟 推7天
                startTime = getPastDate(7,"1",dataType);
            }else if(t2.equals(dataType)){
                //天 推一个月
                startTime = getPastDate(1,"2",dataType);
            }else if(t3.equals(dataType) || t4.equals(dataType)){
                startTime = getPastDate(1,"3",dataType);
            }else{
                startTime = getPastDate(3,"1",dataType);
            }
        }
        if(t0.equals(dataType)){
            //分钟
            statDataVoList = bizReaderDetailMapper.selectStatDataByMinute(projectId, type, dataType, startTime, endTime, null, null);
        } else if(a1.equals(dataType)){
            statDataVoList = bizReaderDetailMapper.selectStatDataByHours(projectId, type, dataType, startTime, endTime, null, null);
        }else if(a2.equals(dataType)){
            //天
            statDataVoList = bizReaderDetailMapper.selectStatDataByDay(projectId, type, dataType, startTime, endTime, null, null);
        }else if(a3.equals(dataType)){
            //周
            statDataVoList = bizReaderDetailMapper.selectStatDataByWeek(projectId, type, dataType, startTime, endTime, null, null);
        }else if(a4.equals(dataType)){
            //月
            statDataVoList = bizReaderDetailMapper.selectStatDataByMouth(projectId, type, dataType, startTime, endTime, null, null);
        }
        if(statDataVoList == null || statDataVoList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("没有数据，导出失败！");
            return msg;
        }else{
            for(StatDataVo dataVo: statDataVoList){
                if(a1.equals(type)){
                    dataVo.setTopicType("家里人");
                }else if(a2.equals(type)){
                    dataVo.setTopicType("议事厅");
                }else if(a3.equals(type)){
                    dataVo.setTopicType("社区话题");
                }else if(a4.equals(type)){
                    dataVo.setTopicType("业主圈");
                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        if (statDataVoList != null && statDataVoList.size() > 0) {
            for (int i = 0; i < statDataVoList.size(); i++) {
                StatDataVo temp = mapper.convertValue(statDataVoList.get(i), StatDataVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"统计类型","数据类型","时间范围","PV数","UV数"};
        String[] keys = {"topicType","dataType","createTime","pv","uv"};
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
     * 查询统计各帖子信息
     * @param projectId
     * @param type
     * @param dataType
     * @param startTime
     * @param endTime
     * @param pSort
     * @param uSort
     * @param page
     * @param limit
     * @return
     */
    public List<StatTopicVo> getStatTopicList(String projectId,String type,String dataType,String startTime,String endTime
            ,String pSort,String uSort, Integer page, Integer limit){
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
        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        List<StatTopicVo> statTopicVoList = bizReaderDetailMapper.selectTopicListByType(type,projectId,dataType,
                startTime,endTime,pSort,uSort,startIndex,limit);;
        if(statTopicVoList == null || statTopicVoList.size() == 0){
            statTopicVoList = new ArrayList<>();
        }else{
            if(a1.equals(type) || a2.equals(type)){
                //家里人,议事厅
                for (StatTopicVo topicVo: statTopicVoList){
                    //查询发帖人等级头衔
                    topicVo.setGradeTitle(getGradeTitle(topicVo.getUserId()));
                }
            }else if(a3.equals(type)){
                //社区话题
                for (StatTopicVo topicVo: statTopicVoList){
                    String userName = bizCommunityTopicMapper.selectUserNameByUserId(topicVo.getUserId());
                    if(userName != null && !StringUtils.isEmpty(userName)){
                        topicVo.setUserName(userName);
                    }
                }
            }
        }
        return statTopicVoList;
    }

    /**
     * 导出帖子列表
     * @param projectId
     * @param type
     * @param dataType
     * @param startTime
     * @param endTime
     * @param pSort
     * @param uSort
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse exportStatTopicExcel(String projectId,String type,String dataType,String startTime,String endTime
            ,String pSort,String uSort, Integer page, Integer limit){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<Map<String, Object>> dataList = new ArrayList<>();
        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        List<StatTopicVo> statTopicVoList = bizReaderDetailMapper.selectTopicListByType(type,projectId,dataType,
                startTime,endTime,pSort,uSort,0,limit);
        if(statTopicVoList == null || statTopicVoList.size() == 0){
            statTopicVoList = new ArrayList<>();
        }else{
            if(a1.equals(type) || a2.equals(type)){
                //家里人,议事厅
                for (StatTopicVo topicVo: statTopicVoList){
                    //查询发帖人等级头衔
                    topicVo.setGradeTitle(getGradeTitle(topicVo.getUserId()));
                }
            }else if(a3.equals(type)){
                //社区话题
                for (StatTopicVo topicVo: statTopicVoList){
                    String userName = bizCommunityTopicMapper.selectUserNameByUserId(topicVo.getUserId());
                    if(userName != null && !StringUtils.isEmpty(userName)){
                        topicVo.setUserName(userName);
                    }
                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        if (statTopicVoList != null && statTopicVoList.size() > 0) {
            for (int i = 0; i < statTopicVoList.size(); i++) {
                StatTopicVo temp = mapper.convertValue(statTopicVoList.get(i), StatTopicVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        String fileName = "";
        if(a1.equals(type)){
            //家里人
            String[]  titles = {"内容","发帖人","等级","发帖时间","点赞数","PV数","UV数"};
            String[]  keys = {"content","userName","gradeTitle","createTime","upNum","pv","uv"};
            excelInfoVo.setTitles(titles);
            excelInfoVo.setKeys(keys);
            fileName = "家里人数据统计.xlsx";
        }else if(a2.equals(type)){
           //议事厅
            String[]  titles = {"内容","帖子类型","话题标签","发帖人","等级","发帖时间","点赞数","PV数","UV数"};
            String[] keys = {"content","topicType","tagName","userName","gradeTitle","createTime","upNum","pv","uv"};
            excelInfoVo.setTitles(titles);
            excelInfoVo.setKeys(keys);
            fileName = "议事厅数据统计.xlsx";
        }else if(a3.equals(type)){
           //社区话题
            String[]  titles = {"内容","发帖人","发帖时间","点赞数","PV数","UV数"};
            String[] keys = {"content","userName","createTime","upNum","pv","uv"};
            excelInfoVo.setTitles(titles);
            excelInfoVo.setKeys(keys);
            fileName = "社区话题数据统计.xlsx";
        }else{
            //业主圈帖子
            String[]  titles = {"内容","发帖人","所属小组","发帖时间","点赞数","PV数","UV数"};
            String[] keys = {"content","userName","groupName","createTime","upNum","pv","uv"};
            excelInfoVo.setTitles(titles);
            excelInfoVo.setKeys(keys);
            fileName = "业主圈帖子数据统计.xlsx";
        }
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);
        return msg;
    }








    /**
     * 查询统计各帖子列表数量
     * @param projectId
     * @param type
     * @param dataType
     * @param startTime
     * @param endTime
     * @return
     */
    public int selectStatTopicCount(String projectId,String type,String dataType,String startTime,String endTime){
        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        String a4 = "4";
        List<StatTopicVo> statTopicVoList =bizReaderDetailMapper.selectTopicListByType(type,projectId,dataType,
                startTime,endTime,null,null,null,null);
        if(statTopicVoList == null || statTopicVoList.size() == 0){
            statTopicVoList = new ArrayList<>();
        }
        return statTopicVoList.size();
    }


    public String getGradeTitle(String userId){
        //查询发帖人等级头衔
        int uValue = 0;
        int uPoint = 0;
        String userValue = bizFamilyPostsMapper.selectUserValueById(userId);
        String userPoint = bizFamilyPostsMapper.selectUserPointById(userId);
        if(userValue != null || !org.apache.commons.lang3.StringUtils.isEmpty(userValue)){
            uValue = Integer.parseInt(userValue);
        }
        if(userPoint != null || !org.apache.commons.lang3.StringUtils.isEmpty(userPoint)){
            uPoint = Integer.parseInt(userPoint);
        }
        int total = uValue + uPoint;
        String gradeTitle = "";
        List<GradeRuleVo> gradeRuleVoList =  bizFamilyPostsMapper.selectGradeRuleList();
        if(gradeRuleVoList != null && gradeRuleVoList.size() > 0){
            for (GradeRuleVo vo : gradeRuleVoList){
                if(total >= vo.getIntegral()){
                    gradeTitle = vo.getGradeTitle();
                }
            }
        }
        return gradeTitle;
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