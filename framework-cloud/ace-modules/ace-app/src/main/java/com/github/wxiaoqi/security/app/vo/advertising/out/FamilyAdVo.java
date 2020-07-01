package com.github.wxiaoqi.security.app.vo.advertising.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/12
 */
@Data
public class FamilyAdVo implements Serializable {


    @ApiModelProperty(value = "广告id")
    private String id;
    @ApiModelProperty(value = "广告标题")
    private String title;
    @ApiModelProperty(value = "广告图片")
    private String advertisingImg;
    @ApiModelProperty(value = "广告图片地址")
    private List<ImgInfo> advertisingImgUrl;
    @ApiModelProperty(value = "跳转业务类型(0-无,1-app内部,2-外部URL跳转)")
    private String skipBus;
    @ApiModelProperty(value = "外部跳转地址")
    private String skipUrl;
    @ApiModelProperty(value = "APP内部跳转业务(1-家里人,2-议事厅,3-邻里活动,4-社区话题,5-业主圈帖子6-优选商品7-团购8-疯抢9-服务预约)")
    private String busClassify;
    @ApiModelProperty(value = "内部跳转业务对象")
    private String objectId;

    private String classifyId;

    private String name;



    public List<ImgInfo> getAdvertisingImgUrl() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(advertisingImg)){
            String[] imArrayIds = new String[]{advertisingImg};
            if(advertisingImg.indexOf(",")!= -1){
                imArrayIds = advertisingImg.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }



}
