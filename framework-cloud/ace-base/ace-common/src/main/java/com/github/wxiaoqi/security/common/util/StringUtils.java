package com.github.wxiaoqi.security.common.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Lzx
 * @Description: 字符串工具类
 * @Date: Created in 10:23 2018/11/20
 * @Modified By:
 */
public class StringUtils {


	public static boolean checkPwdPatten(String password) {
		String pattern = "^.*(?=.{8,})(?=.*[0-9]{1,})(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?()]).*$";
		return !Pattern.matches(pattern,password);
	}

	public static boolean isPhone(String phone) {
		String p1 = "^[\\d\\s]+[-]{0,1}[\\d\\s]+$"; // 验证减号
		String p2 = "[\\+]{0,1}([0-9])+$";//验证加号
		//数字+横杆+空格
		if ( Pattern.matches(p1,phone) || Pattern.matches(p2,phone)) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 校验手机号
	 *
	 * @param str
	 * @return
	 */
	public static boolean isMobile(final String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {

			String str = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(email);
			flag = m.matches();
		} catch (Exception e) {

		}
		return flag;
	}

	public static float formatPrice(String str) {
		float result = 0;
		if (!isEmpty(str)) {
			try {
				result = Float.parseFloat(str);
			} catch (Exception e) {
				result = 0;
			}
		}
		return result;

	}

	public static boolean isEmpty(String value) {
		if (value == null || value.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/*------------校验身份证开始------------*/
	final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

	static {
		zoneNum.put(11, "北京");
		zoneNum.put(12, "天津");
		zoneNum.put(13, "河北");
		zoneNum.put(14, "山西");
		zoneNum.put(15, "内蒙古");
		zoneNum.put(21, "辽宁");
		zoneNum.put(22, "吉林");
		zoneNum.put(23, "黑龙江");
		zoneNum.put(31, "上海");
		zoneNum.put(32, "江苏");
		zoneNum.put(33, "浙江");
		zoneNum.put(34, "安徽");
		zoneNum.put(35, "福建");
		zoneNum.put(36, "江西");
		zoneNum.put(37, "山东");
		zoneNum.put(41, "河南");
		zoneNum.put(42, "湖北");
		zoneNum.put(43, "湖南");
		zoneNum.put(44, "广东");
		zoneNum.put(45, "广西");
		zoneNum.put(46, "海南");
		zoneNum.put(50, "重庆");
		zoneNum.put(51, "四川");
		zoneNum.put(52, "贵州");
		zoneNum.put(53, "云南");
		zoneNum.put(54, "西藏");
		zoneNum.put(61, "陕西");
		zoneNum.put(62, "甘肃");
		zoneNum.put(63, "青海");
		zoneNum.put(64, "宁夏");
		zoneNum.put(65, "新疆");
		zoneNum.put(71, "台湾");
		zoneNum.put(81, "香港");
		zoneNum.put(82, "澳门");
		zoneNum.put(91, "外国");
	}

	final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
	final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

	public static boolean isCardId(String certNo) {
		if (certNo == null || certNo.equals("")) {
			return false;
		}
		if (certNo == null || (certNo.length() != 15 && certNo.length() != 18))
			return false;
		final char[] cs = certNo.toUpperCase().toCharArray();
		// 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X')
				break;// 最后一位可以 是X或x
			if (cs[i] < '0' || cs[i] > '9')
				return false;
			if (i < cs.length - 1) {
				power += (cs[i] - '0') * POWER_LIST[i];
			}
		}

		// 校验区位码
		if (!zoneNum.containsKey(Integer.valueOf(certNo.substring(0, 2)))) {
			return false;
		}

		// 校验年份
		String year = certNo.length() == 15 ? getIdcardCalendar() + certNo.substring(6, 8) : certNo.substring(6, 10);

		final int iyear = Integer.parseInt(year);
		if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
			return false;// 1900年的PASS，超过今年的PASS

		// 校验月份
		String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10, 12);
		final int imonth = Integer.parseInt(month);
		if (imonth < 1 || imonth > 12) {
			return false;
		}

		// 校验天数
		String day = certNo.length() == 15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
		final int iday = Integer.parseInt(day);
		if (iday < 1 || iday > 31)
			return false;

		// 校验"校验码"
		if (certNo.length() == 15)
			return true;
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}

	private static int getIdcardCalendar() {
		GregorianCalendar curDay = new GregorianCalendar();
		int curYear = curDay.get(Calendar.YEAR);
		int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
		return year2bit;
	}

	public static ArrayList<String> stringToArrayList(String str, String mark) {
		if (isEmpty(str) || isEmpty(mark)) {
			return null;
		}
		ArrayList<String> result = new ArrayList<String>();
		if (str.contains(mark)) {
			String[] strs = str.split(mark);
			for (String value : strs) {
				result.add(value);
			}
		} else {
			result.add(str);
		}
		return result;
	}

	public static ArrayList<Integer> stringToIntArrayList(String str, String mark) {
		if (isEmpty(str) || isEmpty(mark)) {
			return null;
		}
		ArrayList<Integer> result = new ArrayList<Integer>();
		try{
			if (str.contains(mark)) {
				String[] strs = str.split(mark);
				for (String value : strs) {
					if (!isEmpty(value)) {
						result.add(Integer.parseInt(value));
					}
				}
			} else {
				result.add(Integer.parseInt(str));
			}
		}catch (Exception e){

		}
		return result;
	}

	/**
	 * 电话号码加密
	 */
	public static String getPhoneEncryption(String phone) {
		if (isEmpty(phone))
			return "";
		if (phone.length() < 7) {
			return phone;
		}
		return phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
	}

	/**
	 * 身份证号码加密
	 */
	public static String getIDCardEncryption(String idCradNumber) {
		if (isEmpty(idCradNumber))
			return "";
		if (idCradNumber.length() < 10) {
			return idCradNumber;
		}
		return idCradNumber.substring(0, 4) + "*********"
				+ idCradNumber.substring(idCradNumber.length() - 4, idCradNumber.length());
	}

	/**
	 * 祛除字符串中的所有的空格
	 */
	public static String removeSpaceKey(String str) {
		if (isEmpty(str))
			return "";
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < str.length(); index++) {
			char c = str.charAt(index);
			if (c == ' ') {
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 校验密码
	 *
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		String wordRegFormat = "^[\\s\\S]*[A-Za-z]+[\\s\\S]*$";
		String numRegFormat = "^[\\s\\S]*[0-9]+[\\s\\S]*$";

		if (password.matches(wordRegFormat) && password.matches(numRegFormat))
			return true;
		else
			return false;
	}
	/**
	 * 获取随机数
	 * @param num 所需位数
	 * @return
	 */
	public static String generateRandomNumber(int num) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 任何一个字符对象为空则返回true
	 * @param strs
	 * @return
	 */
	public static boolean isAnyoneEmpty(String... strs){
		for (String object : strs) {
			if(StringUtils.isEmpty(object)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取初始list里面包含另一个目标list里面的所有内容
	 * @param list 初始列表
	 * @param map 匹配目标列表对象
	 * @return
	 */
	public static List<String> geInitContainList(List<String> list,Map<String,List<String>> map){
		List<String> woList = new ArrayList<String>();
		for (String key : map.keySet()){
			List<String> listTemp = map.get(key);
			boolean isMatched = true;
			for (String skill : list){
				if(!listTemp.contains(skill)){
					isMatched = false;
					break;
				}
			}
			if(isMatched){
				woList.add(key);
			}
		}
		return woList;
	}

	/**
	 * 获取匹配目标列表里面包含另一个初始列表里面的所有内容
	 * @param list 初始列表
	 * @param map 匹配目标列表对象
	 * @return
	 */
	public static List<String> getTargetContainList(List<String> list,Map<String,List<String>> map){
		List<String> woList = new ArrayList<String>();
		for (String key : map.keySet()){
			List<String> listTemp = map.get(key);
			boolean isMatched = true;
			for (String skill : listTemp){
				if(!list.contains(skill)){
					isMatched = false;
					break;
				}
			}
			if(isMatched){
				woList.add(key);
			}
		}
		return woList;
	}

	/**
	 * 踢除列表中重复元素
	 * @param list
	 * @return
	 */
	public static List removeDuplicate(List list) {
		if(list!=null && list.size()>0){
			HashSet h = new HashSet(list);
			list.clear();
			list.addAll(h);
		}
		return list;
	}

}
