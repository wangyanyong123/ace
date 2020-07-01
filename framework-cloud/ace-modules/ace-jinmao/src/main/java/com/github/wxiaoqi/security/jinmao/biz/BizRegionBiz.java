package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizCompanyNoDelivery;
import com.github.wxiaoqi.security.jinmao.entity.BizRegion;
import com.github.wxiaoqi.security.jinmao.mapper.BizCompanyNoDeliveryMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizRegionMapper;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.in.SaveNoDelivery;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.out.ResultNoDelivery;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.out.ResultRegionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 省市区区域表
 *
 * @author zxl
 * @Date 2018-12-18 15:19:48
 */
@Service
public class BizRegionBiz extends BusinessBiz<BizRegionMapper,BizRegion> {

    private Logger logger = LoggerFactory.getLogger(BizRegionBiz.class);
    @Autowired
    private BizRegionMapper bizRegionMapper;
    @Autowired
    private BizCompanyNoDeliveryMapper bizCompanyNoDeliveryMapper;
    /**
     * 查询所有区域列表
     * @return
     */
    public List<ResultRegionList> getAllRegionList() {
        List<ResultRegionList> regionLists = new ArrayList<>();
        List<ResultRegionList> allRegionList = bizRegionMapper.getAllRegionList();
        String tenantID = BaseContextHandler.getTenantID();
        List<String> noDeliveryRegionList = bizRegionMapper.selectNoDeliveryRegion(BaseContextHandler.getTenantID());
        Map<String, String> regionMap = new HashMap<>(allRegionList.size());
        for (ResultRegionList region : allRegionList) {
            regionMap.put(region.getRegionCode(),region.getRegionName());
        }
        if (noDeliveryRegionList.size() > 0 && noDeliveryRegionList != null) {
            for (String noDeliveryCode : noDeliveryRegionList) {
                if (regionMap.containsKey(noDeliveryCode)){
                    regionMap.remove(noDeliveryCode);
                }
            }
        }
        for (Map.Entry<String, String> entry : regionMap.entrySet()) {
            for (ResultRegionList regionList : allRegionList) {
                if (entry.getKey() == regionList.getRegionCode()) {
                    regionLists.add(regionList);
                }
            }
        }
         //已添加的不显示
//        if (regionList != null && regionList.size() > 0) {
//            for (ResultRegionList resultRegionList : allRegionList) {
//                for (String regionCode : regionList) {
//                    if (!(resultRegionList.getRegionCode().equals(regionCode))) {
//
//                        resultRegionList.setRegionFullName(resultRegionList.getRegionFullName());
//                    }
//                }
//                regionLists.add(resultRegionList);
//            }
//        }else {
//            return allRegionList;
//        }
        return regionLists;
    }

    /**
     * 保存不配送区域
     * @return
     */
    public ObjectRestResponse saveNoDeliveryRegion(SaveNoDelivery param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizCompanyNoDelivery bizCompanyNoDelivery = new BizCompanyNoDelivery();
        if (param == null) {
            msg.setStatus(101);
            msg.setMessage("请先选择具体区域");
            return msg;
        }
        if (param.getRegionId() == null || param.getRegionId().size() == 0) {
            msg.setStatus(101);
            msg.setMessage("请先选择具体区域");
            return msg;
        }
        for (Map<String, String> regionId : param.getRegionId()) {
            ResultRegionList regionInfo = bizRegionMapper.selectRegionInfoById(regionId.get("id"));
            String noDeliveryId = UUIDUtils.generateUuid();
            bizCompanyNoDelivery.setId(noDeliveryId);
            bizCompanyNoDelivery.setCompanyId(BaseContextHandler.getTenantID());
            bizCompanyNoDelivery.setCreateBy(BaseContextHandler.getUserID());
            bizCompanyNoDelivery.setCreateTime(new Date());
            bizCompanyNoDelivery.setTimeStamp(new Date());
            bizCompanyNoDelivery.setRegionCode(regionInfo.getRegionCode());
            bizCompanyNoDelivery.setRegionName(regionInfo.getRegionName());
            bizCompanyNoDelivery.setFullName(regionInfo.getRegionFullName());
            if (bizCompanyNoDeliveryMapper.insertSelective(bizCompanyNoDelivery) < 0) {
                logger.error("保存数据失败,id为{}",param.getRegionId());
                msg.setStatus(102);
                msg.setMessage("保存数据失败");
                return msg;
            }
            msg.setData(noDeliveryId);
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 根据删除不配送区域
     * @param id
     * @return
     */
    public ObjectRestResponse deleteNoDeliveryById(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        int i = bizRegionMapper.deleteNoDeliveryById(id);
        if (i < 0) {
            logger.error("删除数据失败,id{}",id);
            msg.setStatus(102);
            msg.setMessage("删除数据失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 获取不配送区域列表
     * @return
     */
    public List<ResultNoDelivery> getNoDeliveryList() {
        return bizRegionMapper.selectNoDeliveryRegionList(BaseContextHandler.getTenantID());
    }
}