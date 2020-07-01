package com.github.wxiaoqi.oss.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("file")
@Api(tags="文件下载")
@Slf4j
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping("download")
    @ApiIgnore
    public void fileDownload(HttpServletResponse response) {
        // 获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
        // String path = servletContext.getRealPath(File.separator);
        String path = File.separator + "opt" + File.separator + "logs" + File.separator + "dbcenter.log";

        // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫dbcenter.log)
        response.setHeader("Content-Disposition", "attachment;fileName=" + "dbcenter.log");
        ServletOutputStream out;
        // 通过文件路径获得File对象(假如此路径中有一个dbcenter.log文件)
        File file = new File(path);

        try {
            FileInputStream inputStream = new FileInputStream(file);

            // 3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = inputStream.read(buffer);
                // 4.写到输出流(out)中
                out.write(buffer, 0, b);
            }
            inputStream.close();
            out.close();
            out.flush();

        } catch (IOException e) {
            logger.error("文件下载异常!" , e);
        }
    }

    @RequestMapping("downloadApp")
    @ApiOperation(value = "下载APP文件", notes = "下载APP文件", httpMethod = "GET")
    @IgnoreUserToken
    @IgnoreClientToken
    public void downloadApp(HttpServletResponse response , String cType) {
        // String path = servletContext.getRealPath(File.separator);
        String path = filePath + File.separator + cType + "app.apk";
        // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫dbcenter.log)
        response.setHeader("Content-Disposition", "attachment;fileName=" + "app.apk");

        File file = new File(path);
        try(ServletOutputStream out = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file)) {
            // 3.通过response获取ServletOutputStream对象(out)
            int b = 0;
            while((b = inputStream.read())!= -1)
            {
                out.write(b);
            }
            out.flush();
        } catch (IOException e) {
            logger.error("APP下载异常!{}" , path , e);
        }

    }

    @RequestMapping("downloadPictureZip")
    @ApiOperation(value = "下载图片压缩包", notes = "下载图片压缩包", httpMethod = "GET")
    @ApiImplicitParam(name="downloadUrl",value="下载图片路径,多个用逗号隔开",dataType="String",required = true ,paramType = "query",example="4")
    @IgnoreUserToken
    @IgnoreClientToken
    public void downloadPictureZip(HttpServletResponse response,String downloadUrl){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        ZipOutputStream zos = null;
        try {
            String downloadFilename =sdf.format(new Date())+ StringUtils.generateRandomNumber(6)+".zip";//文件的名称
            // downloadFilename = new String(downloadFilename.getBytes("ISO-8859-1"),"UTF- 8");
            response.setContentType("multipart/form-data");// 指明response的返回对象是文件流
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
            zos = new ZipOutputStream(response.getOutputStream());
            //图片路径
            String[] files = downloadUrl.split(",");

            for (int i=0;i<files.length;i++) {
                URL url = new URL(files[i]);
                zos.putNextEntry(new ZipEntry(i+".png"));
                //FileInputStream fis = new FileInputStream(new File(files[i]));
                InputStream fis = url.openConnection().getInputStream();
                int b = 0;
                while((b = fis.read())!= -1){
                    zos.write(b);
                }
                fis.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("下载图片压缩包异常!{}"  , e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("下载图片压缩包异常!{}" , e);
        } finally {
            try{
                if(zos!=null){
                    zos.flush();
                }
                if(zos!=null) {
                    zos.close();
                }
            }catch (IOException e1) {
                logger.error("下载图片压缩包异常!{}" , e1);
            }
        }
    }

    @RequestMapping("downloadFile")
    @IgnoreUserToken
    @IgnoreClientToken
    public void downloadFileStream(HttpServletRequest request,HttpServletResponse response, String fileUrl, String fileName) throws IOException {
        String userAgent = request.getHeader("User-Agent");

        if(StringUtils.isEmpty(fileName)){
            fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        }
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("multipart/form-data");// 指明response的返回对象是文件流
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out = response.getOutputStream();
        URL url = new URL(fileUrl);
        HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
        // 连接指定的网络资源
        httpUrl.connect();
        // 获取网络输入流
        BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
        int b;
        while ((b = bis.read())!= -1){
            out.write(b);
        }
        out.flush();
        out.close();
        bis.close();

    }



    private String filePath;

    public FileController() {
        super();
        try {
            //获取Apollo配置
            Config config = ConfigService.getConfig("ace-app-version");
            String fileDir = config.getProperty("check.catalogue", "");
            File file = new File(fileDir);
            if(!file.exists()){
                file.mkdir();
            }
            filePath = fileDir;
        } catch (Exception e) {

        }

    }

}
