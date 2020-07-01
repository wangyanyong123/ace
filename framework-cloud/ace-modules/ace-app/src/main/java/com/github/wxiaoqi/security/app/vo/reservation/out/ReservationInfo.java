package com.github.wxiaoqi.security.app.vo.reservation.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecInfo;
import com.github.wxiaoqi.security.app.vo.product.out.UserCommentVo;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationInfo implements Serializable {

    @ApiModelProperty(value = "服务id")
    private String id;
    @ApiModelProperty(value = "服务分类id")
    private String classifyId;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "服务图文详情")
    private String reservationImagetextInfo;

    //修改
    @ApiModelProperty(value = "服务LOGO")
    private String productImage;
    @ApiModelProperty(value = "服务LOGO图片地址")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "服务精选")
    private String selectionImage;
    @ApiModelProperty(value = "服务精选图片")
    private List<ImgInfo> selectionImageList;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "原价")
    private String originalPrice;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;
    @ApiModelProperty(value = "已选规格")
    private String selectedSpec;
    @ApiModelProperty(value = "送至地址")
    private String address;
    @ApiModelProperty(value = "购买数量")
    private String buyNum;
    @ApiModelProperty(value = "商品规格信息")
    private List<ProductSpecInfo> productSpecInfo;

    @ApiModelProperty(value = "公司id")
    private String companyId;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司logo")
    private String logoImg;
    @ApiModelProperty(value = "公司logo图片地址")
    private List<ImgInfo> logoImgList;
    @ApiModelProperty(value = "用户评价")
    private List<UserCommentVo> userCommentList;

    @ApiModelProperty(value = "是否支持发票(1-是2-否)")
    private String isInvoice;
    @ApiModelProperty(value = "是否打烊(1-是2-否)")
    private String isClose;

    @ApiModelProperty(value = "优惠券信息")
    private List<String> couponList;

    @ApiModelProperty(value = "服务需知")
    private String reservationDesc;
    @ApiModelProperty(value = "服务热线")
    private String reservationTel;
    @ApiModelProperty(value = "服务日期范围(1-一个月内、2-二个月内...以此类推)")
    private String dataScopeVal;
    @ApiModelProperty(value = "上午开始时间")
    private String forenoonStartTime;
    @ApiModelProperty(value = "上午结束时间")
    private String forenoonEndTime;
    @ApiModelProperty(value = "上午库存")
    private int productNumForenoon;
    @ApiModelProperty(value = "下午开始时间")
    private String afternoonStartTime;
    @ApiModelProperty(value = "下午结束时间")
    private String afternoonEndTime;
    @ApiModelProperty(value = "下午库存")
    private int productNumAfternoon;
    @ApiModelProperty(value = "当前库存数(-1表示无限制，已翻译)")
    private String stockNum;
    @ApiModelProperty(value = "是否有库存(1-有库存2-没有)")
    private String isStock;
    @ApiModelProperty(value = "用户是否达到购买上限(1-达到2-没有达到)")
    private String isBuy;

    private int productNum;
    private int limitNum;

    public int getHowManyHoursLater(){
        return AceDictionary.RESERVATION_HOW_MANY_HOURS_LATER;
    }


    public List<ImgInfo> getProductImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(productImage)){
            String[] imArrayIds = new String[]{productImage};
            if(productImage.indexOf(",")!= -1){
                imArrayIds = productImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public List<ImgInfo> getSelectionImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(selectionImage)){
            String[] imArrayIds = new String[]{selectionImage};
            if(selectionImage.contains(",")){
                imArrayIds = selectionImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public List<ImgInfo> getLogoImgList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(logoImg)){
            String[] imArrayIds = new String[]{logoImg};
            if(logoImg.contains(",")){
                imArrayIds = logoImg.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public String getReservationRule(){
        if(StringUtils.isBlank(forenoonStartTime)||StringUtils.isBlank(forenoonStartTime)
                ||StringUtils.isBlank(afternoonStartTime)||StringUtils.isBlank(afternoonEndTime)
        ||StringUtils.isBlank(dataScopeVal)){
            return "";
        }
        String forenoonStart = forenoonStartTime.substring(0, forenoonStartTime.lastIndexOf(":"));
        String forenoonEnd = forenoonEndTime.substring(0, forenoonEndTime.lastIndexOf(":"));
        String afternoonStart = afternoonStartTime.substring(0, afternoonStartTime.lastIndexOf(":"));
        String afternoonEnd = afternoonEndTime.substring(0, afternoonEndTime.lastIndexOf(":"));
        String reservationRuleFormat= "预约规则：预约范围为%s个月内，只能预约%s小时后的时间，时间段为：上午：%s~%s,下午：%s~%s。";
    return String.format(reservationRuleFormat,dataScopeVal,getHowManyHoursLater(),forenoonStart,forenoonEnd,afternoonStart,afternoonEnd);

    }

}
