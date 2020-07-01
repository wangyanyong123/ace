package com.github.wxiaoqi.security.jinmao.vo.decoreteapply;

import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DecoreteApplyOut implements Serializable {
    private static final long serialVersionUID = 1175718890959806301L;

    private DecApplyDetailVo detail;

    @ApiModelProperty(value = "操作流水日志")
    private List<TransactionLogVo> transactionLogList;
}
