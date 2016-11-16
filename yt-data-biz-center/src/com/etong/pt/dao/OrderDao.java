package com.etong.pt.dao;

import com.etong.pt.data.order.Order;
import com.etong.pt.utility.PtResult;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public interface OrderDao {
    PtResult getById(Long id);
    PtResult getListByUserId(Integer userId);
    PtResult findByParam(Order param, Integer page, Integer pageSize);
    PtResult add(Order record);
    PtResult edit(Order record);
    PtResult remove(Long id);
    PtResult getOrderListRequest(Integer status,Integer type,String name, String phone,
                                 String appId,Integer start,Integer limit);
    //多状态返回列表
    PtResult getCarorderListStatus(Long userId,String appId,List<Integer> status);
}
