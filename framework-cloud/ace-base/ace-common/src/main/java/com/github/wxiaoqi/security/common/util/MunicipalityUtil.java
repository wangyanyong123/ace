package com.github.wxiaoqi.security.common.util;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 直辖市工具类
 * @author: guohao
 * @date: 2020-06-15 11:09
 **/
public class MunicipalityUtil {

    public static List<String> getDeliveryCityCodeList(String code){
        if(StringUtils.isEmpty(code)){
            return Collections.emptyList();
        }
        Boolean municipality = isMunicipality(code);
        if(municipality){
            String provinceCode = getProvinceCode(code);
            if(AceDictionary.MUNICIPALITY_BJ.equals(provinceCode)){
                return new ArrayList<>(MUNICIPALITY_BJ_AREA_CODE.keySet());
            }else if(AceDictionary.MUNICIPALITY_TJ.equals(provinceCode)){
                return new ArrayList<>(MUNICIPALITY_TJ_AREA_CODE.keySet());
            }else if(AceDictionary.MUNICIPALITY_CQ.equals(provinceCode)){
                return new ArrayList<>(MUNICIPALITY_CQ_AREA_CODE.keySet());
            }else{
                return new ArrayList<>(MUNICIPALITY_SH_AREA_CODE.keySet());
            }
        }
        return Collections.singletonList(code);
    }

    /**
     * 是否为直辖市编码
     * @param code 编码 省/市/区 编码
     * @return Boolean
     */
    public static Boolean isMunicipality(String code){
        String provinceCode = getProvinceCode(code);
       return AceDictionary.MUNICIPALITY.containsKey(provinceCode);
    }

    /**
     * 获取省份编码
     * @param code 编码 省/市/区 编码
     * @return String
     */
    public static String getProvinceCode(String code){
        Assert.hasLength(code,"code is null/empty");
        Assert.isTrue(code.length() >=6,"code not right");
        return code.substring(0, 3)+"000";

    }




   public static Map<String, String> MUNICIPALITY_BJ_AREA_CODE = new HashMap<String, String>() {

        private static final long serialVersionUID = -9084970948858124229L;
        {
            put("110101","东城区");
            put("110102","西城区");
            put("110103","崇文区");
            put("110104","宣武区");
            put("110105","朝阳区");
            put("110106","丰台区");
            put("110107","石景山区");
            put("110108","海淀区");
            put("110109","门头沟区");
            put("110111","房山区");
            put("110112","通州区");
            put("110113","顺义区");
            put("110114","昌平区");
            put("110115","大兴区");
            put("110116","怀柔区");
            put("110117","平谷区");
            put("110228","密云县");
            put("110229","延庆县");
        }
    };
    public static Map<String, String> MUNICIPALITY_TJ_AREA_CODE = new HashMap<String, String>() {

        private static final long serialVersionUID = 3807504360041534402L;

        {
           put("120101" ,"和平区");
           put("120102" ,"河东区");
           put("120103" ,"河西区");
           put("120104" ,"南开区");
           put("120105" ,"河北区");
           put("120106" ,"红桥区");
           put("120107" ,"塘沽区");
           put("120108" ,"汉沽区");
           put("120109" ,"大港区");
           put("120110" ,"东丽区");
           put("120111" ,"西青区");
           put("120112" ,"津南区");
           put("120113" ,"北辰区");
           put("120114" ,"武清区");
           put("120115" ,"宝坻区");
           put("120221" ,"宁河县");
           put("120223" ,"静海县");
           put("120225" ,"蓟县");
        }
    };
    public static Map<String, String> MUNICIPALITY_SH_AREA_CODE = new HashMap<String, String>() {

        private static final long serialVersionUID = 611076229743689173L;

        {
            put("310101" ,"黄浦区");
            put("310103" ,"卢湾区");
            put("310104" ,"徐汇区");
            put("310105" ,"长宁区");
            put("310106" ,"静安区");
            put("310107" ,"普陀区");
            put("310108" ,"闸北区");
            put("310109" ,"虹口区");
            put("310110" ,"杨浦区");
            put("310112" ,"闵行区");
            put("310113" ,"宝山区");
            put("310114" ,"嘉定区");
            put("310115" ,"浦东新区");
            put("310116" ,"金山区");
            put("310117" ,"松江区");
            put("310118" ,"青浦区");
            put("310119" ,"南汇区");
            put("310120" ,"奉贤区");
            put("310230" ,"崇明县");
        }
    };
    public static Map<String, String> MUNICIPALITY_CQ_AREA_CODE = new HashMap<String, String>() {

        private static final long serialVersionUID = -4939695522635587830L;

        {
            put("500101" ,"万州区");
            put("500102" ,"涪陵区");
            put("500103" ,"渝中区");
            put("500104" ,"大渡口区");
            put("500105" ,"江北区");
            put("500106" ,"沙坪坝区");
            put("500107" ,"九龙坡区");
            put("500108" ,"南岸区");
            put("500109" ,"北碚区");
            put("500110" ,"万盛区");
            put("500111" ,"双桥区");
            put("500112" ,"渝北区");
            put("500113" ,"巴南区");
            put("500114" ,"黔江区");
            put("500115" ,"长寿区");
            put("500222" ,"綦江县");
            put("500223" ,"潼南县");
            put("500224" ,"铜梁县");
            put("500225" ,"大足县");
            put("500226" ,"荣昌县");
            put("500227" ,"璧山县");
            put("500228" ,"梁平县");
            put("500229" ,"城口县");
            put("500230" ,"丰都县");
            put("500231" ,"垫江县");
            put("500232" ,"武隆县");
            put("500233" ,"忠县");
            put("500234" ,"开县");
            put("500235" ,"云阳县");
            put("500236" ,"奉节县");
            put("500237" ,"巫山县");
            put("500238" ,"巫溪县");
            put("500240" ,"石柱土家族自治县");
            put("500241" ,"秀山土家族苗族自治县");
            put("500242" ,"酉阳土家族苗族自治县");
            put("500243" ,"彭水苗族土家族自治县");
            put("500381" ,"江津市");
            put("500382" ,"合川市");
            put("500383" ,"永川市");
            put("500384" ,"南川市");
        }
    };

}
