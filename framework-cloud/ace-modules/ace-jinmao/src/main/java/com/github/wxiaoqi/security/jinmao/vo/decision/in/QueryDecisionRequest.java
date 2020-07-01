package com.github.wxiaoqi.security.jinmao.vo.decision.in;

import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author: guohao
 * @date: 2020-06-04 14:16
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryDecisionRequest extends BaseQueryIn {
    private static final long serialVersionUID = 6131118852392938500L;

    private List<String> projectIds;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    @Override
    protected void doCheck() {

    }
}
