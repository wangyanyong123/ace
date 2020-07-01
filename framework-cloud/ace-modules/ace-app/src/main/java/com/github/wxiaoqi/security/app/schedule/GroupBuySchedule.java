package com.github.wxiaoqi.security.app.schedule;

import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.DoOperateByTypeVo;
import com.github.wxiaoqi.security.app.biz.BizSubscribeWoBiz;
import com.github.wxiaoqi.security.app.entity.BizProduct;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubscribeWoMapper;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时查询团购产品，当团购时间结束，如果未达到成团数，自动取消并退款
 *
 * @author huangxl
 */
@Configuration
@Slf4j
@EnableScheduling
public class GroupBuySchedule {

    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
    @Autowired
    private BizSubscribeWoBiz bizSubscribeWoBiz;

    private static boolean flag = true;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void refreshNotCompleteGroupBuyProduct() {
        String busId = BusinessConstant.getGroupBuyingBusId();
        if(flag){
            flag = false;
            try {
                //查询已结束但未成团的团购产品
                List<BizProduct> bizProductList = bizProductMapper.selectNotCompleteGroupBuyProduct(busId);
                if(bizProductList!=null && bizProductList.size()>0){
                    for (BizProduct bizProduct :bizProductList) {
                        String productId = bizProduct.getId();
                        //获取未成团但已支付的订单
                        List<Map<String, Object>> waitGroupBuySubList = bizSubscribeWoMapper.getNotCompleteGroupBuySubList(productId);
                        if (waitGroupBuySubList != null && waitGroupBuySubList.size() > 0) {
                            int count = 0;
                            for (Map<String, Object> groupSub : waitGroupBuySubList) {
                                String groupSubId = groupSub.get("subId") == null ? "" : (String) groupSub.get("subId");
                                //订单发起退款流程
                                DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
                                doOperateByTypeVo.setId(groupSubId);
                                doOperateByTypeVo.setOperateType(OperateConstants.OperateType.REFUND.toString());
                                ObjectRestResponse restResponse = bizSubscribeWoBiz.doOperateByType(doOperateByTypeVo);
                                if(restResponse!=null && restResponse.getStatus()==200){
                                    log.info("团购产品订单发起退款流程处理成功："+productId);
                                    count++;
                                }else {
                                    log.error("团购产品订单发起退款流程处理失败productId："+productId+",结果："+restResponse.getMessage());
                                }
                            }
                            if(waitGroupBuySubList.size()==count){
                                //更新成团标识为已成团
                                bizProduct.setIsGroupFlag("1");
                                bizProduct.setModifyTime(new Date());
                                bizProductMapper.updateByPrimaryKeySelective(bizProduct);
                                log.info("已更新该团购产品的成团标志："+productId);
                            }else{
                                log.error("该团购产品没有已支付的订单处理有失败");
                            }
                        }else{
                            //更新成团标识为已成团
                            bizProduct.setIsGroupFlag("1");
                            bizProduct.setModifyTime(new Date());
                            bizProductMapper.updateByPrimaryKeySelective(bizProduct);
                            log.info("该团购产品没有已支付的订单productId："+productId);
                        }
                    }
                }else{
                    log.info("没有已结束但未成团的团购产品");
                }
            }catch (Exception e){
                log.error("定时任务调度处理已结束但未成团的团购产品订单异常"+e.getMessage());
            } finally {
                flag = true;
            }

        }

    }


}
