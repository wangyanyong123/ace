package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizContentReader;
import com.github.wxiaoqi.security.app.entity.BizContentReaderDetail;
import com.github.wxiaoqi.security.app.entity.BizProductGoodtoVisit;
import com.github.wxiaoqi.security.app.mapper.BizContentReaderDetailMapper;
import com.github.wxiaoqi.security.app.mapper.BizContentReaderMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductGoodtoVisitMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductRecommendMapper;
import com.github.wxiaoqi.security.app.vo.goodvisit.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 好物探访表
 *
 * @author zxl
 * @Date 2018-12-13 09:56:57
 */
@Service
public class BizProductGoodtoVisitBiz extends BusinessBiz<BizProductGoodtoVisitMapper, BizProductGoodtoVisit> {


    @Autowired
    private BizProductGoodtoVisitMapper bizProductGoodtoVisitMapper;
    @Autowired
    private BizContentReaderMapper bizContentReaderMapper;
    @Autowired
    private BizContentReaderDetailMapper bizContentReaderDetailMapper;
    @Autowired
    private BizProductRecommendMapper bizProductRecommendMapper;

    /**
     * 查询好物探访列表
     * @return
     */
    public ObjectRestResponse getGoodVisitList(String projectId,Integer page, Integer limit) {
        ObjectRestResponse msg = new ObjectRestResponse();
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
        List<GoodVisitVo> goodVisitList = bizProductGoodtoVisitMapper.getGoodVisitList(projectId,startIndex, limit);
        if ( goodVisitList != null && goodVisitList.size() > 0) {
            for (GoodVisitVo goodVisitVo : goodVisitList) {
                GoodVisitReaderVo goodVisitReaderVo = bizContentReaderMapper.selectContentReader(goodVisitVo.getId());
                List<GoodVisitUserPhoto> userPhoto = bizProductGoodtoVisitMapper.selectGoodVisitUserPhoto(goodVisitVo.getId());
                if (goodVisitReaderVo == null) {
                    goodVisitVo.setViewNum(0);
                }else {
                    goodVisitVo.setViewNum(goodVisitReaderVo.getViewNum());
                }
                if (userPhoto != null && userPhoto.size() > 0) {
                    List<ImgeInfo> list = new ArrayList<>();
                    for (GoodVisitUserPhoto goodVisitUserPhoto : userPhoto) {
                        ImgeInfo imgInfo = new ImgeInfo();
                        if (goodVisitUserPhoto.getProfilePhoto()!=null) {
                            imgInfo.setUrl(goodVisitUserPhoto.getProfilePhoto());
                        }else {
                            imgInfo.setUrl(getRandomPhoto());
                        }
                        list.add(imgInfo);
                    }
                    if (userPhoto.size() < 3 && goodVisitVo.getViewNum() > userPhoto.size()) {
                        ImgeInfo imgeInfo = new ImgeInfo();
                        if (userPhoto.size() == 1 && list.size() == 1 && goodVisitVo.getViewNum() >= 3 ) {
                            for (int i = 0; i < 2; i++) {
                                imgeInfo.setUrl(getRandomPhoto());
                                list.add(imgeInfo);
                            }
                        } else {
                            imgeInfo.setUrl(getRandomPhoto());
                            list.add(imgeInfo);
                        }
                    }
                    goodVisitVo.setProfilePhoto(list);
                } else {
                    goodVisitVo.setProfilePhoto(new ArrayList<>());
                }
            }
            msg.setData(goodVisitList);
        }else {
            goodVisitList = new ArrayList<>();
            msg.setData(goodVisitList);
        }
        return msg;
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public ObjectRestResponse<GoodVisitDetailsVo> getGoodVisitDetails(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat sdf = new DecimalFormat("0.00");
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        //点击详情加一阅读量
        msg =  addViewNum(id);

        GoodVisitDetailsVo goodVisitDetails = bizProductGoodtoVisitMapper.getGoodVisitDetails(id);
        if (goodVisitDetails != null) {
            ProductInfoVo productInfo = bizProductGoodtoVisitMapper.getProductInfo(goodVisitDetails.getProductId());
            List<String> label = bizProductRecommendMapper.getRecProductLabel(productInfo.getId());
            if (label != null && label.size() >0) {
                String labelName = "";
                StringBuilder sb = new StringBuilder();
                for (String name : label) {
                    sb.append(name + ",");
                }
                labelName = sb.substring(0, sb.length() - 1);
                productInfo.setLabelName(labelName);
            }else {
                productInfo.setLabelName("");
            }
            productInfo.setProductPrice(sdf.format(Double.parseDouble(productInfo.getProductPrice())));
            goodVisitDetails.setProductInfo(productInfo);
        }
        //商品介绍
        msg.setData(goodVisitDetails);
        return msg;
    }

    /**
     * 阅读数添加
     * @param id
     * @return
     */
    private ObjectRestResponse addViewNum(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizContentReader reader = new BizContentReader();
        BizContentReaderDetail detail = new BizContentReaderDetail();
        GoodVisitReaderVo goodVisitReaderVo = bizContentReaderMapper.selectContentReader(id);
        ContentAndUser content = bizContentReaderDetailMapper.selectContentDetail(BaseContextHandler.getUserID(), id);
        if (goodVisitReaderVo != null) {
            //非首次阅读
            BeanUtils.copyProperties(goodVisitReaderVo,reader);
            reader.setViewNum(goodVisitReaderVo.getViewNum()+1);
            reader.setModifyTime(new Date());
            reader.setModifyTy(BaseContextHandler.getUserID());
            if (bizContentReaderMapper.updateByPrimaryKeySelective(reader) > 0) {
                if (BaseContextHandler.getUserID() != null) {
                    if (content == null ) {
                        //新用户阅读
                        detail.setId(UUIDUtils.generateUuid());
                        detail.setUserId(BaseContextHandler.getUserID());
                        detail.setCreateTime(new Date());
                        detail.setCreateBy(BaseContextHandler.getUserID());
                        detail.setContentId(id);
                        detail.setTimeStamp(new Date());
                        if (bizContentReaderDetailMapper.insertSelective(detail) < 0) {
                            msg.setStatus(102);
                            msg.setMessage("插入阅读记录失败!");
                            return msg;
                        }
                        //用户量加1
                        reader.setUserNum(goodVisitReaderVo.getUserNum() + 1);
                        if (bizContentReaderMapper.updateByPrimaryKeySelective(reader) < 0) {
                            msg.setStatus(102);
                            msg.setMessage("插入阅读记录失败!");
                            return msg;
                        }
                    }else {
                        //刷新用户阅读
                        bizContentReaderDetailMapper.updateReaderTime(id,BaseContextHandler.getUserID());
                    }
                }
            }
        }else {
            //阅读数据添加
            reader.setId(UUIDUtils.generateUuid());
            reader.setContentId(id);
            reader.setViewNum(1);
            reader.setCreateTime(new Date());
            reader.setTimeStamp(new Date());
            //阅读细节添加
            detail.setId(UUIDUtils.generateUuid());
            detail.setContentId(id);
            detail.setCreateTime(new Date());
            detail.setTimeStamp(new Date());
            if (BaseContextHandler.getUserID() != null){
                reader.setUserNum(1);
                reader.setCreateBy(BaseContextHandler.getUserID());
                //阅读细节
                detail.setUserId(BaseContextHandler.getUserID());
                detail.setCreateBy(BaseContextHandler.getUserID());
            }else {
                reader.setUserNum(0);
                reader.setCreateBy("");
                detail.setUserId("");
                detail.setCreateBy("");
            }
            if (bizContentReaderMapper.insertSelective(reader) > 0) {
                if (bizContentReaderDetailMapper.insertSelective(detail) < 0) {
                    msg.setStatus(102);
                    msg.setMessage("插入阅读记录失败!");
                    return msg;
                }
            }
        }

        return msg;
    }

    /**
     * 获取随机头像
     * @return
     */
    private String getRandomPhoto() {
        String userPhoto = "";
        List<String> list = Stream.of("http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo3@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo4@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo5@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo6@2x.png").collect(toList());
        Random random = new Random();
        int n = random.nextInt(list.size());
        userPhoto = list.get(n);
        return userPhoto;
    }

}