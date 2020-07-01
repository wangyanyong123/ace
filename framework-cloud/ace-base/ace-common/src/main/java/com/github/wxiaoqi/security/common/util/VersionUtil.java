package com.github.wxiaoqi.security.common.util;


public class VersionUtil {
	/**
	 * 根据请求参数info获取APP版本信息
	 * @param info  Info=iOS O+|8.4.1|4.6.1|iPhone 5S
	 * @return version
	 */
	public static String getVersion(String info){
		String[] infos = info.split("|");
		String version = infos[2];
		return version;
	}
	
	/**
	 * 比较两个版本号大小
	 * @param info Info=iOS O+|8.4.1|4.6.1|iPhone 5S
	 * @return 1——前者为新版本
	 * @return -1——前者旧版本
	 * @return 0——相等
	 */
	public static int versionCompare(String info,String iOSVersion,String androidVersion){
		int flag = 0;
		String appType = info.split("\\|")[0];
		String[] appVersion = info.split("\\|")[2].split("\\.");  //以.分隔需要转义
        String[] nowVersion = null;
		if(appType.contains("iOS")){
			nowVersion = iOSVersion.split("\\.");  //以.分隔需要转义
		} else if(appType.contains("Android")){
			nowVersion = androidVersion.split("\\.");  //以.分隔需要转义
		}
        
        int length=Math.min(appVersion.length, nowVersion.length);
        int diff=0;
        for(int i=0;i<length;i++){
            diff = appVersion[i].length() - nowVersion[i].length();
            if(diff==0){   //位数相同时，比较大小
            	
                if(appVersion[i].compareTo(nowVersion[i])>0){
                	flag = 1;
                    break;
                }else if(appVersion[i].compareTo(nowVersion[i])<0){
                	flag= -1;
                    break;
                }else if(i==length-1){
                	flag = appVersion[i].compareTo(nowVersion[i]);
                }
                
            }else if(diff !=0){  //位数不同时，直接输出
            	flag = diff;
                break;
            }            
        }
        return flag;
	}
	
	/**
	 * 比较两个版本号大小
	 * @param version1
	 * @param version2
	 * @return 1——前者为新版本
	 * @return -1——前者旧版本
	 * @return 0——相等
	 */
//	public static int versionCompare(String version1, String version2){
//		int flag = 0;
//		String[] split1 = version1.split("\\.");  //以.分隔需要转义
//        String[] split2 = version2.split("\\.");
//
//        int length=Math.min(split1.length, split2.length);
//        int diff=0;
//        for(int i=0;i<length;i++){
//        	//System.out.println("split1["+i+"]长度为"+split1[i].length());
//        	//System.out.println("split2["+i+"]长度为"+split1[i].length());
//            diff = split1[i].length()-split2[i].length();
//            if(diff==0){   //位数相同时，比较大小
//
//                if(split1[i].compareTo(split2[i])>0){
//                	flag = 1;
//                    break;
//                }else if(split1[i].compareTo(split2[i])<0){
//                	flag= -1;
//                    break;
//                }else if(i==length-1){
//                	flag = split1[i].compareTo(split2[i]);
//                }
//
//            }else if(diff !=0){  //位数不同时，直接输出
//            	flag = diff;
//                break;
//            }
//        }
//        return flag;
//	}
	
	/** 
	 * 比较版本号的大小,newVersion大则返回一个正数,oldVersion大返回一个负数,相等则返回0 
	 * @param newVersion 
	 * @param oldVersion
	 * @author chenhao
	 */  
	public static int compareVersion(String newVersion, String oldVersion) throws Exception {  
	    if (newVersion == null || oldVersion == null) {  
	        throw new Exception("version is null");  
	    }

		if (newVersion.equals(oldVersion)) {
			return 0;
		}

	    String[] versionArray1 = newVersion.split("\\."); 
	    String[] versionArray2 = oldVersion.split("\\.");  
	    int idx = 0;  
	    int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值  
	    int diff = 0;  
	    while (idx < minLength  
	            && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度  
	            && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符  
	        ++idx;  
	    }

	    //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大
	    diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;  
	    return diff;  
	}
	
	/**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 
     */
    public static void main(String[] args) throws Exception {
//        System.out.println(compareVersion("1.0.0", "0.9.10"));
        System.out.println(compareVersion("0.9.3", "0.8.0"));
    }
}
