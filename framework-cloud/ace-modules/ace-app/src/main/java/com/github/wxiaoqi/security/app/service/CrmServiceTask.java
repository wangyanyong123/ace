package com.github.wxiaoqi.security.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.app.biz.*;
import com.github.wxiaoqi.security.app.util.XmlTool;
import com.github.wxiaoqi.security.app.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:34 2018/11/28
 * @Modified By:
 */
@Service
@Slf4j
public class CrmServiceTask {

	@Autowired
	private BizCrmProjectBiz crmProjectBiz;

	@Autowired
	private BizCrmCityBiz crmCityBiz;

	@Autowired
	private BizCrmFloorBiz crmFloorBiz;

	@Autowired
	private BizCrmBlockBiz crmBlockBiz;
	@Autowired
	private BizCrmHouseBiz crmHouseBiz;
	@Autowired
	private BizCrmUnitBiz crmUnitBiz;
	@Autowired
	private BizCrmBuildingBiz crmBuildingBiz;

	private final Runtime s_runtime = Runtime.getRuntime();

	public void synchDataFromCrm(String date,String[] project,String type){
		List<BizCrmProject> projectList = new ArrayList<>();
		List<BizCrmCity> cityList = new ArrayList<>();
		List<BizCrmFloor> floorList = new ArrayList<>();
		List<BizCrmBlock> blockList = new ArrayList<>();
		List<BizCrmHouse> houseList = new ArrayList<>();
		List<BizCrmUnit> unitList = new ArrayList<>();
		List<BizCrmBuilding> buildingList = new ArrayList<>();
		if(project==null || project.length==0){
		    return;
        }

        for (String pro:project) {
            try {
                StringBuilder soap = new StringBuilder();
                soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:jm=\"http://schemas.datacontract.org/2004/07/JM.MemberQuality.DataContract.WYModel\" xmlns:arr=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <tem:HouseQuery>\n" +
                        "         <tem:request>");
                if (StringUtils.isNotEmpty(date)) {
                    soap.append("<jm:lastModifyTime>" + date + "</jm:lastModifyTime>");
                }
                if (null != project && project.length > 0) {
                    soap.append("<jm:projectCodeList>");
                    soap.append("<arr:string>" + pro + "</arr:string>");
                    soap.append("</jm:projectCodeList>");
                }
                if (StringUtils.isNotEmpty(type)) {
                    soap.append("  <jm:queryType>" + type + "</jm:queryType>");
                }
                soap.append("</tem:request>\n" +
                        "      </tem:HouseQuery>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>");

//			URL url = new URL("http://172.16.124.163/WYService/BasicService.svc?wsdl");
                String urlStr = "http://211.94.93.146:8090/WYService/BasicService.svc?wsdl";
                URL url = new URL(urlStr);
                URLConnection conn = url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.setRequestProperty("SOAPAction", "http://tempuri.org/IBasicService/HouseQuery");

                log.info("请求URL:" + urlStr);
                log.info("请求参数:" + soap.toString());
                long usedMemory = (s_runtime.totalMemory() - s_runtime.freeMemory()) / 1024 / 1024;
                log.info("堆中已使用内存0:" + usedMemory);

                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
                osw.write(soap.toString());
                osw.flush();
                if (osw != null) {
                    osw.close();
                }
                StringBuilder sTotalString = new StringBuilder(1024 * 1024 * 15);
                String sCurrentLine = "";
                InputStream is = conn.getInputStream();
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(is));
                while ((sCurrentLine = l_reader.readLine()) != null) {
                    sTotalString.append(sCurrentLine);
                }
                if (is != null) {
                    is.close();
                }
                try {
                    if (sTotalString.indexOf("<a:status>1</a:status>") == -1) {
                        log.error("crm返回数据状态原始数据====>" + sTotalString.toString());
                        return;
                    }
                    usedMemory = (s_runtime.totalMemory() - s_runtime.freeMemory()) / 1024 / 1024;
                    log.info("堆中已使用内存1:" + usedMemory);
                    JSONObject jsonObject = XmlTool.documentToJSONObject(sTotalString.toString());
                    if (null != jsonObject) {
                        usedMemory = (s_runtime.totalMemory() - s_runtime.freeMemory()) / 1024 / 1024;
                        log.info("堆中已使用内存2:" + usedMemory);
                        sTotalString.delete(0, sTotalString.length());
                        System.gc();
                        usedMemory = (s_runtime.totalMemory() - s_runtime.freeMemory()) / 1024 / 1024;
                        log.info("堆中已使用内存3:" + usedMemory);
                        JSONArray jsonArr = JSONObject.parseArray(jsonObject.get("Envelope").toString());
                        JSONObject jo = jsonArr.getJSONObject(0);
                        jsonArr = JSONObject.parseArray(jo.get("Body").toString());
                        jo = jsonArr.getJSONObject(0);
                        jsonArr = JSONObject.parseArray(jo.get("HouseQueryResponse").toString());
                        jo = jsonArr.getJSONObject(0);
                        jsonArr = JSONObject.parseArray(jo.get("HouseQueryResult").toString());
                        jo = jsonArr.getJSONObject(0);
                        jsonArr = JSONObject.parseArray(jo.get("body").toString());

                        jo = null;
                        usedMemory = (s_runtime.totalMemory() - s_runtime.freeMemory()) / 1024 / 1024;
                        log.info("堆中已使用内存4:" + usedMemory);

                        JSONObject jbody = jsonArr.getJSONObject(0);
                        if (!"[{\"nil\":\"true\"}]".equals(jbody.get("projectList").toString())) {
                            JSONArray projectJson = JSONObject.parseArray(jbody.get("projectList").toString());
                            if (null != projectJson.getJSONObject(0).get("Project")) {
                                if (JSONObject.parseArray(projectJson.getJSONObject(0).get("Project").toString()).size() > 0) {
                                    projectList = JSONObject.parseArray(JSONObject.parseArray(projectJson.getJSONObject(0).get("Project").toString().replaceAll("\\[\\{\"nil\":\"true\"\\}\\]", "\"\"")).toJSONString(), BizCrmProject.class);
                                    log.info("projectList有数据====>" + projectList.size());
                                }
                            }
                        }
                        if (!"[{\"nil\":\"true\"}]".equals(jbody.get("cityList").toString())) {
                            JSONArray cityJson = JSONObject.parseArray(jbody.get("cityList").toString());
                            if (null != cityJson.getJSONObject(0).get("City")) {
                                if (JSONObject.parseArray(cityJson.getJSONObject(0).get("City").toString()).size() > 0) {
                                    cityList = JSONObject.parseArray(JSONObject.parseArray(cityJson.getJSONObject(0).get("City").toString().replaceAll("\\[\\{\"nil\":\"true\"\\}\\]", "\"\"")).toJSONString(), BizCrmCity.class);
                                    log.info("cityList有数据====>" + cityList.size());
                                }
                            }
                        }
                        if (!"[{\"nil\":\"true\"}]".equals(jbody.get("floorList").toString())) {
                            JSONArray floorJson = JSONObject.parseArray(jbody.get("floorList").toString());
                            if (null != floorJson.getJSONObject(0).get("Floor")) {
                                if (JSONObject.parseArray(floorJson.getJSONObject(0).get("Floor").toString()).size() > 0) {
                                    floorList = JSONObject.parseArray(JSONObject.parseArray(floorJson.getJSONObject(0).get("Floor").toString().replaceAll("\\[\\{\"nil\":\"true\"\\}\\]", "\"\"")).toJSONString(), BizCrmFloor.class);
                                    log.info("floorList有数据====>" + floorList.size());
                                }
                            }
                        }
                        if (!"[{\"nil\":\"true\"}]".equals(jbody.get("blockList").toString())) {
                            JSONArray blockJson = JSONObject.parseArray(jbody.get("blockList").toString());
                            if (null != blockJson.getJSONObject(0).get("Block")) {
                                if (JSONObject.parseArray(blockJson.getJSONObject(0).get("Block").toString()).size() > 0) {
                                    blockList = JSONObject.parseArray(JSONObject.parseArray(blockJson.getJSONObject(0).get("Block").toString().replaceAll("\\[\\{\"nil\":\"true\"\\}\\]", "\"\"")).toJSONString(), BizCrmBlock.class);
                                    log.info("blockList有数据====>" + blockList.size());
                                }
                            }
                        }
                        if (!"[{\"nil\":\"true\"}]".equals(jbody.get("houseList").toString())) {
                            JSONArray houseJson = JSONObject.parseArray(jbody.get("houseList").toString());
                            if (null != houseJson.getJSONObject(0).get("House")) {
                                if (JSONObject.parseArray(houseJson.getJSONObject(0).get("House").toString()).size() > 0) {
                                    houseList = JSONObject.parseArray(JSONObject.parseArray(houseJson.getJSONObject(0).get("House").toString().replaceAll("\\[\\{\"nil\":\"true\"\\}\\]", "\"\"")).toJSONString(), BizCrmHouse.class);
                                    log.info("houseList有数据====>" + houseList.size());
                                }
                            }
                        }
                        if (!"[{\"nil\":\"true\"}]".equals(jbody.get("unitList").toString())) {
                            JSONArray unitJson = JSONObject.parseArray(jbody.get("unitList").toString());
                            if (null != unitJson.getJSONObject(0).get("Unit")) {
                                if (JSONObject.parseArray(unitJson.getJSONObject(0).get("Unit").toString()).size() > 0) {
                                    unitList = JSONObject.parseArray(JSONObject.parseArray(unitJson.getJSONObject(0).get("Unit").toString().replaceAll("\\[\\{\"nil\":\"true\"\\}\\]", "\"\"")).toJSONString(), BizCrmUnit.class);
                                    log.info("unitList有数据====>" + unitList.size());
                                }
                            }
                        }
                        if (!"[{\"nil\":\"true\"}]".equals(jbody.get("buildingList").toString())) {
                            JSONArray buildingJson = JSONObject.parseArray(jbody.get("buildingList").toString());
                            if (null != buildingJson.getJSONObject(0).get("Building")) {
                                if (JSONObject.parseArray(buildingJson.getJSONObject(0).get("Building").toString()).size() > 0) {
                                    buildingList = JSONObject.parseArray(JSONObject.parseArray(buildingJson.getJSONObject(0).get("Building").toString().replaceAll("\\[\\{\"nil\":\"true\"\\}\\]", "\"\"")).toJSONString(), BizCrmBuilding.class);
                                    log.info("buildingList有数据====>" + buildingList.size());
                                }
                            }
                        }
                    } else {
                        log.error("crm返回数据解析成json为null====>" + sTotalString.toString());
                    }
                } catch (Exception e) {
                    log.error("crm返回数据解析成json失败====>" + e.toString());
                }
            } catch (Exception e) {
                log.error("crm返回数据解析成json失败====>" + e.toString());
            }
            long usedMemory = (s_runtime.totalMemory() - s_runtime.freeMemory()) / 1024 / 1024;
            log.info("堆中已使用内存5:" + usedMemory);

            if (null != cityList && cityList.size() > 0) {
                crmCityBiz.updateCity(cityList);
            }
            if (null != projectList && projectList.size() > 0) {
                crmProjectBiz.updateProject(projectList);
            }
            if (null != blockList && blockList.size() > 0) {
                crmBlockBiz.updateBlock(blockList);
            }
            if (null != buildingList && buildingList.size() > 0) {
                crmBuildingBiz.updateBuilding(buildingList);
            }
            if (null != unitList && unitList.size() > 0) {
                crmUnitBiz.updateUnit(unitList);
            }
            if (null != floorList && floorList.size() > 0) {
                crmFloorBiz.updateFloor(floorList);
            }
            if (null != houseList && houseList.size() > 0) {
                crmHouseBiz.updateHouse(houseList);
            }
            usedMemory = (s_runtime.totalMemory() - s_runtime.freeMemory()) / 1024 / 1024;
            log.info("堆中已使用内存6:" + usedMemory);
        }
	}

	public void synchDataFromCrmByProject() {
		List<BizCrmProject> crmProjectList = crmProjectBiz.selectListAll();
		for (BizCrmProject crmProject:crmProjectList) {
			synchDataFromCrm("2001-01-01",new String[]{crmProject.getProjectCode()},"HouseProperty");
		}


	}
}
