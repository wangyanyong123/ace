package com.github.wxiaoqi.constants;

/**
 * 通用常量类
 */
public interface CommonConstant {

    /**
     * 库存管理分布锁设置常量
     */
    Integer SET_LOCK_START_INDEX = 0; // 锁设置失败，默认开始值：0
    Integer SET_LOCK_TIMES = 3; // 锁设置失败，重试次数

    /**
     * 初始化库存信息库存数量的默认值
     */
    Integer DEFAULT_STORE_NUM = 0; // 默认库存数

//    Integer UNLIMIT_STORE_NUM = -999; // 不限制库存数

}
