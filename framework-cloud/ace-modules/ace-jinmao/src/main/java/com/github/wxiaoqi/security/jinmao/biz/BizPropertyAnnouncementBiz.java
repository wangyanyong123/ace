package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizPropertyAnnouncement;
import com.github.wxiaoqi.security.jinmao.entity.BizPropertyAnnouncementReader;
import com.github.wxiaoqi.security.jinmao.feign.AdminFeign;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizPropertyAnnouncementMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizPropertyAnnouncementReaderMapper;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.InputParam.SaveAnnouncementParam;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAnnouncementInfo;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAnnouncementVo;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAppAnnouncementInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAppAnnouncementVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物业公告
 *
 * @author Mr.AG
 * @version 2018-11-26 13:57:07
 * @email 463540703@qq.com
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizPropertyAnnouncementBiz extends BusinessBiz<BizPropertyAnnouncementMapper, BizPropertyAnnouncement> {
    private Logger logger = LoggerFactory.getLogger(BizPropertyAnnouncementBiz.class);
    @Autowired
    private BizPropertyAnnouncementMapper bizPropertyAnnouncementMapper;
    @Autowired
    private BizPropertyAnnouncementReaderMapper bizPropertyAnnouncementReaderMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private OssExcelFeign ossExcelFeign;

    /**
     * 查询物业公告列表
     *
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultAnnouncementVo> getAnnouncementList(String searchVal, Integer page, Integer limit) {
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
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResultAnnouncementVo> announcementVoList = bizPropertyAnnouncementMapper.selectAnnouncementList(null, type, BaseContextHandler.getTenantID(),
                searchVal, startIndex, limit);
        return announcementVoList;
    }

    /**
     * 根据条件查询物业公告数量
     *
     * @param searchVal
     * @return
     */
    public int selectAnnouncementCount(String searchVal) {
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        int total = bizPropertyAnnouncementMapper.selectAnnouncementCount(type, BaseContextHandler.getTenantID(), searchVal);
        return total;
    }


    /**
     * 删除物业公告
     *
     * @param id
     * @return
     */
    public ObjectRestResponse delAnnouncementInfo(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if (bizPropertyAnnouncementMapper.delAnnouncementInfo(id, BaseContextHandler.getUserID()) < 0) {
            msg.setStatus(102);
            msg.setMessage("删除物业公告失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 发布物业公告
     *
     * @param id
     * @return
     */
    public ObjectRestResponse publisherAnnouncement(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if (bizPropertyAnnouncementMapper.publisherAnnouncement(id, BaseContextHandler.getUserID()) < 0) {
            msg.setStatus(102);
            msg.setMessage("发布物业公告失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 保存物业公告
     *
     * @param param
     * @return
     */
    public ObjectRestResponse saveAnnouncement(SaveAnnouncementParam param) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ObjectRestResponse msg = new ObjectRestResponse();
        Map<String, String> map = adminFeign.getDictValue("announcement");
        String projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
        BizPropertyAnnouncement ann = new BizPropertyAnnouncement();
        ann.setId(UUIDUtils.generateUuid());
        ann.setAnnouncementType(param.getAnnouncementType());
        ann.setAnnouncementName(map.get(param.getAnnouncementType()));
        ann.setTitle(param.getTitle());
        ann.setContent(param.getContent());
        ann.setImportantDegree(param.getImportantDegree());
        ann.setImages(param.getImages());
        ann.setProjectId(projectId);
        ann.setPublisher(param.getPublisher());
        try {
            ann.setPublisherTime(sdf.parse(param.getPublisherTime()));
        } catch (ParseException e) {
            logger.error("处理时间异常", e);
        }
        ann.setTenantId(BaseContextHandler.getTenantID());
        ann.setStatus(param.getStatus());
        ann.setTimeStamp(new Date());
        ann.setCreateBy(BaseContextHandler.getUserID());
        ann.setCreateTime(new Date());
        if (bizPropertyAnnouncementMapper.insertSelective(ann) < 0) {
            msg.setStatus(101);
            msg.setMessage("保存物业公告失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询物业公告详情
     *
     * @param id
     * @return
     */
    public List<ResultAnnouncementInfo> getAnnouncementInfo(String id) {
        List<ResultAnnouncementInfo> resultVo = new ArrayList<>();
        ResultAnnouncementInfo announcementInfo = bizPropertyAnnouncementMapper.selectAnnouncementInfo(id);
        if (announcementInfo != null) {
            resultVo.add(announcementInfo);
        } else {
            ResultAnnouncementInfo vo = new ResultAnnouncementInfo();
            resultVo.add(vo);
        }
        return resultVo;
    }

    /**
     * 编辑物业公告
     *
     * @param param
     * @return
     */
    public ObjectRestResponse updateAnnouncement(SaveAnnouncementParam param) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ObjectRestResponse msg = new ObjectRestResponse();
        Map<String, String> map = adminFeign.getDictValue("announcement");
        ResultAnnouncementInfo announcementInfo = bizPropertyAnnouncementMapper.selectAnnouncementInfo(param.getId());
        if (announcementInfo != null) {
            try {
                BizPropertyAnnouncement ann = new BizPropertyAnnouncement();
                BeanUtils.copyProperties(announcementInfo, ann);
                ann.setAnnouncementType(param.getAnnouncementType());
                ann.setAnnouncementName(map.get(param.getAnnouncementType()));
                ann.setTitle(param.getTitle());
                ann.setContent(param.getContent());
                ann.setImportantDegree(param.getImportantDegree());
                ann.setImages(param.getImages());
                ann.setPublisher(param.getPublisher());
                try {
                    ann.setPublisherTime(sdf.parse(param.getPublisherTime()));
                } catch (ParseException e) {
                    logger.error("处理时间异常", e);
                }
                ann.setStatus(param.getStatus());
                ann.setTimeStamp(new Date());
                ann.setModifyBy(BaseContextHandler.getUserID());
                ann.setModifyTime(new Date());
                if (bizPropertyAnnouncementMapper.updateByPrimaryKeySelective(ann) < 0) {
                    msg.setStatus(101);
                    msg.setMessage("编辑物业公告失败!");
                    return msg;
                }
            } catch (Exception e) {
                logger.error("编辑物业公告失败!", e);
            }
        } else {
            msg.setStatus(102);
            msg.setMessage("编辑物业公告失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询物业公告列表--App
     *
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse getAppAnnouncementList(String projectId, Integer page, Integer limit) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(projectId)) {
            msg.setStatus(101);
            msg.setMessage("项目id不能为空!");
            return msg;
        }
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
        List<ResultAppAnnouncementVo> appAnnouncementVoList = bizPropertyAnnouncementMapper.selectAppAnnouncementList(projectId, startIndex, limit);
        for (ResultAppAnnouncementVo annVo : appAnnouncementVoList) {
            int readerNum = bizPropertyAnnouncementMapper.selectAppAnnCount(annVo.getId());
            annVo.setReaderNum(readerNum);
        }
        if (appAnnouncementVoList.size() == 0 && appAnnouncementVoList == null) {
            appAnnouncementVoList = new ArrayList<>();
        }
        msg.setData(appAnnouncementVoList);
        return msg;
    }


    public ObjectRestResponse getAppAnnouncementInfo(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        //点击详情，插入一条阅读记录
        msg = addReader(id);
        ResultAppAnnouncementInfoVo info = bizPropertyAnnouncementMapper.selectAppAnnouncementInfo(id);
        msg.setData(info);
        return msg;
    }


    public ObjectRestResponse addReader(String annId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizPropertyAnnouncementReader reader = new BizPropertyAnnouncementReader();
        reader.setId(UUIDUtils.generateUuid());
        reader.setAnnouncementId(annId);
        reader.setReaderId("1");
        reader.setTimeStamp(new Date());
        reader.setCreateBy("1");
        reader.setCreateTime(new Date());
        if (bizPropertyAnnouncementReaderMapper.insertSelective(reader) < 0) {
            msg.setStatus(102);
            msg.setMessage("插入阅读记录失败!");
        }
        return msg;
    }

    public ObjectRestResponse exportExcel(String id, String title) {
        ObjectRestResponse msg = new ObjectRestResponse();
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResultAnnouncementVo> announcementVoList = bizPropertyAnnouncementMapper.selectAnnouncementList(id, type, BaseContextHandler.getTenantID(),
                title, null, null);
        if (announcementVoList == null || announcementVoList.size() == 0) {
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
//        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> mapList = announcementVoList.parallelStream().map(Bean2MapUtil::transBean2Map).collect(Collectors.toList());

        String[] titles = {"公告标题", "公告类型", "发布人", "发布时间", "状态"};
        String[] keys = {"title", "announcementName", "publisher", "publisherTime", "statusName"};
        String fileName = "物业公告.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(mapList);
        excelInfoVo.setFileName(fileName);
        return ossExcelFeign.uploadExcel(excelInfoVo);
    }
}