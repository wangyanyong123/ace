package com.github.wxiaoqi.security.jinmao.vo.integralproduct.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qs
 * @date 2019/9/9
 */
@Data
public class CashProductVo implements Serializable {

    @ApiModelProperty(value = "兑换id")
    private String id;
    @ApiModelProperty(value = "兑换编号")
    private String cashCode;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "客户名称")
    private String userName;
    @ApiModelProperty(value = "联系方式")
    private String mobilePhone;
    @ApiModelProperty(value = "兑换数")
    private String cashNum;
    @ApiModelProperty(value = "兑换积分")
    private String cashIntegral;
    @ApiModelProperty(value = "地址")
    private String addr;
    @ApiModelProperty(value = "兑换时间")
    private String cashTime;

    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "商品封面")
    private String productImage;

    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;


    private String signDate;

    private String useTime;



    public List<ImgInfo> getProductImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(productImage)){
            String[] imgArrayIds =new String[] { productImage };
            if(productImage.indexOf(",")!=-1){
                imgArrayIds=productImage.split(",");
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
