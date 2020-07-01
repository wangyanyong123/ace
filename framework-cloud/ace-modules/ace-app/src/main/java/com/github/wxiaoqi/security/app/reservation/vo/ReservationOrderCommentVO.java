package com.github.wxiaoqi.security.app.reservation.vo;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;


/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-25 18:23:26
 */
@Data
public class ReservationOrderCommentVO implements Serializable {
	private static final long serialVersionUID = 1L;
	


	    //
    private String imgIds;
	
	    //
    private String description;
	
	    //
    private Integer appraisalVal;
	
	    //
//    private String isArriveOntime;

	    //

    private String name;
	
	    //
    private Date createTime;


    private String profilePhoto;

    public List<String> getImgUrlList(){
        if(StringUtils.isNotEmpty(imgIds)){
            return Arrays.asList(imgIds.split(","));
        }else{
            return Collections.emptyList();
        }
    }

}
