package com.github.wxiaoqi.security.jinmao.task.util;

import com.github.wxiaoqi.security.jinmao.entity.BaseAppHousekeeperArea;
import com.github.wxiaoqi.security.jinmao.entity.BaseAppServerUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liuam
 * @date 2019-08-15 19:27
 */
@Data
@Accessors(chain = true)
public class SyncHouseKeeperParams {

    private BaseAppServerUser baseAppServerUser;

    private List<BaseAppHousekeeperArea> baseAppHousekeeperAreas;

}
