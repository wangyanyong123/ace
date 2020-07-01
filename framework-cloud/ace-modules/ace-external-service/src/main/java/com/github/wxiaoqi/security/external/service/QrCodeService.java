package com.github.wxiaoqi.security.external.service;

import com.github.wxiaoqi.security.external.entity.BizQr;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:51 2019/1/3
 * @Modified By:
 */
public interface QrCodeService {
	BizQr getQrCodeInfo(String token);
}
