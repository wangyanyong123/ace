package com.github.wxiaoqi.security.api.vo.order.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订购产品
 * @author huangxl
 * @date 2018-12-18
 */
@Data
public class SubProductInfo implements Serializable{

	private static final long serialVersionUID = -3002199661729776554L;

    @ApiModelProperty(value = "产品ID")
	private String productId;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "规格ID")
    private String specId;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "数量")
    private int subNum;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;
    //图片id,多张图片逗号分隔
    private String imgId;

    //图片路径
    @ApiModelProperty(value = "图片路径")
    private List<String> imgList;

    public List<ImgInfo> getImgList(){
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(imgId)){
            String[] imgArrayIds =new String[] { imgId };
            if(imgId.indexOf(",")!=-1){
                imgArrayIds=imgId.split(",");
            }
            for (String url : imgArrayIds){
                ImgInfo imgInfo = new ImgInfo();
                imgInfo.setUrl(url);
                list.add(imgInfo);
            }
        }
        return list;
    }

}