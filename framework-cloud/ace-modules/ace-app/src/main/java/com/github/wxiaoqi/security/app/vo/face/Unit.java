package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:29 2018/12/27
 * @Modified By:
 */
@Data
public class Unit implements Serializable {

	private static final long serialVersionUID = -3553471099074828904L;
	private String unitId;
	//预售楼层名称
	private String elevatorAuthority;

	public List<Integer> getElevatorAuthority(){
		List<Integer> floorNumList = new ArrayList<>();
		if(StringUtils.isNoneBlank(elevatorAuthority)){
			String[] floorNames = elevatorAuthority.split(",");
			Arrays.asList(floorNames).forEach(floorName->{
				if (StringUtils.isNoneBlank(floorName)){
					String floorNum = StringUtils.remove(floorName,"层");
					if(StringUtils.isNoneBlank(floorNum) && StringUtils.isNumeric(floorNum)){
						floorNumList.add(Integer.parseInt(floorNum));
					}
				}
			});
		}
		return floorNumList;
	}
}
