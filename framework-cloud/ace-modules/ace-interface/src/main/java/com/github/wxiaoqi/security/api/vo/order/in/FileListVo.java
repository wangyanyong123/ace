package com.github.wxiaoqi.security.api.vo.order.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class FileListVo implements Serializable {

    private List<Map<String,String>> fileList;

}
