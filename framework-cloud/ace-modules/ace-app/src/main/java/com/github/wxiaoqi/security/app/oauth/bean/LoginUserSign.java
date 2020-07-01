package com.github.wxiaoqi.security.app.oauth.bean;

import lombok.Data;
import org.springframework.util.Assert;

/**
 * @author: guohao
 * @create: 2020-04-29 14:11
 **/
@Data
public class LoginUserSign {

    public final static String CLIENT_USER_SIGN = "c";
    public final static String SERVER_USER_SIGN = "s";

    private String mobilePhone;

    private String userSign;

    public static LoginUserSign buildLoginUserSign(String userName){
        Assert.hasLength(userName,"手机号不能为空");
        String[] infos = userName.split("#");
        Assert.isTrue(infos.length == 2,"手机号不存在");
        LoginUserSign loginUserSign = new LoginUserSign();
        loginUserSign.setMobilePhone(infos[0]);
        loginUserSign.setUserSign(infos[1]);
        return loginUserSign;
    }
}
