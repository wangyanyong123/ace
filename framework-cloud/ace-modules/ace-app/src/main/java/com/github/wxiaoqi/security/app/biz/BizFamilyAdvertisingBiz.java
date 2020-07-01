package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizFamilyAdvertising;
import com.github.wxiaoqi.security.app.mapper.BizFamilyAdvertisingMapper;
import com.github.wxiaoqi.security.app.vo.advertising.out.FamilyAdVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 家里人广告位
 *
 * @author huangxl
 * @Date 2019-08-12 17:59:01
 */
@Service
public class BizFamilyAdvertisingBiz extends BusinessBiz<BizFamilyAdvertisingMapper,BizFamilyAdvertising> {

    @Autowired
    private BizFamilyAdvertisingMapper bizFamilyAdvertisingMapper;

    /**
     * 查询app家里人广告位
     * @return
     */
    public ObjectRestResponse<List<FamilyAdVo>> getFamilyAdList(String projectId){
        List<FamilyAdVo> list = bizFamilyAdvertisingMapper.selectAppFamilyAdList(projectId);
        if(list == null && list.size() == 0){
            list = new ArrayList<>();
        }else {
            for (FamilyAdVo familyAdVo : list) {
                if (familyAdVo.getClassifyId() != null) {
                    String name =  bizFamilyAdvertisingMapper.getReserveName(familyAdVo.getObjectId());
                    familyAdVo.setName(name);
                }
            }
        }

        return ObjectRestResponse.ok(list);
    }








}