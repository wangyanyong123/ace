package com.github.wxiaoqi.security.schedulewo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:37 2019/3/7
 * @Modified By:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wo")
public class WoConfig {
	private int outtime;
	private int missedorder;
	private int unconfirmed;
	private String missedorderbusid;
	private String unconfirmedbusid;
}
