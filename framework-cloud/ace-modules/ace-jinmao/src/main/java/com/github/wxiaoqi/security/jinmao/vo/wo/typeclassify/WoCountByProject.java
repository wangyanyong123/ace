package com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify;

import com.github.wxiaoqi.security.jinmao.vo.wo.date.WoOnDateCount;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WoCountByProject implements Serializable {

    private static final long serialVersionUID = -959289366879911187L;
    private String projectName;

    private List<WoOnDateCount> woOnDateCount;
}
