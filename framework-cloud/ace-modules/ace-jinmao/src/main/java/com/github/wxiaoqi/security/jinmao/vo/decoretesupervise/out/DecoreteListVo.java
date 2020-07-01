package com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out;

import lombok.Data;

import java.io.Serializable;

@Data
public class DecoreteListVo implements Serializable {
    private static final long serialVersionUID = 4789289192354751076L;

    /**
     * id,project_id projectId,service_price servicePrice,cost_price costPrice,
     *         promo_imge promoImge,service_intro serviceIntro,publish_status publishStatus
     */
    private String id;

    private String projectName;

    private String status;

    private String statusStr;

    public String getStatusStr() {
        String statusStr = "";
        if("0".equals(status)){
            statusStr = "未发布";
        }else if("1".equals(status)){
            statusStr = "已发布";
        }
        return statusStr;
    }
}
