package com.github.wxiaoqi.security.api.vo.household;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;
import lombok.Data;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:10 2018/12/5
 * @Modified By:
 */
@Data
public class HouseInfoTree  extends TreeNodeVO<HouseInfoTree> {
	String name;
	String code;

	public HouseInfoTree() {
	}
	public HouseInfoTree(Object id, Object parentId, String name, String code) {
		this.name = name;
		this.code = code;
		this.setId(id);
		this.setParentId(parentId);
	}
}