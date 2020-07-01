package com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;

public class ModuleTree extends TreeNodeVO<ModuleTree> {
    String label;
    String code;

    public ModuleTree() {

    }

    public ModuleTree(Object id, Object parentId, String label, String code) {
        this.label = label;
        this.code = code;
        this.setId(id);
        this.setParentId(parentId);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
