package com.github.wxiaoqi.security.jinmao.vo.familyad.in;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.ObjectInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/12
 */
@Data
public class SaveFamilyAdParam implements Serializable {


    @ApiModelProperty(value = "广告id(编辑时传)")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内链业务分类(1-家里人,2-议事厅,3-邻里活动,4-社区话题,5-业主圈帖子)")
    private String busClassify;
    @ApiModelProperty(value = "跳转业务(0-无,1-app内部,2-外部URL跳转)")
    private String skipBus;
    @ApiModelProperty(value = "业务对象")
    private List<ObjectInfo> objectVo;
    @ApiModelProperty(value = "跳转地址")
    private String skipUrl;
    @ApiModelProperty(value = "广告排序")
    private String viewSort;
    @ApiModelProperty(value = "所属项目")
    private List<ResultProjectVo> projectVo;
    @ApiModelProperty(value = "广告图片")
    private List<ImgInfo> adImageList;

    private String classifyId;









}
