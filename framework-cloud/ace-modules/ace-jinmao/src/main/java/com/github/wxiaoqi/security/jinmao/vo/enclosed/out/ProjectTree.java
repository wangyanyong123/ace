package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModelProperty;

public class ProjectTree extends TreeNodeVO<ProjectTree> {

    String name;
    @ApiModelProperty(value = "(1-城市2-项目3-地块4-楼栋5-单元)")
    String flag;

    public ProjectTree() {

    }

    public ProjectTree(Object id,Object parentId,String name,String flag) {
        this.name = name;
        this.setId(id);
        this.setParentId(parentId);
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
