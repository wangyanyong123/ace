package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizIntegralScreen;
import com.github.wxiaoqi.security.jinmao.mapper.BizIntegralScreenMapper;
import com.github.wxiaoqi.security.jinmao.vo.integralscreen.in.SaveIntegralScreenParam;
import com.github.wxiaoqi.security.jinmao.vo.integralscreen.out.IntegralScreenInfo;
import com.github.wxiaoqi.security.jinmao.vo.integralscreen.out.IntegralScreenVo;
import com.github.wxiaoqi.security.jinmao.vo.postage.out.PostageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 积分筛选表
 *
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
@Service
public class BizIntegralScreenBiz extends BusinessBiz<BizIntegralScreenMapper,BizIntegralScreen> {

    @Autowired
    private BizIntegralScreenMapper bizIntegralScreenMapper;


    /**
     * 保存积分范围
     * @param params
     * @return
     */
    public ObjectRestResponse saveIntegralScreen(SaveIntegralScreenParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(101);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        bizIntegralScreenMapper.delIntegralScreen();
        if (params.getContent().size() >= 1){
            List<Map<String, String>> content = params.getContent();
            for (int i = 0; i <= content.size() - 1 ; i++) {
                String endIntegral = content.get(i).get("endIntegral");
                String startIntegral = content.get(i).get("startIntegral");
                if ( startIntegral.equals("") || endIntegral.equals("")) {
                    msg.setStatus(101);
                    msg.setMessage("请输入正确的区间积分");
                    return msg;
                }
            }
            for (Map<String, String> map : params.getContent()) {
                BizIntegralScreen screen = new BizIntegralScreen();
                screen.setId(UUIDUtils.generateUuid());
                screen.setStartIntegral(Integer.parseInt(map.get("startIntegral")));
                screen.setEndIntegral(Integer.parseInt(map.get("endIntegral")));
                screen.setTimeStamp(new Date());
                screen.setCreateBy(BaseContextHandler.getUserID());
                screen.setCreateTime(new Date());
                bizIntegralScreenMapper.insertSelective(screen);
            }

        }
        BizIntegralScreen screen = new BizIntegralScreen();
        screen.setId(UUIDUtils.generateUuid());
        screen.setStartIntegral(Integer.parseInt(params.getStartIntegral()));
        screen.setEndIntegral(Integer.parseInt(params.getEndIntegral()));
        screen.setTimeStamp(new Date());
        screen.setCreateBy(BaseContextHandler.getUserID());
        screen.setCreateTime(new Date());
        if(bizIntegralScreenMapper.insertSelective(screen) < 0){
            msg.setStatus(101);
            msg.setMessage("保存积分范围失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询积分范围
     * @return
     */
    public List<IntegralScreenVo> getIntegralScreenInfo(){
        List<IntegralScreenVo> result = new ArrayList<>();
        List<IntegralScreenInfo> screenInfoList =  bizIntegralScreenMapper.selectIntegralScreenInfo();
        IntegralScreenVo integralScreenVo = new IntegralScreenVo();
        List<IntegralScreenInfo> screenList = new ArrayList<>();
        if (screenInfoList != null && screenInfoList.size() > 0) {
            for (IntegralScreenInfo screenInfo : screenInfoList) {
                if (! screenInfo.getEndIntegral().equals("-1")) {
                    screenList.add(screenInfo);
                }else {
                    integralScreenVo.setStartIntegral(screenInfo.getStartIntegral());
                    integralScreenVo.setEndIntegral(screenInfo.getEndIntegral());
                    integralScreenVo.setSid(screenInfo.getId());
                }
            }
            integralScreenVo.setScreenList(screenList);

        }else {
            integralScreenVo.setScreenList(new ArrayList<>());
            integralScreenVo.setStartIntegral("0");
            integralScreenVo.setEndIntegral("-1");
        }
        result.add(integralScreenVo);
        return result;
    }





}