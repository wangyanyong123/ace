package com.github.wxiaoqi.security.app.axb.util;

import com.baidubce.BceClientException;
import com.baidubce.auth.SignOptions;
import com.baidubce.http.Headers;
import com.baidubce.http.HttpMethodName;
import com.baidubce.internal.InternalRequest;
import com.baidubce.util.DateUtils;
import com.baidubce.util.HttpUtils;
import com.github.wxiaoqi.security.common.util.HttpClientUtil;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * AXB工具
 */
@Component
public class AXBUtils {

    private static final Logger logger = LoggerFactory.getLogger(AXBUtils.class);

    private static final String BCE_AUTH_VERSION = "bce-auth-v1";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);

    // Default headers to sign with the BCE signing protocol.
    private static final Set<String> defaultHeadersToSign = Sets.newHashSet();
    private static final Joiner headerJoiner = Joiner.on('\n');
    private static final Joiner signedHeaderStringJoiner = Joiner.on(';');

    @Value("${pns.ak}")
    private String accessKeyId;
    @Value("${pns.sk}")
    private String secretAccessKey;



    static {
        AXBUtils.defaultHeadersToSign.add(Headers.HOST.toLowerCase());
        AXBUtils.defaultHeadersToSign.add(Headers.CONTENT_LENGTH.toLowerCase());
        AXBUtils.defaultHeadersToSign.add(Headers.CONTENT_TYPE.toLowerCase());
        AXBUtils.defaultHeadersToSign.add(Headers.CONTENT_MD5.toLowerCase());
    }


    /**
     * 无参数接口调用
     * @param url 接口地址
     * @return 响应参数
     */
    public String execute(String url){
        return  this.execute(url,null);
    }

    /**
     * 带参数接口调用
     * @param url 接口地址
     * @param obj 参数
     * @return 响应参数
     */
    public String execute(String url,Object obj){
        String timestamp = DateUtils.formatAlternateIso8601Date(new Date());
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<String,String>();
        headers.put(Headers.HOST,HttpUtils.generateHostHeader(uri));
        headers.put(Headers.CONTENT_TYPE,"application/json; charset=utf-8");
        headers.put(Headers.BCE_DATE, timestamp);
        headers.put("Authorization",this.sign(uri,timestamp));

        String reponse = null;
        try {
            String jsonObj = "";
            if(obj != null){
                jsonObj = JacksonJsonUtil.beanToJson(obj);
            }
            logger.info("obj=="+JacksonJsonUtil.beanToJson(obj));
            reponse = HttpClientUtil.sentPostHttpNew(url,headers, jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  reponse;
    }

    /**
     * 权限认证签名
     * @param uri 接口地址
     * @return 签名
     */
    private String sign(URI uri,String timestamp) {
        SignOptions options = SignOptions.DEFAULT;
        Set<String> headerssign = Sets.newHashSet();
        headerssign.add(Headers.HOST);
        headerssign.add(Headers.CONTENT_TYPE);
        headerssign.add(Headers.BCE_DATE);
        options.setHeadersToSign(headerssign);
        InternalRequest request = new InternalRequest(HttpMethodName.POST,uri);
        request.setSignOptions(options);
        request.addHeader(Headers.CONTENT_TYPE,"application/json; charset=utf-8");
        request.addHeader(Headers.BCE_DATE, timestamp);
        request.addHeader(Headers.HOST, HttpUtils.generateHostHeader(request.getUri()));

        // 前缀字符串
        String authString =
                AXBUtils.BCE_AUTH_VERSION + "/" + accessKeyId + "/" + timestamp + "/" + options.getExpirationInSeconds();

        //派生签名密钥
        String signingKey = this.sha256Hex(secretAccessKey, authString);
        // 请求地址URI
        String canonicalURI = this.getCanonicalURIPath(request.getUri().getPath());
        // 请求地址是否包含参数
        String canonicalQueryString = HttpUtils.getCanonicalQueryString(request.getParameters(), true);
        // Sorted the headers should be signed from the request.
        SortedMap<String, String> headersToSign =
                this.getHeadersToSign(request.getHeaders(), options.getHeadersToSign());
        // Formatting the headers from the request based on signing protocol.
        String canonicalHeader = this.getCanonicalHeaders(headersToSign);
        //签名头域
        String signedHeaders = "";
        if (options.getHeadersToSign() != null) {
            signedHeaders = AXBUtils.signedHeaderStringJoiner.join(headersToSign.keySet());
            signedHeaders = signedHeaders.trim().toLowerCase();
        }

        // 规范请求
        String canonicalRequest =
                request.getHttpMethod() + "\n" + canonicalURI + "\n" + canonicalQueryString + "\n" + canonicalHeader;

        // Signing the canonical request using key with sha-256 algorithm.
        String signature = this.sha256Hex(signingKey, canonicalRequest);

        String authorizationHeader = authString + "/" + signedHeaders + "/" + signature;

        logger.debug("CanonicalRequest:{}\tAuthorization:{}", canonicalRequest.replace("\n", "[\\n]"),
                authorizationHeader);
        return authorizationHeader;
//        request.addHeader(Headers.AUTHORIZATION, authorizationHeader);
    }

    private String getCanonicalURIPath(String path) {
        if (path == null) {
            return "/";
        } else if (path.startsWith("/")) {
            return HttpUtils.normalizePath(path);
        } else {
            return "/" + HttpUtils.normalizePath(path);
        }
    }

    private String getCanonicalHeaders(SortedMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        }

        List<String> headerStrings = Lists.newArrayList();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            headerStrings.add(HttpUtils.normalize(key.trim().toLowerCase()) + ':' + HttpUtils.normalize(value.trim()));
        }
        Collections.sort(headerStrings);

        return headerJoiner.join(headerStrings);
    }

    private SortedMap<String, String> getHeadersToSign(Map<String, String> headers, Set<String> headersToSign) {
        SortedMap<String, String> ret = Maps.newTreeMap();
        if (headersToSign != null) {
            Set<String> tempSet = Sets.newHashSet();
            for (String header : headersToSign) {
                tempSet.add(header.trim().toLowerCase());
            }
            headersToSign = tempSet;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                if ((headersToSign == null && this.isDefaultHeaderToSign(key))
                        || (headersToSign != null && headersToSign.contains(key.toLowerCase())
                        && !Headers.AUTHORIZATION.equalsIgnoreCase(key))) {
                    ret.put(key, entry.getValue());
                }
            }
        }
        return ret;
    }

    private boolean isDefaultHeaderToSign(String header) {
        header = header.trim().toLowerCase();
        return header.startsWith(Headers.BCE_PREFIX) || defaultHeadersToSign.contains(header);
    }

    private String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
            return new String(Hex.encodeHex(mac.doFinal(stringToSign.getBytes(UTF8))));
        } catch (Exception e) {
            throw new BceClientException("Fail to generate the signature", e);
        }
    }

}