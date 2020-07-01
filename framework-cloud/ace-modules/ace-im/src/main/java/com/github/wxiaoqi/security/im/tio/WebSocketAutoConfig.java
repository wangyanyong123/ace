package com.github.wxiaoqi.security.im.tio;

import com.github.wxiaoqi.security.im.config.ImConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerConfig;


@Configuration
@EnableConfigurationProperties(ImConfig.class)
public class WebSocketAutoConfig {

    @Autowired
    private ImConfig imConfig;

    @Bean
    WebSocketStarter websocketStarter() throws Exception{

        WsServerConfig wsServerConfig=new WsServerConfig(Integer.valueOf(imConfig.getTioServerPort()));
        wsServerConfig.setBindIp(imConfig.getTioServerIp());

        WebSocketStarter websocketStarter = new WebSocketStarter(wsServerConfig);

        ServerGroupContext serverGroupContext = websocketStarter.getServerGroupContext();

        serverGroupContext.setHeartbeatTimeout(imConfig.getTioTimeout());

        //启动程序
        websocketStarter.start();
        //返回
        return websocketStarter;
    }
}
