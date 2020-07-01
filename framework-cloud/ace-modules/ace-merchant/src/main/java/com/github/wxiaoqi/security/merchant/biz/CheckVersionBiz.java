package com.github.wxiaoqi.security.merchant.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.VersionUtil;
import com.github.wxiaoqi.security.merchant.config.VersionConfig;
import com.github.wxiaoqi.security.merchant.vo.version.Client;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckVersionBiz {

    /**
     * 检查是否需要更新
     * @param client
     * Code：000 不需要更新
     * Code：001 强制更新
     * Code: 002 选择更新
     * @return
     */

    public ObjectRestResponse checkVersion(Client client) {

        Config config = ConfigService.getConfig("ace-app-version");
        String url = config.getProperty(VersionConfig.ApolloKey.DOWNLOAD_URL.toString(), "");
        if(Client.ClientType.ANDROID_MERCHANT.equals(client.getcType())){//【android】
            url = url +  "?cType=" + Client.ClientType.ANDROID_MERCHANT.toString();
        } else if(Client.ClientType.IOS_MERCHANT.equals(client.getcType())){//【ios】
        }

        ObjectRestResponse msg = getLastVersion(client);

        Map<String, Object> data = new HashMap<String, Object>();
        if(0== msg.getStatus()){
            data.put("url", url);
            data.put("isUpdate", "000");
            data.put("updateContent", msg.getData());
            data.put("lastVersion",msg.getMessage());
        } else if (1 == msg.getStatus()) {
            data.put("url", url);
            data.put("isUpdate", "001");
            data.put("updateContent", msg.getData()); //更新内容
            data.put("lastVersion",msg.getMessage());
        } else if (2 == msg.getStatus()) {
            data.put("url", url);
            data.put("isUpdate", "002");
            data.put("updateContent", msg.getData()); //更新内容
            data.put("lastVersion",msg.getMessage());
        }

        return new ObjectRestResponse().ok(data);

    }

    /**
     * 获取最新版本检查是否需要更新
     * @param client
     * @return
     */
    private  ObjectRestResponse getLastVersion(Client client) {
        ObjectRestResponse response = new ObjectRestResponse();
        String currVersion = client.getcVersion();
        String lastVersion = "";
        String lowVersion = "";
        String[] updateContent = {};
        //获取Apollo配置
        Config config = ConfigService.getConfig("ace-app-version");
        if (Client.ClientType.ANDROID_MERCHANT.toString().equals(client.getcType())) {//【android 金小茂】
            lastVersion = config.getProperty(VersionConfig.ApolloKey.AND_MERCHANT_LAST.toString(),"");
            lowVersion = config.getProperty(VersionConfig.ApolloKey.AND_MERCHANT_LOW.toString(),"");
            updateContent = config.getProperty(VersionConfig.ApolloKey.AND_MERCHANT_UPDATE.toString(),"").split(",");
        } else if (Client.ClientType.IOS_MERCHANT.toString().equals(client.getcType())) {//【android 回家】
            lastVersion = config.getProperty(VersionConfig.ApolloKey.IOS_MERCHANT_LAST.toString(),"");
            lowVersion = config.getProperty(VersionConfig.ApolloKey.IOS_MERCHANT_LOW.toString(),"");
            updateContent = config.getProperty(VersionConfig.ApolloKey.IOS_MERCHANT_UPDATE.toString(),"").split(",");
        }

        String code = "000"; //不需要更新
        try {
            if (VersionUtil.compareVersion(currVersion, lowVersion) < 0) {
                code = "001";//强制更新
            }
            if (VersionUtil.compareVersion(currVersion, lowVersion) >= 0 && VersionUtil.compareVersion(currVersion, lastVersion) < 0) {
                code = "002";//选择更新
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("版本比较异常");
            return response;
        }
        response.setStatus(Integer.parseInt(code));
        response.setMessage(lastVersion);
        if ("001".equals(code) || "002".equals(code)) {
            response.setData(updateContent);
        }
        return response;
    }


}
