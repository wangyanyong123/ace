package com.github.wxiaoqi.security.im.tio;

import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.im.tio.handler.SingleChatProcesser;
import com.github.wxiaoqi.security.im.tio.handler.WebSocketProcesser;
import com.github.wxiaoqi.security.im.tio.handler.WebSocketTextType;
import com.github.wxiaoqi.security.im.util.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.utils.json.Json;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.HashMap;


public class WebSocketHandler implements IWsMsgHandler {
	private static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    private static HashMap<String,WebSocketProcesser> proccessMap=new HashMap<String,WebSocketProcesser>();

    static{
        proccessMap.put("single",new SingleChatProcesser());
    }

    private static WebSocketProcesser getProcesser(String type){
        WebSocketProcesser processer = proccessMap.get(type);
        return processer;
    }

    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        return handleHandshakeUserInfo(httpRequest,httpResponse,channelContext);
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
//        //绑定到群组，后面会有群发
//        Tio.bindGroup(channelContext, Const.GROUP_ID);
//        int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();
//
//        String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + count + "】人在线" + "'}";
//        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
//        WsResponse wsResponse = WsResponse.fromText(msg, "utf-8");
//        //群发
//        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);

    }

    /**
     *
     * 解析客户端的token，获取用户信息转化为ContextUser对象
     * 将ContextUser对象，以UserId为Key，ContextUser为value存入ChannelContext的Attribute中，方便后续使用当前用户信息
     * 获取用户群组，遍历调用 Aio.bindGroup方法加入群组
     * */
    private HttpResponse handleHandshakeUserInfo(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws  Exception {
        //增加token验证方法
        String token = httpRequest.getRequestLine().getPath().replaceAll("/","");
        if (StringUtils.isEmpty(token)) {
            //没有token 未授权
            httpResponse.setStatus(HttpResponseStatus.C401);
        } else {
            //解析token
//            RedisUtil redisUtil = SpringUtil.getBean("redisUtil", RedisUtil.class);
			UserAuthUtil userAuthUtil = BeanUtils.getBean(UserAuthUtil.class);
			IJWTInfo userInfo = userAuthUtil.getInfoFromToken(token);

//            String userJson = (String) redisUtil.get(token);
//            User user = Json.toBean(userJson, User.class);
            if (userInfo == null) {
                //没有找到用户
                httpResponse.setStatus(HttpResponseStatus.C404);
            } else {
                channelContext.setAttribute(String.valueOf(userInfo.getId()),userInfo);
                //绑定用户ID
                Tio.bindUser(channelContext, String.valueOf(userInfo.getId()));
                log.info("收到来自{}的ws握手包\r\n{}", userInfo.getId() + ":" + httpRequest.getClientIp(), httpRequest.toString());

            }
        }
//		httpResponse.setStatus(HttpResponseStatus.C200);
        return httpResponse;
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return "暂不支持字节消息解析";
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    /**

     * 字符消息（binaryType = blob）过来后会走这个方法

     */
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
    	log.info("text:{}"+text);
        try{
            WebSocketTextType websocketTextType = Json.toBean(text,WebSocketTextType.class);
            //拿到对应的消息处理器
            WebSocketProcesser processer = getProcesser(websocketTextType.getType());
            //处理消息
            processer.process(text, channelContext);

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return WsResponse.fromText(Json.toJson(ReturnResult.build(400,"发送失败")),"utf-8");
        }

    }
}
