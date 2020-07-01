package com.github.wxiaoqi.security.app.vo.decision.in;

import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author: guohao
 * @date: 2020-06-04 14:16
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryDecisionRequest extends BaseQueryIn {

    private static final long serialVersionUID = -8381725522224409655L;
    private List<String> projectIds;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    @Override
    protected void doCheck() {
        Assert.notEmpty(projectIds,"projectIds 为空");
    }
}
