package com.github.wxiaoqi.security.common.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:15 2018/11/20
 * @Modified By:
 */
public class XMLHandler extends DefaultHandler {

	@SuppressWarnings("rawtypes")
	private HashMap map = null;// 存储单个解析的完整对象
	@SuppressWarnings("rawtypes")
	private List list = null;// 存储所有的解析对象
	private String currentTag = null;// 正在解析的元素的标签
	private String currentValue = null;// 解析当前元素的值
	private String nodeName = null;// 解析当前的节点名称

	public XMLHandler(String nodeName) {
		this.nodeName = nodeName;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
		list = new ArrayList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {
		// 当遇到文档的开头的时候，调用这个方法
		if (qName.equals(nodeName)) {
			map = new HashMap();
		}
		if (attributes != null && map != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				map.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
		currentTag = qName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// 这个方法是用来处理xml文件所读取到的内容
		if (currentTag != null && map != null) {
			currentValue = new String(ch, start, length);
			if (currentValue != null && !currentValue.trim().equals("")
					&& !currentValue.trim().equals("\n")) {
				map.put(currentTag, currentValue);
			}
		}
		currentTag = null;// 把当前的节点的对应的值和标签设置为空
		currentValue = null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals(nodeName)) {
			list.add(map);
			map = null;
		}
		super.endElement(uri, localName, qName);
	}

	public static InputStream getStringStream(String sInputString) {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
		}
		return tInputStringStream;
	}

	/**
	 * XML转List
	 */
	@SuppressWarnings("rawtypes")
	public static List getListFromXML(String xmlString,String nodeName){
		try{
			InputStream is = getStringStream(xmlString);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			XMLHandler handler = new XMLHandler(nodeName);
			parser.parse(is,handler);// 开始解析XML文件
			return handler.getList();
		}catch (ParserConfigurationException | SAXException | IOException e){
			e.printStackTrace();//不處理異常
		}
		return null;
	}


	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static void main(String[] args) {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<response><error>0</error>"
				+ "<message><srctermid>13751026807</srctermid><sendTime>20160112160748</sendTime><msgcontent>/,<><</msgcontent><addSerial></addSerial><addSerialRev>4122</addSerialRev></message>"
				+ "<message><srctermid>15112309117</srctermid><sendTime>20160510084035</sendTime><msgcontent>1111</msgcontent><addSerial></addSerial><addSerialRev>4122</addSerialRev></message>"
				+ "<message><srctermid>15112309117</srctermid><sendTime>20160510081436</sendTime><msgcontent>1111</msgcontent><addSerial></addSerial><addSerialRev>4122</addSerialRev></message>"
				+ "</response>";

		String xmlStr2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><error>0</error><message></message></response>";
		String xmlStr3 = "<response><error>0</error><message><srctermid>18820296127</srctermid><sendTime>20160527131620</sendTime><msgcontent>!@#$%^&*()_+<>,./{}|:'?</msgcontent><addSerial></addSerial><addSerialRev>4122</addSerialRev></message></response>";

		xmlStr3 = xmlStr3.replaceAll("<msgcontent>", "<msgcontent><![CDATA[").replaceAll("</msgcontent>", "]]></msgcontent>");

		System.out.println("d:"+xmlStr3);

		List list = getListFromXML(xmlStr2,"error");
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				System.out.print(key+":"+map.get(key)+",");
			}
			System.out.println("");
		}

		//<![CDATA[" 开始，由 "]]>
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(0);
			System.out.println("dtest==" + map.get("error").toString());
		}
	}


}

