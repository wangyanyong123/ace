package com.github.wxiaoqi.security.api.vo.order.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WoListForWebVo implements Serializable {

	private static final long serialVersionUID = -8353235804415215936L;

	private String id;

	//标题
	@ApiModelProperty(value = "标题")
	private String title;

	//工单编码
	@ApiModelProperty(value = "工单编码")
	private String woCode;

	//工单编码
	@ApiModelProperty(value = "工单编码")
	private String crmWoCode;


	//工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
	@ApiModelProperty(value = "工单状态")
	private String woStatus;

	private String woStatusStr;
	@ApiModelProperty(value = "工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)")
	private String woType;

	@ApiModelProperty(value = "客户联系人名称")
	private String contactName;

	@ApiModelProperty(value = "客户联系人电话")
	private String contactTel;

	@ApiModelProperty(value = "地址")
	private String addr;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "工单处理人")
	private String handleBy;

	@ApiModelProperty(value = "工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统、4-FM系统)")
	private String comeFrom;
	@ApiModelProperty(value = "工单来源渠道字段")
	private String comeFromStr;

	@ApiModelProperty(value = "工单创建时间")
	private String createTime;

	@ApiModelProperty(value = "工单接单时间")
	private String receiveWoTime;

	@ApiModelProperty(value = "工单开始处理时间")
	private String startProcessTime;

	@ApiModelProperty(value = "工单完成时间")
	private String finishWoTime;

	@ApiModelProperty(value = "是否代客，代客-1 非代客-0")
	private String valet;

	private String valetStr;
	@ApiModelProperty(value = "代客人姓名")
	private String publishName;

	@ApiModelProperty(value = "代客人手机号")
	private String publishTel;

	@ApiModelProperty(value = "当前工单工序")
	private String processId;

	@ApiModelProperty(value = "是否可以转单(0-不可以，1-可以)")
	private String isTurn;
	@ApiModelProperty(value = "当前工单处理人")
	private String handleId;
    //图片id,多张图片逗号分隔
    private String imgId;

    @ApiModelProperty(value = "同步CRM状态")
	private String crmSyncFlag;
	private String crmSyncFlagStr;
	private String categoryName;
	private String categoryCode;
    //图片路径
    @ApiModelProperty(value = "图片路径")
    private List<String> imgList;

    public List<ImgInfo> getImgList(){
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(imgId)){
            String[] imgArrayIds =new String[] { imgId };
            if(imgId.indexOf(",")!=-1){
                imgArrayIds=imgId.split(",");
            }
            for (String url : imgArrayIds){
                ImgInfo imgInfo = new ImgInfo();
                imgInfo.setUrl(url);
                list.add(imgInfo);
            }
        }
        return list;
    }


	public String getWoStatusStr(){
		String woStatusStr = "";
		if("00".equals(woStatus)){
			woStatusStr = "待系统受理";
		}else if("01".equals(woStatus)){
			woStatusStr = "待接受";
		}else if("02".equals(woStatus)){
			woStatusStr = "已接受";
		}else if("03".equals(woStatus)){
			woStatusStr = "处理中";
		}else if("04".equals(woStatus)){
			woStatusStr = "暂停中";
		}else if("05".equals(woStatus)){
			woStatusStr = "已完成";
		}else if("06".equals(woStatus)){
			woStatusStr = "已取消";
		}else if("07".equals(woStatus)){
			woStatusStr = "已取消";
		}
		return woStatusStr;
	}
	//1-回家APP、2-金小茂APP、3-CRM系统、4-WEB端
	public String getComeFromStr() {
		String comeFromStr = "";
		if ("1".equals(comeFrom)) {
			comeFromStr = "回家APP";
		} else if ("2".equals(comeFrom)) {
			comeFromStr = "金小茂APP";
		} else if ("3".equals(comeFrom)) {
			comeFromStr = "CRM系统";
		}else if("4".equals(comeFrom)){
			comeFromStr = "WEB端";
		}
		return comeFromStr;
	}

	public String getCrmSyncFlagStr() {
		String crmSyncFlagStr = "";
		if ("0".equals(crmSyncFlag)) {
			crmSyncFlagStr = "";
		} else if ("1".equals(crmSyncFlag)) {
			crmSyncFlagStr = "已同步";
		} else if ("2".equals(crmSyncFlag)) {
			crmSyncFlagStr = "同步失败";
		}
		return crmSyncFlagStr;
	}

	public String getValetStr() {
		String valetStr = "";
		if("0".equals(valet)){
			valetStr = "非代客";
		}else if("1".equals(valet)){
			valetStr = "代客";
		}
		return valetStr;
	}
}
