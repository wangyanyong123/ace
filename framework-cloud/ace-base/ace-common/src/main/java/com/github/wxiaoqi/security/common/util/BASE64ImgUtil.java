package com.github.wxiaoqi.security.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class BASE64ImgUtil {
	
	public static void main(String[] args) {
    	String imagesUrl = "https://virgin.oss-cn-shenzhen.aliyuncs.com/jinmao/dev/ORDERWO/20190114/590b6537d2827b58a7dff87abc92ae65.jpg";
    	//1.加密
    	String resultStr = encoderOne(imagesUrl);
    	//2.解密
    	decoder(resultStr);

	}

	public static List<Map<String,String>> encoder(String imagesUrls){
	    List<Map<String,String>> imagesList = new ArrayList<Map<String,String>>();
        // 对字节数组Base64编码
        if(StringUtils.isNotEmpty(imagesUrls)) {
            String[] imgURLArray = imagesUrls.split(",");
            Map<String,String> imagesMap = null;
            for (int i = 0; i < imgURLArray.length; i++) {
                String encoder = encoderOne(imgURLArray[i]);
                imagesMap = new HashMap<>();
                imagesMap.put("filebinary",encoder);
                imagesMap.put("fileId",imgURLArray[i]);
                imagesList.add(imagesMap);
            }
        }
        return imagesList;
    }

    public static String encoderOne(String imagesUrl) {
		//1.加密
        StringBuilder result = new StringBuilder();
    	try {  
            // 创建URL  
            URL url = new URL(imagesUrl);  
            // 创建链接  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(3 * 1000);  
            ByteArrayOutputStream outPut = new ByteArrayOutputStream();
            
            InputStream inStream = conn.getInputStream();  
            int len = -1;
            byte[] data = new byte[inStream.available()];
            while ((len = inStream.read(data)) != -1) {
            	outPut.write(data, 0, len);
            }
            result.append(new String(Base64.encodeBase64(outPut.toByteArray())));
            inStream.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
    	
    	String resultStr = result.toString();
    	System.out.println("加密之后字符串："+resultStr.toString());
		return resultStr;
	}

    public static byte[] decodeBase64(String resultStr) {
        //Base64解码
        byte[] b = Base64.decodeBase64(resultStr);
        return  b;
    }

	public static void decoder(String resultStr) {
		//2.解密
    	try
        {
            //Base64解码
            byte[] b = Base64.decodeBase64(resultStr);
            //新生成的图片png
            String imgFilePath = "d://22212.png";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        }catch (Exception e){
        	e.printStackTrace();  
	     }
	}
    
}
