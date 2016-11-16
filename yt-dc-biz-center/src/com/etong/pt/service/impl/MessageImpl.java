package com.etong.pt.service.impl;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.etong.pt.dao.OrderDao;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.Cache;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2015/11/9.
 */
public class MessageImpl {
    public static final String SMS_TOPIC = "orderPayCallbackMessage";
    private OrderDao orderDao;
    private Cache cacheClient;
    private String namesrvAddr;//消息中心注册地址


    public void setCacheClient(Cache cacheClient) {
        this.cacheClient = cacheClient;
    }
    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @PostConstruct
    public void init() {
        this.messageSave();
    }

    public PtResult messageSave() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MessageConsumer");
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe(SMS_TOPIC,"push");
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerInstance(orderDao,cacheClient));
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }


}
