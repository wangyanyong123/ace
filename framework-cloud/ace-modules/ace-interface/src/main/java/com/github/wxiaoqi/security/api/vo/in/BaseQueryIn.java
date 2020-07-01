package com.github.wxiaoqi.security.api.vo.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: guohao
 * @create: 2020-04-15 14:26
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseQueryIn extends BaseIn {

    private static final long serialVersionUID = 5948854871923817053L;
    @ApiModelProperty(value = "开始查询位置")
    private Integer start;

    @ApiModelProperty(value = "页码")
    private Integer page;

    @ApiModelProperty(value = "每页条数,默认10")
    private Integer limit=10;

    @ApiModelProperty(value = "商户ID")
    private String tenantId;

    @Override
    public void check() {
        initStart();
        doCheck();
    }

    private void initStart() {
        if (start == null && page != null && limit != null) {
            start = (page - 1) * limit;
        }
    }

    protected  abstract void doCheck();
}
