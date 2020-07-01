package com.github.wxiaoqi.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CopyObjectResult;
import com.github.wxiaoqi.config.CloudStorageConfig;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 *
 * @author ace
 */
@Slf4j
public class AliyunCloudStorageService extends CloudStorageService {
    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        init();
    }

    private void init() {
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e) {
            throw new BusinessException("上传文件失败，请检查配置信息");
        }

        return config.getAliyunDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix() + "/" + config.getAliyunPath(), suffix));
    }

    /**
     * 上传到指定文件夹，dir替换 阿波罗 AliyunPath
     *
     * @param data   :  文件
     * @param suffix : 后缀
     * @param dir    : 指定文件夹
     * @return java.lang.String
     * @throws
     * @Author guohao
     * @Date 2020/4/15 11:59
     */
    @Override
    public String uploadSuffixByDesignatedDir(byte[] data, String suffix, String dir) {
        return upload(data, getPath(config.getAliyunPrefix() + "/" + dir, suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String uploadTextSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix() + "/" + config.getAliyunTextImgPath(), suffix));
    }

    @Override
    public void movePath(String fileName, String oldPath, String newPath) {

        if(client.doesObjectExist(config.getAliyunBucketName(),oldPath+fileName)) {
            CopyObjectResult result = client.copyObject(config.getAliyunBucketName(),oldPath + fileName,
                    config.getAliyunBucketName(),newPath + fileName);
            log.info("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
        }else{
            log.info("文件不存在"+config.getAliyunBucketName()+oldPath+fileName);
        }
    }
}
