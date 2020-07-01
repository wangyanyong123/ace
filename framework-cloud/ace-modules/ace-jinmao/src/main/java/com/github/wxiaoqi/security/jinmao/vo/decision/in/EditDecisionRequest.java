package com.github.wxiaoqi.security.jinmao.vo.decision.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionAnnexInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: guohao
 * @date: 2020-06-04 13:53
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class EditDecisionRequest extends BaseIn {
    private static final long serialVersionUID = 8306756195092540042L;

    private String id;

    //项目id
    private String projectId;

    //事件类型 1：一般事件 2：特殊事件
    private Integer eventType;

    //标题
    private String title;

    //内容
    private String content;

    //
    private Date startTime;

    //
    private Date endTime;

    //
    private String remark;

    private List<DecisionAnnexInfo> annexList;

    @Override
    public void check() {

        if(StringUtils.isEmpty(id)){
            Assert.hasLength(projectId,"projectId  为空");
            Assert.notNull(eventType,"eventType  为空");
            Assert.hasLength(title,"title  为空");
            Assert.hasLength(content,"content  为空");
            Assert.notNull(startTime,"startTime  为空");
            Assert.notNull(endTime,"endTime  为空");
        }

    }

}
