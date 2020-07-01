package com.github.wxiaoqi.security.external.util;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:41 2019/1/2
 * @Modified By:
 */
public class Utils {
	private static ApplicationContext context;

	private static ObjectMapper objectMapper = new ObjectMapper();

	private static final ExecutorService threadPool = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);

	public static void initContext(ApplicationContext ctx) {
		context = ctx;
	}

	public static void execute(Runnable command){
		threadPool.execute(command);
	}

	public static void stop( ){
		threadPool.shutdown();
	}



	public static <T> Future<T> submit(Callable<T> task){
		return threadPool.submit(task);
	}

	public static <T> T getBean(Class<T> requiredType) {
		if (context == null) {
			return null;
		}
		return context.getBean(requiredType);
	}

	public static Object getBean(String requiredType) {
		if (context == null) {
			return null;
		}
		return context.getBean(requiredType);
	}

	/**
	 * json字符串转对象
	 *
	 * @param t
	 * @param o
	 * @return
	 */
	public static <T> T readValue(Class<T> t, String o) {
		T data = null;
		try {
			data = objectMapper.readValue(o, t);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * get guid
	 *
	 * @return
	 */
	public static String getGUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.toUpperCase();
		uuid = uuid.replaceAll("-", "");

		return uuid;
	}

	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") List list){
		if(list == null || list.size() == 0){
			return false;
		}
		return true;
	}
}
