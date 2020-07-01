package com.github.wxiaoqi.security.plan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:55 2019/2/28
 * @Modified By:
 */
@Configuration
@ConfigurationProperties(prefix="planwo")
@Data
public class PlanWoConfig {
	private List<String> projectcode;
}
