package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizAccountStatement;
import com.github.wxiaoqi.security.jinmao.entity.BizCredentials;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizAccountStatementMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizCredentialsMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.statement.in.AddParam;
import com.github.wxiaoqi.security.jinmao.vo.statement.in.UpdateParam;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 *
 * @author huangxl
 * @Date 2019-03-19 15:54:18
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizAccountStatementBiz extends BusinessBiz<BizAccountStatementMapper,BizAccountStatement> {
    private Logger logger = LoggerFactory.getLogger(BizAccountStatementBiz.class);
    @Autowired
    private BizAccountStatementMapper bizAccountStatementMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizCredentialsMapper bizCredentialsMapper;
    @Autowired
    private ToolFegin toolFeign;

    /**
     * 查询账单列表
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param balanceStatus
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public List<StatementVo> getBillList(String tenantId,String startTime,String endTime,String balanceStatus,
                                         String projectId,Integer page,Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        List<StatementVo> billList = bizAccountStatementMapper.selectBillList(tenantId, startTime, endTime, balanceStatus, projectId, startIndex, limit);
        for (StatementVo vo : billList){
            //查询项目名称
            List<String> projectNames = baseTenantProjectMapper.selectProjectNamesById(tenantId);
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder sb = new StringBuilder();
                for (String project:projectNames){
                    sb.append(project + ",");
                }
                projectName = sb.substring(0, sb.length()-1);
                vo.setProjectName(projectName);
            }
        }
        return billList;
    }


    /**
     * 查询账单数量
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param balanceStatus
     * @param projectId
     * @return
     */
    public int selectBillCount(String tenantId,String startTime,String endTime,String balanceStatus,String projectId){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        return bizAccountStatementMapper.selectBillCount(tenantId, startTime, endTime, balanceStatus, projectId);
    }


    /**
     * 导出excel
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param balanceStatus
     * @param projectId
     * @return
     */
    public ObjectRestResponse exportExcel(String tenantId,String startTime,String endTime,String balanceStatus,String projectId){
        ObjectRestResponse msg = new ObjectRestResponse();
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        //获取账单列表
        List<StatementVo> billList = bizAccountStatementMapper.selectExportBillList(tenantId, startTime, endTime, balanceStatus, projectId);
        for (StatementVo vo : billList){
            //查询项目名称
            List<String> projectNames = baseTenantProjectMapper.selectProjectNamesById(tenantId);
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder sb = new StringBuilder();
                for (String project:projectNames){
                    sb.append(project + ",");
                }
                projectName = sb.substring(0, sb.length()-1);
                vo.setProjectName(projectName);
            }
        }
        if(billList == null || billList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (billList != null && billList.size() > 0) {
            for (int i = 0; i < billList.size(); i++) {
                StatementVo temp = mapper.convertValue(billList.get(i), StatementVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"账单编号","商户账号","商户名称","服务范围","结算周期","结算金额","结算状态"};
        String[] keys = {"billNumber","account","tenantName","projectName","settlementCycle","balanceMoney","balanceStatus"};
        String fileName = "账单.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }


    /**
     *查询账单详情
     * @param tenantId
     * @return
     */
    public ObjectRestResponse<BillInfo> getBillDetail(String id,String tenantId){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        BillInfo billInfo = bizAccountStatementMapper.selectBillInfo(id, tenantId);
        if(billInfo == null){
            billInfo = new BillInfo();
        }else{
            //商品精选图片
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            if(billInfo.getBalanceImg() != null){
                String[] selectLImgs = billInfo.getBalanceImg().split(",");
                for(String url : selectLImgs){
                    imgInfo.setUrl(url);
                    list.add(imgInfo);
                }
            }
            billInfo.setBalanceImgList(list);
        }
        return ObjectRestResponse.ok(billInfo);
    }

    /**
     * 查询账单明细列表(已完成与已退款)
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    public List<BillDetailList> getBillDetailList(String tenantId, String startTime,String endTime,Integer page,Integer limit){
        DecimalFormat df =new  DecimalFormat("0.00");
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        List<BillDetailList> billDetailList = bizAccountStatementMapper.selectBillDetail(tenantId, startTime, endTime,startIndex,limit);
        if(billDetailList == null || billDetailList.size() == 0){
            billDetailList = new ArrayList<>();
        }else{
            for(BillDetailList detailList : billDetailList){
                detailList.setCost(df.format(Double.parseDouble(detailList.getCost())));
            }
        }
        return billDetailList;
    }

    /**
     * 查询账单明细列表(所有支付状态)
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    public List<BillDetailList> getBillDetailAllList(String tenantId, String startTime,String endTime,Integer page,Integer limit){
        DecimalFormat df =new  DecimalFormat("0.00");
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        List<BillDetailList> billDetailList = bizAccountStatementMapper.selectBillAllDetail(tenantId, startTime, endTime,startIndex,limit);
        if(billDetailList == null || billDetailList.size() == 0){
            billDetailList = new ArrayList<>();
        }else{
            for(BillDetailList detailList : billDetailList){
                detailList.setCost(df.format(Double.parseDouble(detailList.getCost())));
            }
        }
        return billDetailList;
    }


    public int selectBillDetailCount(String tenantId, String startTime,String endTime){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        return bizAccountStatementMapper.selectBillDetailCount(tenantId, startTime, endTime);
    }

    public int selectBillDetailAllCount(String tenantId, String startTime,String endTime){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        return bizAccountStatementMapper.selectBillDetailAllCount(tenantId, startTime, endTime);
    }


    /**
     * 导出账单明细excel
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    public ObjectRestResponse exportBillDetailExcel(String tenantId,String startTime,String endTime,String status){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        if(!"3".equals(type)){
            tenantId = BaseContextHandler.getTenantID();
        }
        //获取账单列表
        List<BillDetailList> billList = null;
        if("1".equals(status)){
            billList =  bizAccountStatementMapper.selectExportBillDetail(tenantId, startTime, endTime);
        }else{
            billList = bizAccountStatementMapper.selectExportBillDetailAll(tenantId, startTime, endTime);
        }
        for(BillDetailList detailList : billList){
            detailList.setCost(df.format(Double.parseDouble(detailList.getCost())));
        }
        if(billList == null || billList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (billList != null && billList.size() > 0) {
            for (int i = 0; i < billList.size(); i++) {
                BillDetailList temp = mapper.convertValue(billList.get(i), BillDetailList.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
                dataList.add(dataMap);
            }
        }
        String[] titles = {"订单编号","商品名称","订单完成时间","客户","联系电话","订单金额","支付类型","订单状态"};
        String[] keys = {"subCode","title","finishWoTime","contactName","contactTel","cost","payType","subscribeStatus"};
        String fileName ="账单明细.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }





    /**
     * 账单操作
     * @param param
     * @return
     */
    public ObjectRestResponse updateBillStatus(UpdateParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        StringBuilder sb = new StringBuilder();
        String balanceImgs = "";
        if("2".equals(param.getStatus())){
            if(param.getBalanceImgList() == null || param.getBalanceImgList().size() == 0){
                msg.setStatus(201);
                msg.setMessage("结算图片不能为空!");
                return msg;
            }
            for(ImgInfo temp: param.getBalanceImgList()){
                sb.append(temp.getUrl()+",");
            }
            balanceImgs = sb.substring(0,sb.length()-1);
            if(StringUtils.isNotEmpty(balanceImgs)){
                ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(balanceImgs, DocPathConstant.SHOP);
                if(objectRestResponse.getStatus()==200){
                    balanceImgs = objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData();
                }
            }
        }
        if(bizAccountStatementMapper.updateBalanceStatus(param.getStatus(),balanceImgs,param.getId()) < 0){
            msg.setStatus(101);
            msg.setMessage("操作失败！");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }


    /**
     * 插入付款凭证
     * @param param
     * @return
     */
    public ObjectRestResponse addCredentials(AddParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        String settlement = bizAccountStatementMapper.selectSettlementById(param.getId());
        String accountNum = "SW"+System.currentTimeMillis();
        BizCredentials credentials = new BizCredentials();
        credentials.setId(UUIDUtils.generateUuid());
        credentials.setAccountId(param.getId());
        credentials.setAccountNum(accountNum);
        credentials.setTenantId(param.getTenantId());
        credentials.setRemark(settlement+"商城订单结算");
        credentials.setIsPrint("1");
        credentials.setCreateperson("敏睿（深圳）数字园区技术有限公司");
        credentials.setCreateBy(BaseContextHandler.getUserID());
        credentials.setCreateTime(new Date());
        credentials.setTimeStamp(new Date());
        if(bizCredentialsMapper.insertSelective(credentials) < 0){
            msg.setStatus(101);
            msg.setMessage("插入付款凭证数据失败！");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }


    /**
     * 查询凭证信息
     * @param id
     * @return
     */
    public ObjectRestResponse<AccountInfo> getAccountInfo(String id){
        AccountInfo accountInfo = bizAccountStatementMapper.selectAccountInfoById(id);
        if(accountInfo == null){
            accountInfo = new AccountInfo();
        }
        return ObjectRestResponse.ok(accountInfo);
    }

















    /**
     * 统计各商户收益金额
     * @return
     */
     @Scheduled(cron = "0 0 3 1 * ?")
    public void addStatisticsData() throws ParseException {

	   try {
		   //加锁
		   long time = System.currentTimeMillis() + TIMEOUT;
		   if(!lock("addStatisticsData",String.valueOf(time))){
			   logger.error("正在统计各商户收益金额");
			   return ;
		   }

           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           DecimalFormat df =new  DecimalFormat("0.00");
           List<StatisticsVo> statisticsVoList = bizAccountStatementMapper.selectTenantMoneyById();
           if(statisticsVoList == null || statisticsVoList.size() == 0){
               logger.info("暂无统计数据!");
           }else{
               for (StatisticsVo vo : statisticsVoList){
                   ObjectRestResponse objectRestResponse = codeFeign.getCode("Bill","B","6","0");
                   logger.info("生成账单编码处理结果："+objectRestResponse.getData());
                   BizAccountStatement statement = new BizAccountStatement();
                   statement.setId(UUIDUtils.generateUuid());
                   statement.setTenantId(vo.getId());
                   if(objectRestResponse.getStatus()==200){
                       statement.setBillNumber(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
                   }
                   statement.setSettlementCycleStart(sdf.parse((vo.getDateTime()+"-01")));
                   statement.setSettlementCycleEnd(sdf.parse(getLastDayOfMonth(vo.getDateTime())));
                   statement.setBalanceMoney(df.format(Double.parseDouble(vo.getActualCost())));
                   if(bizAccountStatementMapper.insertSelective(statement) < 0){
                       logger.info("统计失败!");
                   }
               }
           }
		   //解锁
		   unlock("addStatisticsData",String.valueOf(time));

	   }catch (Exception e){
		   logger.error("获取编码异常："+e);
		   e.printStackTrace();
	   }

    }



    /**
     * 获取指定年月的最后一天
     * @param ym
     * @return
     */
    public static String getLastDayOfMonth(String ym) {
        String arr[] = ym.split("-");

        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);

        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }



    private static final int TIMEOUT = 10*1000;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public boolean lock(String key,String value){
		if(stringRedisTemplate.opsForValue().setIfAbsent(key,value)){
			return true;
		}

		String currentValue = stringRedisTemplate.opsForValue().get(key);
		if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
			String oldValue =stringRedisTemplate.opsForValue().getAndSet(key,value);
			if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue) ){
				return true;
			}
		}
		return false;
	}

	public void unlock(String key,String value){
		try {
			String currentValue = stringRedisTemplate.opsForValue().get(key);
			if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value) ){
				//删除key
				stringRedisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			logger.error("[Redis分布式锁] 解锁出现异常了，{}",e);
		}
	}


}