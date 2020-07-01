package com.github.wxiaoqi.security.jinmao.task.util;

import com.github.wxiaoqi.security.jinmao.entity.BaseTenant;
import com.github.wxiaoqi.security.jinmao.entity.BaseUser;
import lombok.Data;

import java.util.List;

/**
 * @author liuam
 * @date 2019-08-16 14:46
 */
@Data
public class SyncBusinessUserParams {
    private BaseUser baseUser;

    private BaseTenant baseTenant;
}
