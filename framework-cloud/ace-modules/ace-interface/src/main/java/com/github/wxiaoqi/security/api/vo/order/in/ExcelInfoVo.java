package com.github.wxiaoqi.security.api.vo.order.in;

import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ExcelInfoVo implements Serializable {

    private String[] titles;

    private String[] keys;

    private List<Map<String, Object>> dataList;

    private String fileName;

    private String content;

    private int width;

    private int height;
}
