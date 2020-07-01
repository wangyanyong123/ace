package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.jinmao.entity.SysCoverageStat;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.SysCoverageStatMapper;
import com.github.wxiaoqi.security.jinmao.vo.CoverageStat.CoverageStatVo;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.out.SensitiveVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 覆盖率统计表
 *
 * @author huangxl
 * @Date 2019-10-28 09:57:52
 */
@Service
public class SysCoverageStatBiz extends BusinessBiz<SysCoverageStatMapper,SysCoverageStat> {

    @Autowired
    private SysCoverageStatMapper sysCoverageStatMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;


    /**
     * 查询统计数据列表
     * @param dayTime
     * @param page
     * @param limit
     * @return
     */
    public List<CoverageStatVo> getCoverageStatList(String dayTime,Integer page, Integer limit){
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
        List<CoverageStatVo> coverageStatVoList = sysCoverageStatMapper.selectCoverageStatList(dayTime,startIndex,limit);
        return coverageStatVoList;
    }


    public int selectCoverageStatCount(String dayTime){
        return sysCoverageStatMapper.selectCoverageStatCount(dayTime);
    }




    /**
     * 导出excel
     * @param dayTime
     * @return
     */
    public ObjectRestResponse exportExcel(String dayTime) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(dayTime)){
            msg.setStatus(501);
            msg.setMessage("请选择导出的日期");
            return msg;
        }
        //查询统计数据
        List<CoverageStatVo> coverageStatVoList = sysCoverageStatMapper.selectCoverageStatList(dayTime,null,null);
        if(coverageStatVoList == null || coverageStatVoList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (coverageStatVoList != null && coverageStatVoList.size() > 0) {
            for (int i = 0; i < coverageStatVoList.size(); i++) {
                CoverageStatVo temp = mapper.convertValue(coverageStatVoList.get(i), CoverageStatVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"项目编码","项目名称","累计认证户数","新增认证户数"};
        String[] keys = {"projectCode","projectName","sumHouseNum","addHouseNum"};
        String fileName = "日数据统计表.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);
        return msg;
    }





}