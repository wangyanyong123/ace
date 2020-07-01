package com.github.wxiaoqi.oss.cloud;


import com.github.wxiaoqi.config.CloudStorageConfig;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云存储
 *
 * @author ace
 */
public class QcloudCloudStorageService extends CloudStorageService {
    private COSClient client;

    public QcloudCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        init();
    }

    private void init() {
        Credentials credentials = new Credentials(config.getQcloudAppId(), config.getQcloudSecretId(),
                config.getQcloudSecretKey());

        //初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(config.getQcloudRegion());

        client = new COSClient(clientConfig, credentials);
    }

    @Override
    public String upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        //上传到腾讯云
        UploadFileRequest request = new UploadFileRequest(config.getQcloudBucketName(), path, data);
        String response = client.uploadFile(request);
        JSONObject jsonObject = JSONObject.fromObject(response);
        if (jsonObject.getInt("code") != 0) {
            throw new BusinessException("文件上传失败，" + jsonObject.getString("message"));
        }

        return config.getQcloudDomain() + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new BusinessException("上传文件失败");
        }
    }

    @Override
    public String uploadSuffixByDesignatedDir(byte[] data, String suffix, String dir) {
        String prefix = config.getQcloudPrefix();
        if (StringUtils.isNotEmpty(dir)) {
            prefix = prefix + "/" + dir;
        }
        return upload(data, getPath(prefix, suffix));
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getQcloudPrefix(), suffix));
    }

    /**
     * @param fileName
     * @param oldPath
     * @param newPath
     * @return 返回http地址
     */
    @Override
    public void movePath(String fileName, String oldPath, String newPath) {

    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQcloudPrefix(), suffix));
    }

    @Override
    public String uploadTextSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix() + "/" + config.getAliyunTextImgPath(), suffix));
    }
}
