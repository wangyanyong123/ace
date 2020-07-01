package com.github.wxiaoqi.security.admin.vo.dict.out;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModelProperty;

public class DictTree extends TreeNodeVO<DictTree> {
    @ApiModelProperty(value = "编码id")
    private String id;
    @ApiModelProperty(value = "父编码id")
    private String pid;
    @ApiModelProperty(value ="值")
    private String val;
    @ApiModelProperty(value ="名称")
    private String name;

    public DictTree() {

    }

    public DictTree(Object id, Object pid,String val, String name) {
        this.name = name;
        this.val = val;
        this.setId(id);
        this.setParentId(pid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
