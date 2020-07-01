package com.github.wxiaoqi.security.jinmao.vo.house;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;
import lombok.Data;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:35 2018/12/4
 * @Modified By:
 */
@Data
public class HouseInfoTree  extends TreeNodeVO<HouseInfoTree> {
	String name;
	String code;
	String nameStr;
	String type;

	public HouseInfoTree() {
	}
	public HouseInfoTree(Object id, Object parentId, String name,String nameStr,String code,String type) {
		this.name = name;
		this.nameStr = nameStr;
		this.code = code;
		this.type = type;
		this.setId(id);
		this.setParentId(parentId);
	}
}
