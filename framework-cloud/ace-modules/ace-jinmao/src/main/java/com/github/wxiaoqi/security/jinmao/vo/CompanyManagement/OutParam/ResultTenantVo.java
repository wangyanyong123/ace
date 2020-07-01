package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam;

import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class ResultTenantVo implements Serializable {

    @ApiModelProperty(value = "租户(公司、商户)ID")
    private String id;
    @ApiModelProperty(value = "公司地址")
    private String address;
    @ApiModelProperty(value = "企业法人")
    private String juristicPerson;
    @ApiModelProperty(value = "成立时间")
    private Date setupTime;
    @ApiModelProperty(value = "注册资本,以万元为单位")
    private String regCapital;
    @ApiModelProperty(value = "公司名称")
    private String name;
    @ApiModelProperty(value = "营业执照号")
    private String licenceNo;
    @ApiModelProperty(value = "负责人名字")
    private String contactorName;
    @ApiModelProperty(value = "负责人电话")
    private String contactTel;
    @ApiModelProperty(value = "负责人邮箱")
    private String contactEmail;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "简介")
    private String summary;
    private String cityName;

    private String tenantType;


    public List<Map<String,String>> getProjectList(){
        if(StringUtils.isEmpty(projectId)){
            return Collections.emptyList();
        }
        String[] projectIds = projectId.split(",");
        String[] projectNames = projectName.split(",");
        List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < projectIds.length ; i++) {
            Map<String,String> map = new HashMap<>();
            map.put("projectId",projectIds[i]);
            map.put("projectName",projectNames[i]);
            list.add(map);
        }

        return list;
    }

}
