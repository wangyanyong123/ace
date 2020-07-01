package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizProductRecommend;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductRecommendMapper;
import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import com.github.wxiaoqi.security.app.vo.recommend.out.RecommendProductVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品推荐表
 *
 * @author zxl
 * @Date 2018-12-10 10:10:55
 */
@Service
public class BizProductRecommendBiz extends BusinessBiz<BizProductRecommendMapper,BizProductRecommend> {

    @Resource
    private BizProductRecommendMapper bizProductRecommendMapper;
    @Resource
    private BizProductMapper bizProductMapper;

    /**
     * 商品推荐列表
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<RecommendProductVo> getRecommendProduct(String projectId,Integer page, Integer limit) {
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        if (StringUtils.isEmpty(projectId)) {
            msg.setMessage("projectId不能为空！");
            return msg;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<RecommendProductVo> recommendProductVos = bizProductRecommendMapper.selectRecommendProduct(projectId,null,startIndex,limit);
        if (recommendProductVos == null || recommendProductVos.size() == 0) {
            recommendProductVos = new ArrayList<>();
        }
        if (recommendProductVos.size() != 0){
            List<String> productList = new ArrayList<>();
            for (RecommendProductVo recommendProductVo : recommendProductVos) {
                productList.add(recommendProductVo.getId());
            }
            List<String> couponIds = bizProductMapper.selectCouponIds(productList);
            if (couponIds.size() != 0) {
                bizProductMapper.updateCouponStatusByProduct(couponIds);
            }
        }
        for (RecommendProductVo productVo : recommendProductVos) {
            List<String> labelList = bizProductRecommendMapper.getRecProductLabel(productVo.getId());
            if (labelList != null && labelList.size() > 0) {
                String labelName = "";
                StringBuilder sb = new StringBuilder();
                for (String name : labelList) {
                    sb.append(name + ",");
                }
                labelName = sb.substring(0, sb.length() - 1);
                productVo.setLabelName(labelName);
            }else {
                productVo.setLabelName("");
            }
            //商品图片
            List<ImgeInfo> list = new ArrayList<>();
            ImgeInfo imgeInfo = new ImgeInfo();
            imgeInfo.setUrl(productVo.getProductImage());
            list.add(imgeInfo);
            productVo.setProductImageUrl(list);
            //商品单价
            productVo.setPrice(df.format(Double.parseDouble(productVo.getPrice())));
        }

        msg.setData(recommendProductVos);
        return msg;
    }

    public List<RecommendProductVo> getRecommendProduct(String projectId, String busId, Integer productType,
                                                        List<String> cityCodeList, Integer page, Integer limit) {
        List<RecommendProductVo> recommendProductVos = doGetRecommendList(projectId, busId, productType,cityCodeList, page, limit);
        return recommendProductVos;
    }

    private List<RecommendProductVo> doGetRecommendList(String projectId, String busId, Integer productType,
                                                        List<String> cityCodeList, Integer page, Integer limit){
        if ((page == null) || (page == 0)) {
            page = 1;
        }
        if (limit == null || limit == 0) {
            limit = 10;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<RecommendProductVo> recommendProductVos;
        if(productType == null){
            recommendProductVos = bizProductRecommendMapper.selectRecommendProduct(projectId,cityCodeList,startIndex,limit);
        }else if(productType ==1){
            recommendProductVos = bizProductRecommendMapper.selectRecommendProductList(projectId,busId,productType,cityCodeList,startIndex,limit);
        }else if(productType ==2){
            recommendProductVos = bizProductRecommendMapper.selectRecommendReservationList(projectId,busId,productType,cityCodeList,startIndex,limit);
        }else{
            recommendProductVos = Collections.emptyList();
        }
        return recommendProductVos;
    }
}
