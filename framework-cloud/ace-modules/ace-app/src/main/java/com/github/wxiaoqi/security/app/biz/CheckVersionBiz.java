package com.github.wxiaoqi.security.app.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.app.config.VersionConfig;
import com.github.wxiaoqi.security.app.vo.version.Client;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckVersionBiz {

    @Autowired
    private VersionConfig versionConfig;



    /**
     * 检查是否需要更新
     * @param client
     * Code：000 不需要更新
     * Code：001 强制更新
     * Code: 002 选择更新
     * @return
     */

    public ObjectRestResponse checkVersion(Client client) {
        ObjectRestResponse msg = new ObjectRestResponse();

        Config config = ConfigService.getConfig("ace-app-version");
        String url = config.getProperty(VersionConfig.ApolloKey.DOWNLOAD_URL.toString(), "");
        if(Client.ClientType.ANDROID_JINXIAOMAO.toString().equals(client.getcType())){//【android 金小茂】
            url = url +  "?cType=" + Client.ClientType.ANDROID_JINXIAOMAO.toString();
        }else if(Client.ClientType.ANDROID_HOME.toString().equals(client.getcType())){//【android 回家】
            url = url +  "?cType=" + Client.ClientType.ANDROID_HOME.toString();
        }else if(Client.ClientType.IOS_JINXIAOMAO.toString().equals(client.getcType())){//【ios 金小茂】

        }else if(Client.ClientType.IOS_HOME.toString().equals(client.getcType())){//【ios 回家】

        }

        msg = getLastVersion(client);

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
        // C1-IOS回家C2-IOS金小茂C3-安卓回家C4-安卓金小茂
        //获取Apollo配置
        Config config = ConfigService.getConfig("ace-app-version");
        if (Client.ClientType.ANDROID_JINXIAOMAO.toString().equals(client.getcType())) {//【android 金小茂】
//            lastVersion = versionConfig.getAnJinLastVersion();
//            lowVersion = versionConfig.getAnJinLowVersion();
//            updateContent = versionConfig.getAnJinUpdateContent();
            lastVersion = config.getProperty(VersionConfig.ApolloKey.AND_JIN_LAST.toString(),"");
            lowVersion = config.getProperty(VersionConfig.ApolloKey.AND_JIN_LOW.toString(),"");
            updateContent = config.getProperty(VersionConfig.ApolloKey.AND_JIN_UPDATE.toString(),"").split(",");
        } else if (Client.ClientType.ANDROID_HOME.toString().equals(client.getcType())) {//【android 回家】
//            lastVersion = versionConfig.getAnHomeLastVersion();
//            lowVersion = versionConfig.getAnHomeLowVersion();
//            updateContent = versionConfig.getAnHomeUpdateContent();
            lastVersion = config.getProperty(VersionConfig.ApolloKey.AND_HOME_LAST.toString(),"");
            lowVersion = config.getProperty(VersionConfig.ApolloKey.AND_HOME_LOW.toString(),"");
            updateContent = config.getProperty(VersionConfig.ApolloKey.AND_HOME_UPDATE.toString(),"").split(",");
        } else if (Client.ClientType.IOS_JINXIAOMAO.toString().equals(client.getcType())) {//【ios 金小茂】
//            lastVersion = versionConfig.getIosJinLastVersion();
//            lowVersion = versionConfig.getIosJinLowVersion();
//            updateContent = versionConfig.getIosJinUpdateContent();
            lastVersion = config.getProperty(VersionConfig.ApolloKey.IOS_JIN_LAST.toString(),"");
            lowVersion = config.getProperty(VersionConfig.ApolloKey.IOS_JIN_LOW.toString(),"");
            updateContent = config.getProperty(VersionConfig.ApolloKey.IOS_JIN_UPDATE.toString(),"").split(",");
        } else if (Client.ClientType.IOS_HOME.toString().equals(client.getcType())) {//【ios 回家】
//            lastVersion = versionConfig.getIosHomeLastVersion();
//            lowVersion = versionConfig.getIosHomeLowVersion();
//            updateContent = versionConfig.getIosHomeUpdateContent();
            lastVersion = config.getProperty(VersionConfig.ApolloKey.IOS_HOME_LAST.toString(),"");
            lowVersion = config.getProperty(VersionConfig.ApolloKey.IOS_HOME_LOW.toString(),"");
            updateContent = config.getProperty(VersionConfig.ApolloKey.IOS_HOME_UPDATE.toString(),"").split(",");
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
