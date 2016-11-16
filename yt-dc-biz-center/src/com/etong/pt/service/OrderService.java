package com.etong.pt.service;

import com.etong.pt.data.order.Order;
import com.etong.pt.utility.PtResult;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public interface OrderService {
    PtResult add(Order record);
    PtResult getById(Long id);
    PtResult getListByUserId(Integer userId);
    PtResult findByParam(Integer carId, String name, String phone,Long userId,String appId, Integer status,Integer type, Integer page, Integer pageSize);
    PtResult editWithInquire(Long orderId, Double bottomPrice, String agency, String salesman, String salePhone, Long operatorId);
    PtResult editWithPay(Long orderId, Double payments, Integer payMode, Integer payItem);
    PtResult modifyOrder(Order order);
    PtResult bargainOrderPrice(Long orderId,Boolean bargain,String devicecode);
    PtResult modifyShareStatus(Long orderId,Boolean wechatshare,Double wdownPrice,Boolean blogshare,Double bdownPrice,Boolean baidushare,Double ddownPrice);
    PtResult getOrderListRequest(Integer status,Integer type,String name, String phone, String appId,Integer start,Integer limit);
    PtResult getCarorderListStatus(Long userId,String appId,List<Integer> status);
    PtResult revoke(Long id);
}
