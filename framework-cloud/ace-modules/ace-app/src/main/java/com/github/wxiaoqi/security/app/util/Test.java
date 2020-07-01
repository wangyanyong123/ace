package com.github.wxiaoqi.security.app.util;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        List<String> skillListTemp = new ArrayList<>();
        skillListTemp.add("1");
        skillListTemp.add("2");
        skillListTemp.add("3");

        List<String> skillListTemp2 = new ArrayList<>();
        skillListTemp2.add("1");
        skillListTemp2.add("2");
        skillListTemp2.add("4");
        List<String> skillListTemp3 = new ArrayList<>();
        skillListTemp3.add("1");
        skillListTemp3.add("3");
        List<String> skillListTemp4 = new ArrayList<>();
        skillListTemp4.add("1");
        skillListTemp4.add("2");
        skillListTemp4.add("4");
        skillListTemp4.add("3");

        Map<String,List<String>> map = new HashMap<String,List<String>>();
        map.put("woId1",skillListTemp2);
        map.put("woId2",skillListTemp3);
        map.put("woId3",skillListTemp4);

        List<String> woList = new ArrayList<String>();
        for (String key : map.keySet()){
            List<String> list = map.get(key);
            boolean isMatched = true;
            for (String skill : list){
                if(!skillListTemp.contains(skill)){
                    isMatched = false;
                    break;
                }
            }
            if(isMatched){
                woList.add(key);
            }
        }

        System.out.println(woList.size()+"ss"+woList.toString());
    }


}
