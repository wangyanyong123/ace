package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;

public class EnclosedTree extends TreeNodeVO<EnclosedTree> {

    String name;

    public EnclosedTree(){

    }

    public EnclosedTree(Object id, Object parentId, String name){
        this.name=name;
        this.setId(id);
        this.setParentId(parentId);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
