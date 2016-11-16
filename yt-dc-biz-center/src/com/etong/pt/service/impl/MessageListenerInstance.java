package com.etong.pt.service.impl;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.etong.pt.constants.EnumConstant;
import com.etong.pt.dao.OrderDao;
import com.etong.pt.data.order.Order;
import com.etong.pt.util.AlipayMessage;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.pt.utility.SerializeHelper;
import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import org.apache.log4j.Logger;

import javax.cache.CacheException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2015/11/9.
 */
public class MessageListenerInstance implements MessageListenerConcurrently {
    private Logger logger=Logger.getLogger(MessageListenerInstance.class);
    private OrderDao orderDao;
    private Cache cacheClient;

    public MessageListenerInstance(OrderDao orderDao,Cache cacheClient ) {
        this.orderDao = orderDao;
        this.cacheClient=cacheClient;
    }
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt msg : list) {
            String msgId = msg.getKeys();
            PtResult checkResult=this.getfromCache(msgId);
            if (checkResult.isSucceed()) {
                logger.info("该消息为已消费消息，出现重复消费情况，系统已做处理，消息key为："+msgId);
                continue;
            }
            //获取消息内容
            byte[] orderInfo = msg.getBody();
            if (orderInfo.length < 1) {
                continue;
            }
            logger.info("message come in...");
            //消费消息体
            AlipayMessage alipayMessage = null;
            try {
                alipayMessage = SerializeHelper.byteToObject(orderInfo);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            logger.info("Message  received:||"+alipayMessage.toString());
            Order order = new Order();
            order.setF_ofid(alipayMessage.getOrderId());
            order.setF_paycode(alipayMessage.getPayCode());
            order.setF_status(EnumConstant.OrderStatus.PAY.getValue());
            order.setF_payitem(alipayMessage.getPayItem());
            order.setF_paymode(alipayMessage.getPayType());
            order.setF_paytime((int) (new Date().getTime()/1000));
            PtResult ptResult=orderDao.edit(order);
            if (!ptResult.isSucceed()) {
                logger.info(new Date().toString()+"消息key为："+msgId+",订单ID为："+"消费失败，订单状态修改失败："+ptResult.getPtError()+"||稍后将重试！");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }else {
                logger.info(new Date().toString()+"订单信息修改成功");
                try {
                    cacheClient.set(msgId,3600,msgId,SerializationType.PROVIDER);
                } catch (TimeoutException e) {
                    logger.error("订单ID缓存记录，因超时添加失败,消息key为"+msgId+"订单ID为："+alipayMessage.getOrderId());
                } catch (com.google.code.ssm.providers.CacheException e) {
                    logger.error("订单ID缓存记录，因服务异常添加失败,消息key为"+msgId+"，订单ID为："+alipayMessage.getOrderId());
                }
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
    public PtResult getfromCache(String key){
        try {
            Object object=cacheClient.get(key, SerializationType.PROVIDER);
            if (object==null){
                return new PtResult(PtCommonError.PT_ERROR_NODATA,null,null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,object);
        } catch (TimeoutException e) {
            logger.error("访问缓存超时|"+e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_MEMCACHE,e.getMessage(),null);
        } catch (CacheException e) {
            return new PtResult(PtCommonError.PT_ERROR_MEMCACHE,e.getMessage(),null);
        } catch (com.google.code.ssm.providers.CacheException e) {
            return new PtResult(PtCommonError.PT_ERROR_MEMCACHE,e.getMessage(),null);
        }
    }
}
