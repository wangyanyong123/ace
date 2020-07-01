package com.github.wxiaoqi.pay.ali.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayClientInitConfig {
    private String gateWay;
    private String appId;
    private String privateKey;
    private String appCertPath;
    private String alipayCertPath;
    private String alipayRootCertPath;

    private String charset;
    private String signType;
    private String notifyUrl;

    /**
     * 开放平台 SDK 封装了签名实现，只需在创建 DefaultAlipayClient 对象时，
     * 设置请求网关 (gateway)，
     * 应用 id (app_id)，
     * 应用私钥 (private_key)，
     * 应用公钥证书路径（app_cert_path 文件绝对路径），
     * 支付宝公钥证书文件路径（alipay_cert_path 文件绝对路径），
     * 支付宝CA根证书文件路径（alipay_root_cert_path 文件绝对路径），
     * 编码格式 （charset），
     * 签名类型 （sign_type）
     * 即可，报文请求时会自动进行签名。
     */
    @Bean
    public DefaultAlipayClient alipayClient() throws AlipayApiException, FileNotFoundException {
        //构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(gateWay);
        certAlipayRequest.setAppId(appId);
        certAlipayRequest.setPrivateKey(privateKey);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(charset);
        certAlipayRequest.setSignType(signType);
        certAlipayRequest.setCertPath( ResourceUtils.getFile(appCertPath).getAbsolutePath());
        certAlipayRequest.setAlipayPublicCertPath(ResourceUtils.getFile(alipayCertPath).getAbsolutePath());
        certAlipayRequest.setRootCertPath(ResourceUtils.getFile(alipayRootCertPath).getAbsolutePath());
        return new DefaultAlipayClient(certAlipayRequest);
    }
}
