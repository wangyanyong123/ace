package com.github.wxiaoqi.security.jinmao.vo.Service.OutParam;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;

public class ServiceTree extends TreeNodeVO<ServiceTree> {

    String name;

    public ServiceTree(){

    }

    public ServiceTree(Object id, Object parentId, String name){
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
