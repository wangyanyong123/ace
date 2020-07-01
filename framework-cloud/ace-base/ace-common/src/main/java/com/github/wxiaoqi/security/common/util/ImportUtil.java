package com.github.wxiaoqi.security.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.ObjectMetadata;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * excel操作类
 * @author jiandan
 *
 */
public class ImportUtil {
	// 日志
	private static Logger logger = LoggerFactory.getLogger(ImportUtil.class);

	/**
	 * 根据文件输入流上传文件
	 * @param fileName
	 * @param folderName
	 * @param ossClient
	 * @param bucket
	 * @return
	 */
	public static ObjectRestResponse uploadOssFile(InputStream is, String folderName, String fileName, OSSClient ossClient, String bucket) {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(ossClient != null){
			try {
				// 文件夹
				String folderUrl = folderName + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
				// 文件全称
				String fullName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + randomNumber(4) + fileName;
				// 文件全路径
				String fullUrl = folderUrl + fullName;
				// 创建上传Object的Metadata
				ObjectMetadata meta = new ObjectMetadata();
				// 设置上传内容类型
				meta.setContentType(getContentType(fullUrl));
				//上传
				ossClient.putObject(bucket, fullUrl, is, meta);
				// 获取响应信息
				ResponseMessage responseMsg =  ossClient.getObject(bucket, fullUrl).getResponse();
				// 文件成功上传到OSS
				logger.info("返回的编码状态为：{}.", responseMsg.getStatusCode());
				if(responseMsg.getStatusCode() == 200){
					msg.setStatus(200);
					msg.setData(fullUrl);
					logger.info("文件上传到OSS的地址为：{}.", responseMsg.getUri());
					logger.info("fullUrl:{}.", fullUrl);
				}else{
					msg.setStatus(responseMsg.getStatusCode());
					msg.setMessage(responseMsg.getErrorResponseAsString());
					logger.info("返回的错误码为：{}.", responseMsg.getErrorResponseAsString());
				}
				// 关闭client
				ossClient.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
				msg.setStatus(500);
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(ossClient != null){
					ossClient.shutdown();
				}
			}
		} else {
			logger.info("oss客户端不存在");
			msg.setStatus(500);
			msg.setMessage("oss客户端不存在");
		}
		return msg;
	}

	/**
	 * 根据文件路径上传文件
	 * @param fileName
	 * @param folderName
	 * @param ossClient
	 * @param bucket
	 * @return
	 */
	public static ObjectRestResponse uploadOssFile(String filePath, String fileName, String folderName, OSSClient ossClient, String bucket) {
		ObjectRestResponse msg = new ObjectRestResponse();
		File file = null;
		if(ossClient != null){
			try {
				// 文件夹
				String folderUrl = folderName + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
				// 文件全路径
				String fullUrl = folderUrl + fileName;
				// 创建上传Object的Metadata
				ObjectMetadata meta = new ObjectMetadata();
				// 设置上传内容类型
				meta.setContentType(getContentType(fullUrl));
				//上传
				file = new File(filePath + fileName);
				System.out.println(filePath + fileName);
				ossClient.putObject(bucket, fullUrl, file, meta);
				// 获取响应信息
				ResponseMessage responseMsg =  ossClient.getObject(bucket, fullUrl).getResponse();
				// 文件成功上传到OSS
				logger.info("返回的编码状态为：{}.", responseMsg.getStatusCode());
				if(responseMsg.getStatusCode() == 200){
					msg.setStatus(200);
					msg.setData(fullUrl);
					logger.info("文件上传到OSS的地址为：{}.", responseMsg.getUri());
					logger.info("fullUrl:{}.", fullUrl);
				}else{
					msg.setStatus(responseMsg.getStatusCode());
					msg.setMessage(responseMsg.getErrorResponseAsString());
					logger.info("返回的错误码为：{}.", responseMsg.getErrorResponseAsString());
				}
				// 关闭client
				ossClient.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
				msg.setStatus(500);
			} finally {
				if(ossClient != null){
					ossClient.shutdown();
				}
				if(file != null && file.exists()){
					file.delete();
				}
			}
		} else {
			logger.info("oss客户端不存在");
			msg.setStatus(500);
			msg.setMessage("oss客户端不存在");
		}
		return msg;
	}

	/**
	 * 可以循环调用oss上传，需手动创建客户端，并手动调用，速度快
	 * @param is
	 * @param folderName
	 * @param fileName
	 * @param ossClient
	 * @return
	 */
	public static ObjectRestResponse uploadOssFileBatch(InputStream is, String folderName, String fileName, OSSClient ossClient, ObjectMetadata meta, String bucket) {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(ossClient != null){
			try {
				// 文件夹
				String folderUrl = folderName + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
				// 文件全称
				String fullName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + randomNumber(4) + fileName;
				// 文件全路径
				String fullUrl = folderUrl + fullName;
				//上传
				ossClient.putObject(bucket, fullUrl, is, meta);
				// 获取响应信息
				ResponseMessage responseMsg =  ossClient.getObject(bucket, fullUrl).getResponse();
				// 文件成功上传到OSS
				logger.info("返回的编码状态为：{}.", responseMsg.getStatusCode());
				if(responseMsg.getStatusCode() == 200){
					msg.setStatus(200);
					msg.setData(fullUrl);
					logger.info("文件上传到OSS的地址为：{}.", responseMsg.getUri());
					logger.info("RpcUtils.getRpcPassport():{}.", responseMsg.getHeaders().get("Content-Type"));
					logger.info("fullUrl:{}.", fullUrl);
				}else{
					msg.setStatus(responseMsg.getStatusCode());
					msg.setMessage(responseMsg.getErrorResponseAsString());
					logger.info("返回的错误码为：{}.", responseMsg.getErrorResponseAsString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg.setStatus(500);
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.info("oss客户端不存在");
			msg.setStatus(500);
			msg.setMessage("oss客户端不存在");
		}
		return msg;
	}


	/**
     * 获取count个随机数
     * @param count 随机数个数
     * @return
     */
    public static String randomNumber(int count){
    	StringBuilder sb = new StringBuilder();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
	public static String getContentType(String fileName){
    	String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) return "image/bmp";
        else if(".gif".equalsIgnoreCase(fileExtension)) return "image/gif";
        else if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)) return "image/jpeg";
        else if(".png".equalsIgnoreCase(fileExtension)) return "image/png";
        else if(".html".equalsIgnoreCase(fileExtension)) return "text/html";
        else if(".txt".equalsIgnoreCase(fileExtension)) return "text/plain";
        else if(".vsd".equalsIgnoreCase(fileExtension)) return "application/vnd.visio";
        else if(".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)) return "application/vnd.ms-powerpoint";
        else if(".doc".equalsIgnoreCase(fileExtension) || ".docx".equalsIgnoreCase(fileExtension)) return "application/msword";
        else if(".xls".equalsIgnoreCase(fileExtension) || ".xlsx".equalsIgnoreCase(fileExtension)) return "application/vnd.ms-excel";
        else if(".xml".equalsIgnoreCase(fileExtension)) return "text/xml";
        else if(".pdf".equalsIgnoreCase(fileExtension)) return "application/pdf";
        else return "text/html";
	}

	/**
	 * 根据文件输入流上传文件,可直接替换原有文件
	 * ##不关闭ossClient,请在调用方法中关闭
	 * @param fullUrl
	 * @param folderName
	 * @param ossClient
	 * @param bucket
	 * @return
	 */
	public static ObjectRestResponse uploadOrCoverFile(InputStream is, String fullUrl, String folderName, String fileName, String bucket, OSSClient ossClient) {
		ObjectRestResponse msg = new ObjectRestResponse();
		try {
			if(isEmpty(fullUrl)){
				// 文件夹
				String folderUrl = folderName + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
				// 文件全称
				String fullName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ImportUtil.randomNumber(4) + fileName;
				// 文件全路径
				fullUrl = folderUrl + fullName;
			}
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 设置上传内容类型
			meta.setContentType(ImportUtil.getContentType(fullUrl));
			//上传
			ossClient.putObject(bucket, fullUrl, is, meta);
			// 获取响应信息
			ResponseMessage responseMsg =  ossClient.getObject(bucket, fullUrl).getResponse();
			// 文件成功上传到OSS
			logger.info("返回的编码状态为：{}.", responseMsg.getStatusCode());
			if(responseMsg.getStatusCode() == 200){
				msg.setStatus(200);
				msg.setData(fullUrl);
				logger.info("文件上传到OSS的地址为：{}.", responseMsg.getUri());
				logger.info("fullUrl:{}.", fullUrl);
			}else{
				msg.setStatus(responseMsg.getStatusCode());
				msg.setMessage(responseMsg.getErrorResponseAsString());
				logger.info("返回的错误码为：{}.", responseMsg.getErrorResponseAsString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setStatus(500);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}

	private static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

	/**
	 * 通用导出处理方法
	 * @param titles 表头
	 * @param keys 字段名
	 * @param dataList 数据列
	 * @param fileName 文件名，带文件类型
	 * @param excelUrl excel上传路径
	 * @param bucket oss-bucket
	 * @return
	 */
	@SuppressWarnings("resource")
	public static InputStream exportExcel(String[] titles, String [] keys, List<Map<String, Object>> dataList, String fileName, String excelUrl, String bucket){
		ObjectRestResponse msg = new ObjectRestResponse();
		DataInputStream in = null;
		ByteArrayOutputStream os = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		int listSize = 0;
		try {
//			XSSFWorkbook workbook = new XSSFWorkbook();
			SXSSFWorkbook  workbook = new SXSSFWorkbook(-1);
			sheet = workbook.createSheet();
			workbook.setSheetName(0,fileName);
			// 第一行标题
			row = sheet.createRow(0);
			for(int i = 0;i < titles.length;i++){
				cell = row.createCell(i);
				cell.setCellValue(titles[i]);
			}
			listSize = dataList.size();
			Map<String, Object> dataMap = null;
			Object object = null;
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			localSimpleDateFormat.setLenient(false);
			logger.info("循环开始编写单元格:"+localSimpleDateFormat.format(new Date()));
			for(int j = 0;j < listSize;j++){
				row = sheet.createRow(j+1);
				dataMap = dataList.get(j);
				for(int k = 0;k < titles.length;k++){
					cell = row.createCell(k);
					object = dataMap.get(keys[k]);
					if(object==null) {
						cell.setCellValue("");
					}else {
						cell.setCellValue(object.toString());
					}
				}
			}

			int k =1;
			// 合并相同列中的数据单元格
			int flag = 0;
			if (!"项目注册状态统计.xlsx".equals(fileName)){
				if(sheet.getLastRowNum()>1){//获取最后一行行标，比行数小  满足合并的条件，1是数据行的开始
					for(int i=1;i<=sheet.getLastRowNum();i++){
						Row row_k = sheet.getRow(k);//获取开始一行的数据
						Cell cell_k = row_k.getCell(0);
						String departname = cell_k.getStringCellValue();
						Row row_i = sheet.getRow(i);//获取开始下一行的数据
						Cell cells_i = row_i.getCell(0);
						//判断数据是否相同
						if(departname.equals(cells_i.getStringCellValue())){
							flag++;//行数
						}else {
							sheet.addMergedRegion(new CellRangeAddress(k, flag, 0, 0));
							k = 1 + flag;
							i--;
						}
					}
					sheet.addMergedRegion(new CellRangeAddress(k, flag, 0, 0));
				}
			}
			logger.info("循环结束编写单元格:"+localSimpleDateFormat.format(new Date()));
			os = new ByteArrayOutputStream();
			workbook.write(os);
			byte[] workbookBytes = os.toByteArray();
			// 把文件已流文件的方式 推入到url中
			in  = new DataInputStream(new ByteArrayInputStream(workbookBytes));
//			logger.info("开始上传文件到OSS:"+localSimpleDateFormat.format(new Date()));
//			msg = ImportUtil.uploadOssFile(in, excelUrl, fileName, ossClient, bucket);
//			logger.info("结束上传文件到OSS:"+localSimpleDateFormat.format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭流
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return in;
	}

	/**
	 * 通用导出处理方法，支持多Sheet
	 * @param sourceMap 数据源
	 * @param title 表头key
	 * @param key 字段名key
	 * @param data 数据集key
	 * @param ossClient oss客户端
	 * @param fileName 文件名，带文件类型
	 * @param excelUrl excel上传路径
	 * @param bucket oss-bucket
	 * @return
	 */
	@SuppressWarnings({ "resource", "unchecked" })
	public static ObjectRestResponse exportExcelSheets(Map<String, Map<String, Object>> sourceMap, String title, String key, String data, OSSClient ossClient, String fileName, String excelUrl, String bucket){
		ObjectRestResponse msg = new ObjectRestResponse();
		DataInputStream in = null;
		ByteArrayOutputStream os = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		int listSize = 0;
		try {
//			XSSFWorkbook workbook = new XSSFWorkbook();
			Workbook workbook = new SXSSFWorkbook(800);

			Iterator<Entry<String, Map<String, Object>>> printEntries = sourceMap.entrySet().iterator();
			Entry<String, Map<String, Object>> item = null;
			Map<String, Object> items = null;
        	String sheetName = "";
        	String[] titles = null;
        	String[] keys = null;
        	List<Map<String, Object>> dataList = null;
        	Map<String, Object> dataMap = null;

        	while (printEntries.hasNext()){
        		item = printEntries.next();
        		sheetName = item.getKey();
        		sheet = workbook.createSheet(sheetName);
        		row = sheet.createRow(0);

        		items = item.getValue();
        		titles = (String[]) items.get(title);
        		keys = (String[]) items.get(key);
        		dataList = (List<Map<String, Object>>) items.get(data);

    			for(int i = 0;i < titles.length;i++){
    				cell = row.createCell(i);
    				cell.setCellValue(titles[i]);
    			}
    			listSize = dataList.size();
    			for(int j = 0;j < listSize;j++){
    				row = sheet.createRow(j+1);
    				dataMap = dataList.get(j);
    				for(int k = 0;k < titles.length;k++){
    					cell = row.createCell(k);
    					if(dataMap.containsKey(keys[k])){
    						cell.setCellValue((dataMap.get(keys[k]) == null?"":dataMap.get(keys[k])).toString());
    					} else {
    						cell.setCellValue("");
    					}
    				}
    			}
        	}

			os = new ByteArrayOutputStream();
			workbook.write(os);
			byte[] workbookBytes = os.toByteArray();
			// 把文件已流文件的方式 推入到url中
			in  = new DataInputStream(new ByteArrayInputStream(workbookBytes));
			msg = ImportUtil.uploadOssFile(in, excelUrl, fileName, ossClient, bucket);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭流
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}


}
