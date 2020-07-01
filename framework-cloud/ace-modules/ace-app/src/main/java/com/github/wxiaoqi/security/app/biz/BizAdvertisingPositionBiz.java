package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.vo.advertising.out.AdvertisingInfo;
import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizAdvertisingPosition;
import com.github.wxiaoqi.security.app.mapper.BizAdvertisingPositionMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 优选商城广告位
 *
 * @author zxl
 * @Date 2018-12-17 15:07:24
 */
@Service
public class BizAdvertisingPositionBiz extends BusinessBiz<BizAdvertisingPositionMapper,BizAdvertisingPosition> {

    @Resource
    private BizAdvertisingPositionMapper bizAdvertisingPositionMapper;
    @Autowired
    private BizCrmProjectBiz bizCrmProjectBiz;

    /**
     * 获取广告位信息
     * @return
     */
    public ObjectRestResponse<AdvertisingInfo> getAdvertisinList(String projectId, Integer position) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (position == null) {
            position = 0;
        }
        List<AdvertisingInfo> advertisingInfoList = bizAdvertisingPositionMapper
                .selectAdvertList(Collections.singletonList(projectId), position);
        doSetImgInfo(advertisingInfoList);
        msg.setData(advertisingInfoList);
        return msg;
    }

    private void doSetImgInfo( List<AdvertisingInfo> advertisingInfoList){
        if(CollectionUtils.isEmpty(advertisingInfoList)){
            return;
        }
        advertisingInfoList.parallelStream().forEach(advertisingInfo->{
            List<ImgeInfo> list = new ArrayList<>();
            ImgeInfo imgeInfo = new ImgeInfo();
            imgeInfo.setUrl(advertisingInfo.getAdvertisingImg());
            list.add(imgeInfo);
            advertisingInfo.setAdvertisingImgUrl(list);
        });

    }
    public ObjectRestResponse<AdvertisingInfo> getAdvertisinListWithCityCode(Integer position,List<String> cityCodeList) {
        List<String> projectIdList = bizCrmProjectBiz.findProjectIdListByCCodeList(cityCodeList);
        List<AdvertisingInfo> advertisingInfoList;
        if(CollectionUtils.isNotEmpty(projectIdList)){
            advertisingInfoList = bizAdvertisingPositionMapper.selectAdvertList(projectIdList, position);
        }else{
            int count = bizCrmProjectBiz.count();
            advertisingInfoList= bizAdvertisingPositionMapper.findListOfAllProjectOptions(count);
        }
        doSetImgInfo(advertisingInfoList);
        return ObjectRestResponse.ok(advertisingInfoList);
    }
}
