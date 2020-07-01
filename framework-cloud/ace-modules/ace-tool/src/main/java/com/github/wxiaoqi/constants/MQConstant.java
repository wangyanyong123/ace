package com.github.wxiaoqi.constants;

/**
 * Rabbit消息队列相关常量
 */
public interface MQConstant {
 
    //秒杀交换机
    String SECKILL_DELAYED_EXCHANGE = "seckill.delayed.change";
    String SECKILL_DELAYED_REPEAT_EXCHANGE = "seckill.delayed.repeat.change";
 
    //秒杀订单超时死信队列（不需要处理）
    String SECKILL_ORDER_DELAYED_DEAD_QUEUE = "seckill.order.delayed.dead.queue";
 
    //秒杀订单超时转发队列（需要处理）
    String SECKILL_ORDER_DELAYED_QUEUE = "seckill.order.delayed.queue";

    //秒杀订单超时重试死信队列（不需要处理）
    String SECKILL_ORDER_DELAYED_REPEAT_DEAD_QUEUE = "seckill.order.delayed.repeat.dead.queue";
 
    //秒杀订单超时重试队列（需要处理）
   String SECKILL_ORDER_DELAYED_REPEAT_QUEUE = "seckill.order.delayed.repeat.queue";
 
}