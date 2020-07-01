package com.github.wxiaoqi.oss.service;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.config.CloudStorageConfig;
import com.github.wxiaoqi.oss.cloud.OSSFactory;
import com.github.wxiaoqi.oss.entity.FileInfo;
import com.github.wxiaoqi.oss.entity.UploadInfo;
import com.github.wxiaoqi.oss.mapper.FileInfoMapper;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件信息
 *
 * @author zxl
 * @Date 2018-12-03 13:59:20
 */
@Service
public class FileInfoBiz extends BusinessBiz<FileInfoMapper,FileInfo> {
	@Autowired
	private UploadInfoService uploadInfoService;
	@Autowired
	private CloudStorageConfig config;
	@Autowired
	private OSSFactory ossFactory;

	public ObjectRestResponse<String> movePath(String url, String dirPath) {
		ObjectRestResponse<String> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(url)) {
			response.setStatus(501);
			response.setMessage("url路径不能为空！");
			return response;
		}
//		https://ascendas.oss-cn-shanghai.aliyuncs.com/jinmao/temp/20181130/7bd061f6a833439f95cb897a3add7226.jpg

		UploadInfo param = new UploadInfo();
		param.setNatrualPath(url);
		param.setIsMove("0");
		UploadInfo uploadInfo = uploadInfoService.selectOne(param);
		if(null == uploadInfo || StringUtils.isEmpty(uploadInfo.getId())){
			response.setStatus(502);
			response.setMessage("url错误！");
			return response;
		}

		if (StringUtils.isEmpty(dirPath)){
			response.setStatus(503);
			response.setMessage("dirPath不能为空！");
			return response;
		}else {
			if (dirPath.startsWith("/")) {
				dirPath = dirPath.substring(1);
			}
			if (dirPath.endsWith("/")) {
				dirPath = dirPath.substring(0,dirPath.length() - 1);
			}
		}
		String domainUrl = "";
		Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		while(matcher.find()){
			domainUrl = matcher.group();
		}
		String ss = url.substring(url.indexOf(domainUrl)+domainUrl.length()+1);
		String oldPath = ss.substring(0,ss.lastIndexOf('/'));
		String data = oldPath.substring(oldPath.lastIndexOf('/'));
		ossFactory.build().movePath(uploadInfo.getRealFileName(), oldPath+"/", config.getAliyunPrefix()+"/"+dirPath+data+"/");
		String newUrl = url.replaceAll(oldPath,config.getAliyunPrefix()+"/"+dirPath+data);
		uploadInfo.setIsMove("1");
		uploadInfo.setUpdTime(new Date());
		uploadInfo.setUpdUserId(BaseContextHandler.getUserID());
		uploadInfoService.updateSelectiveById(uploadInfo);
		FileInfo fileInfo = new FileInfo();
		BeanUtils.copyProperties(uploadInfo,fileInfo);
//		fileInfo.setId(UUIDUtils.generateUuid());
		fileInfo.setNatrualPath(newUrl);
		this.insertSelective(fileInfo);
		return new ObjectRestResponse<>().data(newUrl);
	}

	public ObjectRestResponse<String> moveAppUploadPath(String url, String dirPath) {
		ObjectRestResponse<String> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(url)) {
			response.setStatus(511);
			response.setMessage("url路径不能为空！");
			return response;
		}

		if (StringUtils.isEmpty(dirPath)){
			response.setStatus(513);
			response.setMessage("dirPath不能为空！");
			return response;
		}else {
			if (dirPath.startsWith("/")) {
				dirPath = dirPath.substring(1);
			}
			if (dirPath.endsWith("/")) {
				dirPath = dirPath.substring(0,dirPath.length() - 1);
			}
		}
		String domainUrl = "";
		Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		while(matcher.find()){
			domainUrl = matcher.group();
		}
		String ss = url.substring(url.indexOf(domainUrl)+domainUrl.length()+1);
		String oldPath = ss.substring(0,ss.lastIndexOf('/'));
		String data = oldPath.substring(oldPath.lastIndexOf('/'));
		String realFileName = url.substring(url.lastIndexOf('/') + 1);
		ossFactory.build().movePath(realFileName, oldPath+"/", config.getAliyunPrefix()+"/"+dirPath+data+"/");
		String newUrl = url.replaceAll(oldPath,config.getAliyunPrefix()+"/"+ dirPath+data);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(UUIDUtils.generateUuid());
		fileInfo.setNatrualPath(newUrl);
		fileInfo.setSystem("2");
		fileInfo.setStatus("1");
		fileInfo.setLoadstatus("2");
		fileInfo.setCrtTime(new Date());
		fileInfo.setStorageType("1");
		this.insertSelective(fileInfo);
		return new ObjectRestResponse<>().data(newUrl);
	}
}