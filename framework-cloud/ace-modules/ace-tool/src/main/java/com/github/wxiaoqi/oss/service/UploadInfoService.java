package com.github.wxiaoqi.oss.service;

import com.github.wxiaoqi.oss.entity.UploadInfo;
import com.github.wxiaoqi.oss.mapper.UploadInfoMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.stereotype.Service;

/**
 * @Author: Lzx
 * @Description: 影像资料
 * @Date: Created in 10:21 2018/11/13
 * @Modified By:
 */
@Service
public class UploadInfoService extends BusinessBiz<UploadInfoMapper,UploadInfo> {
}
