package com.github.wxiaoqi.security.im.tio.pojo;

import lombok.Data;

@Data
public class SingleChatBody {

    private String msgType;
    private String message;
    private Long fromUser;
    private Long toUser;

}
