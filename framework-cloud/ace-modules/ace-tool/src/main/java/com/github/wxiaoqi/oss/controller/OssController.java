package com.github.wxiaoqi.oss.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.config.CloudStorageConfig;
import com.github.wxiaoqi.oss.cloud.OSSFactory;
import com.github.wxiaoqi.oss.entity.UploadInfo;
import com.github.wxiaoqi.oss.service.FileInfoBiz;
import com.github.wxiaoqi.oss.service.UploadInfoService;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.api.vo.order.in.FileListVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.BASE64ImgUtil;
import com.github.wxiaoqi.security.common.util.ImportUtil;
import com.github.wxiaoqi.security.common.util.QRCodeFactory;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import io.swagger.annotations.*;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 文件上传
 *
 * @author ace
 */
@RestController
@RequestMapping("/oss")
@Api(tags="oss上传文件工具接口")
public class OssController {
    @Autowired
    private OSSFactory ossFactory;

    @Autowired
    private UploadInfoService uploadInfoService;

    @Autowired
    private FileInfoBiz fileInfoBiz;
    @Autowired
    private CloudStorageConfig config;
    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @CheckUserToken
    @ApiOperation(value = "上传文件", notes = "上传文件",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="文件类型:1、图片,2、文件,3、视频",dataType="String",required = true ,paramType = "query",example="4"),
            @ApiImplicitParam(name="fileDesc",value="图片描述",dataType="String",required = false ,paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="system",value="来源系统 1:客户APP  2：服务APP  3;web端   4 服务端",dataType="String",required = true ,paramType = "query",example="4"),
            @ApiImplicitParam(name="targetDir",value="指定文件夹 {工单订单类：orderwo ；家里人广告：webposts ; " +
                    "app家里人,议事厅:appposts; 商城管理：shop; 物业服务:property; 业主圈：ownercircle；app广告页: system" +
                    "商户管理: merchant}",dataType="String",required = true ,paramType = "query",example="orderwo")
    })
    public ObjectRestResponse<String> upload(@ApiParam(value="上传的文件",required=true)@RequestParam("file") MultipartFile file, @RequestParam("type") String type,
											 @RequestParam("fileDesc") String fileDesc, @RequestParam("system") String system
                                             ,String targetDir) throws Exception {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        UploadInfo uploadInfo = new UploadInfo();
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if(".undefined".equals(suffix) && "undefined".equals(fileType)){
            String t = file.getContentType();
            String tp = t.split("/")[1];
            if(StringUtils.isNotEmpty(tp)){
            	suffix = "."+ tp;
            	fileType = tp;
			}
        }
        Long size = file.getSize();
        String originalFilename = file.getOriginalFilename();
        String userId = BaseContextHandler.getUserID();

        uploadInfo.setId(UUIDUtils.generateUuid());
        uploadInfo.setType(type);
        uploadInfo.setFileType(fileType);
        uploadInfo.setSize(size);
        uploadInfo.setFileName(originalFilename);
        uploadInfo.setFileDesc(fileDesc);
        uploadInfo.setSystem(system);
        uploadInfo.setStatus("1");
        uploadInfo.setLoadstatus("1");
        uploadInfo.setCrtTime(new Date());
        uploadInfo.setCrtUserId(userId);
        uploadInfo.setStorageType("1");
        uploadInfo.setIsMove("0");
//        uploadInfo.setRealFileName();
//        uploadInfo.setNatrualPath();
//        uploadInfo.setLargePath();
//        uploadInfo.setMediumPath();
//        uploadInfo.setThumbnailPath();
//        uploadInfo.setUpdTime();
        uploadInfoService.insertSelective(uploadInfo);
        String url;
        if (StringUtils.isNotEmpty(targetDir)) {
            url = ossFactory.build().uploadSuffixByDesignatedDir(file.getBytes(), suffix, targetDir);
        } else {
            url = ossFactory.build().uploadSuffix(file.getBytes(), suffix);
        }
        if (StringUtils.isNoneBlank(url)){
            uploadInfo.setRealFileName(url.substring(url.lastIndexOf('/') + 1));
            uploadInfo.setNatrualPath(url);
            uploadInfo.setLoadstatus("2");
            uploadInfo.setUpdTime(new Date());
        }else {
            uploadInfo.setLoadstatus("3");
            uploadInfo.setUpdTime(new Date());
        }
        uploadInfoService.updateSelectiveById(uploadInfo);
        return new ObjectRestResponse<>().data(url);
    }

    /**
     * 富文本编辑器上传文件
     */
    @PostMapping("/uploadText")
    @CheckUserToken
    @ApiOperation(value = "富文本编辑器上传文件", notes = "富文本编辑器上传文件",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="文件类型:1、图片,2、文件,3、视频",dataType="String",required = true ,paramType = "query",example="4"),
            @ApiImplicitParam(name="fileDesc",value="图片描述",dataType="String",required = false ,paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="system",value="来源系统 1:客户APP  2：服务APP  3;web端   4 服务端",dataType="String",required = true ,paramType = "query",example="4")
    })
    public ObjectRestResponse<String> uploadText(@ApiParam(value="上传的文件",required=true)@RequestParam("file") MultipartFile file, @RequestParam("type") String type,
                                             @RequestParam("fileDesc") String fileDesc, @RequestParam("system") String system) throws Exception {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        UploadInfo uploadInfo = new UploadInfo();
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if(".undefined".equals(suffix) && "undefined".equals(fileType)){
            String t = file.getContentType();
            String tp = t.split("/")[1];
            if(StringUtils.isNotEmpty(tp)){
                suffix = "."+ tp;
                fileType = tp;
            }
        }
        Long size = file.getSize();
        String originalFilename = file.getOriginalFilename();
        String userId = BaseContextHandler.getUserID();

        uploadInfo.setId(UUIDUtils.generateUuid());
        uploadInfo.setType(type);
        uploadInfo.setFileType(fileType);
        uploadInfo.setSize(size);
        uploadInfo.setFileName(originalFilename);
        uploadInfo.setFileDesc(fileDesc);
        uploadInfo.setSystem(system);
        uploadInfo.setStatus("1");
        uploadInfo.setLoadstatus("1");
        uploadInfo.setCrtTime(new Date());
        uploadInfo.setCrtUserId(userId);
        uploadInfo.setStorageType("1");
        uploadInfo.setIsMove("0");
//        uploadInfo.setRealFileName();
//        uploadInfo.setNatrualPath();
//        uploadInfo.setLargePath();
//        uploadInfo.setMediumPath();
//        uploadInfo.setThumbnailPath();
//        uploadInfo.setUpdTime();
        uploadInfoService.insertSelective(uploadInfo);
        String url = ossFactory.build().uploadTextSuffix(file.getBytes(), suffix);
        if (StringUtils.isNoneBlank(url)){
            uploadInfo.setRealFileName(url.substring(url.lastIndexOf('/') + 1));
            uploadInfo.setNatrualPath(url);
            uploadInfo.setLoadstatus("2");
            uploadInfo.setUpdTime(new Date());
        }else {
            uploadInfo.setLoadstatus("3");
            uploadInfo.setUpdTime(new Date());
        }
        uploadInfoService.updateSelectiveById(uploadInfo);
        return new ObjectRestResponse<>().data(url);
    }



    @RequestMapping(value = "/uploadExcel",method = RequestMethod.POST)
    @CheckUserToken
    @ApiOperation(value = "上传Excel", notes = "上传Excel",httpMethod = "POST")
    public ObjectRestResponse uploadExcel(@RequestBody @ApiParam ExcelInfoVo excelInfoVo) throws Exception {
        InputStream in = ImportUtil.exportExcel(excelInfoVo.getTitles(), excelInfoVo.getKeys(), excelInfoVo.getDataList(), excelInfoVo.getFileName(), "", "");
        // 上传文件
        String folderUrl = config.getAliyunPrefix() + "/" + config.getAliyunPath() + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        String fullUrl = folderUrl + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + excelInfoVo.getFileName();
        UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.setId(UUIDUtils.generateUuid());
        uploadInfo.setType("2");
        uploadInfo.setFileType("xlsx");
        uploadInfo.setFileName(fullUrl);
        uploadInfo.setFileDesc(excelInfoVo.getFileName());
        uploadInfo.setSystem("3");
        uploadInfo.setCrtTime(new Date());
        uploadInfo.setCrtUserId(BaseContextHandler.getUserID());
        uploadInfoService.insertSelective(uploadInfo);
        String url = ossFactory.build().upload(in, fullUrl);
        if (StringUtils.isNoneBlank(url)){
            uploadInfo.setRealFileName(url.substring(url.lastIndexOf('/') + 1));
            uploadInfo.setNatrualPath(url);
            uploadInfo.setLoadstatus("2");
            uploadInfo.setUpdTime(new Date());
        }else {
            uploadInfo.setLoadstatus("3");
            uploadInfo.setUpdTime(new Date());
        }
        uploadInfoService.updateSelectiveById(uploadInfo);
        return new ObjectRestResponse<>().data(url);
    }


    @RequestMapping(value = "/uploadQRImg",method = RequestMethod.POST)
    @CheckUserToken
    @ApiOperation(value = "上传二维码", notes = "上传二维码",httpMethod = "POST")
    public ObjectRestResponse uploadQRImg(@RequestBody @ApiParam ExcelInfoVo excelInfoVo) throws Exception {
        String path = QRCodeFactory.createQrCodeImg(excelInfoVo.getContent(),excelInfoVo.getWidth(),excelInfoVo.getHeight());
        File file = new File(path);
        InputStream input = new FileInputStream(file);
        //InputStream in = ImportUtil.exportExcel(excelInfoVo.getTitles(), excelInfoVo.getKeys(), excelInfoVo.getDataList(), excelInfoVo.getFileName(), "", "");
        // 上传文件
        String folderUrl = config.getAliyunPrefix() + "/" + config.getAliyunPath() + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        String fullUrl = folderUrl + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + excelInfoVo.getFileName();
        UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.setId(UUIDUtils.generateUuid());
        uploadInfo.setType("1");
        uploadInfo.setFileType("png");
        uploadInfo.setFileName(fullUrl);
        uploadInfo.setFileDesc(excelInfoVo.getFileName());
        uploadInfo.setSystem("3");
        uploadInfo.setCrtTime(new Date());
        uploadInfo.setCrtUserId(BaseContextHandler.getUserID());
        uploadInfoService.insertSelective(uploadInfo);
        String url = ossFactory.build().upload(input, fullUrl);
        if (StringUtils.isNoneBlank(url)){
            uploadInfo.setRealFileName(url.substring(url.lastIndexOf('/') + 1));
            uploadInfo.setNatrualPath(url);
            uploadInfo.setLoadstatus("2");
            uploadInfo.setUpdTime(new Date());
        }else {
            uploadInfo.setLoadstatus("3");
            uploadInfo.setUpdTime(new Date());
        }
        uploadInfoService.updateSelectiveById(uploadInfo);
        return new ObjectRestResponse<>().data(url);
    }


    @GetMapping("/movePath")
    @CheckUserToken
    @ApiOperation(value = "移动文件到指定路径", notes = "移动文件到指定路径",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="url",value="路径",dataType="String",required = true ,paramType = "query",example="4"),
            @ApiImplicitParam(name="dirPath",value="目录",dataType="String",required = true ,paramType = "query",example="4")
    })
    public ObjectRestResponse<String> movePath(@RequestParam("url") String url, @RequestParam("dirPath") String dirPath) {
        return fileInfoBiz.movePath(url,dirPath);
    }

    @GetMapping("/movePaths")
    @CheckUserToken
    @ApiOperation(value = "移动多个文件到指定路径", notes = "移动文件到指定路径",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="urls",value="路径：List<String>转成json字符串",dataType="String",required = true ,paramType = "query",example="4"),
            @ApiImplicitParam(name="dirPath",value="目录",dataType="String",required = true ,paramType = "query",example="4")
    })
    public ObjectRestResponse<List<String>> movePaths(@RequestParam("urls") String urls, @RequestParam("dirPath") String dirPath) {
        ObjectRestResponse<List<String>> response = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(urls)) {
            response.setStatus(501);
            response.setMessage("urls！");
            return response;
        }
        List<String> list = new ArrayList<>();
        try {
            list = JSONObject.parseArray(urls,String.class);
        } catch (Exception e){
            response.setStatus(502);
            response.setMessage("urls格式错误！");
            return response;
        }
        List<String> dataList = new ArrayList<String>();
        if(null != list && list.size() > 0){
            Map<String,Object> map = new HashMap<>();
            for (String url: list) {
                ObjectRestResponse<String> res = fileInfoBiz.movePath(urls,dirPath);
                if (res.getStatus() == 200){
                    dataList.add(res.getData());
                }else {
                    response.setStatus(504);
                    response.setMessage("处理图片"+url+"出错，"+res.getMessage());
                    return response;
                }
            }
            response.setData(dataList);
        }else {
            response.setStatus(503);
            response.setMessage("urls错误！");
            return response;
        }
        return response;
    }

    @GetMapping("/moveUrlPaths")
    @ApiOperation(value = "移动多个文件到指定路径", notes = "移动文件到指定路径",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="urls",value="路径：多个文件用逗号(,)分隔",dataType="String",required = true ,paramType = "query",example="4"),
            @ApiImplicitParam(name="dirPath",value="目录",dataType="String",required = true ,paramType = "query",example="4")
    })
    public ObjectRestResponse<String> moveUrlPaths(@RequestParam("urls") String urls, @RequestParam("dirPath") String dirPath) {
        ObjectRestResponse<String> response = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(urls)) {
            response.setStatus(501);
            response.setMessage("urls！");
            return response;
        }
        String[] urlsArray =new String[] { urls };
        if(urls.indexOf(",")!=-1){
            urlsArray=urls.split(",");
        }
        if(urlsArray!=null && urlsArray.length>0){
            String urlsResult = "";
            for (String url: urlsArray) {
                ObjectRestResponse<String> res = fileInfoBiz.movePath(url,dirPath);
                if (res.getStatus() == 200){
                    urlsResult = urlsResult +","+ res.getData();
                }else {
                    response.setStatus(504);
                    response.setMessage("处理图片"+url+"出错，"+res.getMessage());
                    return response;
                }
            }
            response.setData(urlsResult.substring(1));
        }else {
            response.setStatus(502);
            response.setMessage("urls错误！");
            return response;
        }
        return response;
    }

	@GetMapping("/moveAppUploadUrlPaths")
	@ApiOperation(value = "移动app上传多个文件到指定路径", notes = "移动app上传多个文件到指定路径",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="urls",value="路径：多个文件用逗号(,)分隔",dataType="String",required = true ,paramType = "query",example="4"),
			@ApiImplicitParam(name="dirPath",value="目录",dataType="String",required = true ,paramType = "query",example="4")
	})
	public ObjectRestResponse<String> moveAppUploadUrlPaths(@RequestParam("urls") String urls, @RequestParam("dirPath") String dirPath) {
		ObjectRestResponse<String> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(urls)) {
			response.setStatus(501);
			response.setMessage("urls不能为空！");
			return response;
		}
		String[] urlsArray =new String[] { urls };
		if(urls.indexOf(",")!=-1){
			urlsArray=urls.split(",");
		}
		if(urlsArray!=null && urlsArray.length>0){
			String urlsResult = "";
			for (String url: urlsArray) {
				ObjectRestResponse<String> res = fileInfoBiz.moveAppUploadPath(url,dirPath);
				if (res.getStatus() == 200){
					urlsResult = urlsResult +","+ res.getData();
				}else {
					response.setStatus(506);
					response.setMessage("处理图片"+url+"出错，"+res.getMessage());
					return response;
				}
			}
            response.setData(urlsResult.substring(1));
		}else {
			response.setStatus(502);
			response.setMessage("urls错误！");
			return response;
		}
		return response;
	}

    @RequestMapping(value = "/uploadBase64Images",method = RequestMethod.POST)
    @ApiOperation(value = "上传Base64加密图片", notes = "上传Base64加密图片",httpMethod = "POST")
    public ObjectRestResponse uploadBase64Images(@RequestBody @ApiParam FileListVo fileListVo) throws Exception {
        String imagesUrls = "";
        if(fileListVo!=null && fileListVo.getFileList()!=null && fileListVo.getFileList().size()>0){
            for (Map<String,String> file: fileListVo.getFileList()) {
                String imagesStr = file.get("filebinary")==null ? "" : (String)file.get("filebinary");
                if(StringUtil.isNotEmpty(imagesStr)){
                    //Base64解码
                    byte[] imagesBytes = BASE64ImgUtil.decodeBase64(imagesStr);
                    InputStream in  = new DataInputStream(new ByteArrayInputStream(imagesBytes));
                    String fileType = "jpg";
                    String fileDesc = UUIDUtils.generateUuid()+"."+fileType;

                    // 上传文件
                    String folderUrl = config.getAliyunPrefix() + "/" + config.getAliyunPath() + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
                    String fullUrl = folderUrl + fileDesc;
                    UploadInfo uploadInfo = new UploadInfo();
                    uploadInfo.setId(UUIDUtils.generateUuid());
                    uploadInfo.setType("1");
                    uploadInfo.setFileType(fileType);
                    uploadInfo.setSize(100L);
                    uploadInfo.setFileName(fileDesc);
                    uploadInfo.setFileDesc(fileDesc);
                    uploadInfo.setSystem("1");
                    uploadInfo.setStatus("1");
                    uploadInfo.setLoadstatus("1");
                    uploadInfo.setCrtTime(new Date());
                    uploadInfo.setCrtUserId("admin");
                    uploadInfo.setStorageType("1");
                    uploadInfo.setIsMove("0");
                    uploadInfoService.insertSelective(uploadInfo);
                    String url = ossFactory.build().upload(in, fullUrl);
                    if (StringUtils.isNoneBlank(url)){
                        uploadInfo.setRealFileName(url.substring(url.lastIndexOf('/') + 1));
                        uploadInfo.setNatrualPath(url);
                        uploadInfo.setLoadstatus("2");
                        uploadInfo.setUpdTime(new Date());
                    }else {
                        uploadInfo.setLoadstatus("3");
                        uploadInfo.setUpdTime(new Date());
                    }
                    uploadInfoService.updateSelectiveById(uploadInfo);
                    imagesUrls = imagesUrls + "," + url;
                }
            }
            if(StringUtil.isNotEmpty(imagesUrls) && imagesUrls.length()>1){
                imagesUrls = imagesUrls.substring(1);
            }
        }
        return new ObjectRestResponse<>().data(imagesUrls);
    }

    @RequestMapping(value = "/uploadBase64Image",method = RequestMethod.POST)
    @ApiOperation(value = "上传Base64加密图片", notes = "上传Base64加密图片",httpMethod = "POST")
    public ObjectRestResponse uploadBase64Image(@RequestParam String fileData,@RequestParam String targetDir) throws Exception {
        //Base64解码
        fileData = fileData.replace("data:image/jpg;base64,", "");
        byte[] imagesBytes = BASE64ImgUtil.decodeBase64(fileData);
        InputStream in  = new DataInputStream(new ByteArrayInputStream(imagesBytes));
        String fileType = "jpg";
        String fileDesc = UUIDUtils.generateUuid()+"."+fileType;

        // 上传文件
        String folderUrl = config.getAliyunPrefix() + "/" +targetDir + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        String fullUrl = folderUrl + fileDesc;
        UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.setId(UUIDUtils.generateUuid());
        uploadInfo.setType("1");
        uploadInfo.setFileType(fileType);
        uploadInfo.setSize(100L);
        uploadInfo.setFileName(fileDesc);
        uploadInfo.setFileDesc(fileDesc);
        uploadInfo.setSystem("1");
        uploadInfo.setStatus("1");
        uploadInfo.setLoadstatus("1");
        uploadInfo.setCrtTime(new Date());
        uploadInfo.setCrtUserId("admin");
        uploadInfo.setStorageType("1");
        uploadInfo.setIsMove("0");
        uploadInfoService.insertSelective(uploadInfo);
        String url = ossFactory.build().upload(in, fullUrl);
        if (StringUtils.isNoneBlank(url)){
            uploadInfo.setRealFileName(url.substring(url.lastIndexOf('/') + 1));
            uploadInfo.setNatrualPath(url);
            uploadInfo.setLoadstatus("2");
            uploadInfo.setUpdTime(new Date());
        }else {
            uploadInfo.setLoadstatus("3");
            uploadInfo.setUpdTime(new Date());
        }
        uploadInfoService.updateSelectiveById(uploadInfo);

        return new ObjectRestResponse<>().data(url);
    }
}
