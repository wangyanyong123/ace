package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizSensitiveWords;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizSensitiveWordsMapper;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.in.SaveSensitiveParam;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.in.SensitiveStatusParam;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.out.SensitiveVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 敏感词表
 *
 * @author huangxl
 * @Date 2019-01-25 14:01:58
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizSensitiveWordsBiz extends BusinessBiz<BizSensitiveWordsMapper,BizSensitiveWords> {

    @Autowired
    private BizSensitiveWordsMapper bizSensitiveWordsMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;

    /**
     * 查询敏感词列表
     *
     * @param sensitiveStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<SensitiveVo> getSensitiveList(String sensitiveStatus, String searchVal, Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if (page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<SensitiveVo> sensitiveVoList = bizSensitiveWordsMapper.selectSensitiveList(sensitiveStatus, searchVal, startIndex, limit);
        if (sensitiveVoList == null || sensitiveVoList.size() == 0) {
            sensitiveVoList = new ArrayList<>();
        }
        return sensitiveVoList;
    }

    /**
     * 查询敏感词数量
     *
     * @param sensitiveStatus
     * @param searchVal
     * @return
     */
    public int selectSensitiveCount(String sensitiveStatus, String searchVal) {
        int total = bizSensitiveWordsMapper.selectSensitiveCount(sensitiveStatus, searchVal);
        return total;
    }

    /**
     * 保存敏感词
     *
     * @param param
     * @return
     */
    public ObjectRestResponse saveSensitiveWords(SaveSensitiveParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(param.getWords())) {
            msg.setStatus(501);
            msg.setMessage("请输入敏感词!");
            return msg;
        }
        //判断敏感词是否已存在
        if (isExist(param.getWords())) {
            msg.setStatus(501);
            msg.setMessage("该敏感词已存在!");
            return msg;
        }
        BizSensitiveWords words = new BizSensitiveWords();
        words.setId(UUIDUtils.generateUuid());
        words.setWords(param.getWords());
        words.setCreateBy(BaseContextHandler.getUserID());
        words.setCreateTime(new Date());
        words.setTimeStamp(new Date());
        if (bizSensitiveWordsMapper.insertSelective(words) < 0) {
            msg.setStatus(501);
            msg.setMessage("创建敏感词失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 判断敏感词是否已存在
     *
     * @param words
     * @return
     */
    public Boolean isExist(String words) {
        boolean flat = false;
        List<String> sensitiveVoList = bizSensitiveWordsMapper.selectAllSensitive();
        if (sensitiveVoList != null && sensitiveVoList.size() > 0) {
            for (String vo : sensitiveVoList) {
                if (words.equals(vo)) {
                    flat = true;
                }
            }
        }
        return flat;
    }


    /**
     * 查询敏感词详情
     *
     * @param id
     * @return
     */
    public List<SaveSensitiveParam> getSensitiveInfo(String id) {
        List<SaveSensitiveParam> msg = new ArrayList<>();
        SaveSensitiveParam info = bizSensitiveWordsMapper.selectSensitiveInfo(id);
        if (info == null) {
            info = new SaveSensitiveParam();
        }
        msg.add(info);
        return msg;
    }


    /**
     * 编辑敏感词
     *
     * @param param
     * @return
     */
    public ObjectRestResponse updateSensitiveInfo(SaveSensitiveParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(param.getWords())) {
            msg.setStatus(501);
            msg.setMessage("请输入敏感词!");
            return msg;
        }
        //判断敏感词是否已存在
        if (isExist(param.getWords())) {
            msg.setStatus(501);
            msg.setMessage("该敏感词已存在!");
            return msg;
        }
        SaveSensitiveParam info = bizSensitiveWordsMapper.selectSensitiveInfo(param.getId());
        if (info != null) {
            BizSensitiveWords words = new BizSensitiveWords();
            BeanUtils.copyProperties(info, words);
            words.setWords(param.getWords());
            words.setModifyBy(BaseContextHandler.getUserID());
            words.setModifyTime(new Date());
            words.setTimeStamp(new Date());
            if (bizSensitiveWordsMapper.updateByPrimaryKeySelective(words) < 0) {
                msg.setStatus(501);
                msg.setMessage("编辑敏感词失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 敏感词操作(0-删除,1-已启用,2-禁用)
     *
     * @param param
     * @return
     */
    public ObjectRestResponse updateSensitiveStatus(SensitiveStatusParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(param.getId())) {
            msg.setStatus(501);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if (bizSensitiveWordsMapper.updateSensitiveStatus(BaseContextHandler.getUserID(), param.getStatus(), param.getId()) < 0) {
            msg.setStatus(501);
            msg.setMessage("操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 导入excel模板
     * @param file
     * @return
     */
    public ObjectRestResponse importExcel(MultipartFile file) {
        ObjectRestResponse msg = new ObjectRestResponse();
        List<String> list = new ArrayList<>();
        if (file.isEmpty()) {
            msg.setStatus(501);
            msg.setMessage("上传文件不能为空，请导入excel文件!");
            return msg;
        }
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!fileType.toLowerCase().equals("xls") && !fileType.toLowerCase().equals("xlsx")){
            msg.setStatus(501);
            msg.setMessage("导入的文件格式不正确，请导入excel文件!");
            return msg;
        }
        // IO流读取文件
        InputStream input = null;
        Workbook wb = null;
        try {
                input =  file.getInputStream();
                // 创建文档
                wb = WorkbookFactory.create(input);
                //获取Excel文档中的第一个表单
                Sheet sht0 = wb.getSheetAt(0);
                //对Sheet中的每一行进行迭代
                for (Row r : sht0) {
                    //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
                    if (r.getRowNum() < 1) {
                        continue;
                    }
                    int type = r.getCell(0).getCellType();
                    if(HSSFCell.CELL_TYPE_STRING == r.getCell(0).getCellType()){
                        list.add(r.getCell(0).getStringCellValue());
                    }else if(HSSFCell.CELL_TYPE_NUMERIC == r.getCell(0).getCellType()){
                        String temp = String.valueOf(r.getCell(0).getNumericCellValue());
                        list.add(temp.substring(0,temp.indexOf(".")));
                    }
                }
            } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> newList = new ArrayList<>();
       //去重导入文件数据
        List<String> listTemp = new ArrayList();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        //判断excel导入的敏感词是否在数据库中有重复
        List<String> allSensitiveList = bizSensitiveWordsMapper.selectAllSensitive();
        for(String temp : listTemp){
            if(!allSensitiveList.contains(temp)){
                newList.add(temp);
            }
        }
        //添加进数据库
        for(String word : newList){
            BizSensitiveWords words = new BizSensitiveWords();
            String id = UUIDUtils.generateUuid();
            words.setWords(word);
            words.setSensitiveStatus("1");
            words.setId(id);
            words.setCreateBy(BaseContextHandler.getUserID());
            words.setCreateTime(new Date());
            words.setTimeStamp(new Date());
            if (bizSensitiveWordsMapper.insertSelective(words) <= 0) {
                msg.setStatus(501);
                msg.setMessage("导入数据失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 导出excel
     * @param sensitiveStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse exportExcel(String sensitiveStatus, String searchVal, Integer page, Integer limit){
        ObjectRestResponse msg = new ObjectRestResponse();
        //获取敏感词列表
        List<SensitiveVo> sensitiveVoList = bizSensitiveWordsMapper.selectExportSensitiveList(sensitiveStatus,searchVal);
        if(sensitiveVoList == null || sensitiveVoList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (sensitiveVoList != null && sensitiveVoList.size() > 0) {
            for (int i = 0; i < sensitiveVoList.size(); i++) {
                SensitiveVo temp = mapper.convertValue(sensitiveVoList.get(i), SensitiveVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"敏感词","状态"};
        String[] keys = {"words","sensitiveStatus"};
        String fileName = "敏感词.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }









}