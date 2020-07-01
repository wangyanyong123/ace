package com.github.wxiaoqi.log.vo.liveness;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/9/12 16:23
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class LivenessTable implements Serializable {
    private static final long serialVersionUID = 8345099796888718917L;

    List<UserLiveness> liveList;

    List<String> date;

    List<Integer> count;
}
