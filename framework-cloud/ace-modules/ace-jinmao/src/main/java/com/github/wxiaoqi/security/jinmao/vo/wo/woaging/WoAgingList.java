package com.github.wxiaoqi.security.jinmao.vo.wo.woaging;

import lombok.Data;

import java.io.Serializable;

@Data
public class WoAgingList implements Serializable {

    private String woCode;

    private String incidentType;

    private String title;

    private String handleBy;

    private String createTime;

    private String finishTime;

    private String receiveTime;

    private String woStatus;

    private String woStatusStr;

    public String getWoStatusStr(){
        String woStatusStr = "";
        if("00".equals(woStatus)){
            woStatusStr = "待系统受理";
        }else if("01".equals(woStatus)){
            woStatusStr = "待接受";
        }else if("02".equals(woStatus)){
            woStatusStr = "已接受";
        }else if("03".equals(woStatus)){
            woStatusStr = "处理中";
        }else if("04".equals(woStatus)){
            woStatusStr = "暂停中";
        }else if("05".equals(woStatus)){
            woStatusStr = "已完成";
        }else if("06".equals(woStatus)){
            woStatusStr = "已取消";
        }else if("07".equals(woStatus)){
            woStatusStr = "已取消";
        }
        return woStatusStr;
    }
}
