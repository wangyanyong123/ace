package com.github.wxiaoqi.security.app.util;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:49 2018/11/27
 * @Modified By:
 */
public class XmlTool {
	/**
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static Document strToDocument(String xml){
		try {
			return DocumentHelper.parseText("<xml>"+xml+"</xml>");
		} catch (DocumentException e) {
			return null;
		}
	}

	/**
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static JSONObject documentToJSONObject(String xml){
		return elementToJSONObject(strToDocument(xml).getRootElement());
	}

	/**
	 * @param node
	 * @return
	 */
	public static JSONObject elementToJSONObject(Element node) {
		JSONObject result = new JSONObject();
		List<Attribute> listAttr = node.attributes();
		for (Attribute attr : listAttr) {
			result.put(attr.getName(), attr.getValue());
		}
		List<Element> listElement = node.elements();
		if (!listElement.isEmpty()) {
			for (Element e : listElement) {
				if (e.attributes().isEmpty() && e.elements().isEmpty())
					result.put(e.getName(), e.getTextTrim());
				else {
					if (!result.containsKey(e.getName()))
						result.put(e.getName(), new JSONArray());
					((JSONArray) result.get(e.getName())).add(elementToJSONObject(e));
				}
			}
		}
		return result;
	}
}

