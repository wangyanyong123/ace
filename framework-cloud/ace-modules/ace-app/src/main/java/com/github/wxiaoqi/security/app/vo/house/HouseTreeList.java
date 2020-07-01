package com.github.wxiaoqi.security.app.vo.house;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:35 2018/12/4
 * @Modified By:
 */
@Data
public class HouseTreeList implements Serializable {
	private static final long serialVersionUID = -3672396933921461388L;
	private String id;
	private String parentId;
	private String name;
	private String code;

}
