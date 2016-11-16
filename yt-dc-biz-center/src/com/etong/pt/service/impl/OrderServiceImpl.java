package com.etong.pt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.data.auto.carparam.CarParam;
import com.etong.dc.auto.service.AutoService;
import com.etong.pt.constants.EnumConstant;
import com.etong.pt.constants.SystemConstant;
import com.etong.pt.dao.BargainDao;
import com.etong.pt.dao.OrderDao;
import com.etong.pt.data.bargain.BargainInfo;
import com.etong.pt.data.order.Order;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.service.OrderService;
import com.etong.pt.user.PtUserService;
import com.etong.pt.utility.MessageHelper;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.pt.utility.SmsHelper;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public class OrderServiceImpl implements OrderService {

    private Logger logger=Logger.getLogger(OrderServiceImpl.class);
    public static final String MESSAGE_TOPIC = "orderMonitorMessage";
    private OrderDao orderDao;
    private BargainDao bargainDao;
    private PtUserService ptUserService;
    private SmsHelper smsHelper;//短信提交至消息中心，订单添加时发生
    private String sendPhone;
    private String smsContent;
    private AutoService autoService;

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public void setSmsHelper(SmsHelper smsHelper) {
        this.smsHelper = smsHelper;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public void setPtUserService(PtUserService ptUserService) {
        this.ptUserService = ptUserService;
    }

    public void setBargainDao(BargainDao bargainDao) {
        this.bargainDao = bargainDao;
    }

    public void setAutoService(AutoService autoService) {
        this.autoService = autoService;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public PtResult add(Order record) {
        record.setF_createtime((int) (new Date().getTime() / 1000));
        record.setF_operatetime((int) (new Date().getTime() / 1000));
        record.setF_status(EnumConstant.OrderStatus.INQUIRE.getValue());
        record.setF_wechatshare(false);
        record.setF_baidushare(false);
        record.setF_blogshare(false);
        record.setF_derate(new Double(0));
        PtResult ptResult = orderDao.add(record);
        Long orderId=ptResult.getObject();
        if (!ptResult.isSucceed()) {
            return ptResult;
        }
        MessageHelper messageHelper=new MessageHelper();
        ptResult=messageHelper.sendMessageToServer(record,MESSAGE_TOPIC,"push");
        if (!ptResult.isSucceed()){
            logger.info(new Date().toString()+"|订单生成消息提交消息中心失败,客户电话："+record.getF_phone());
        }

        ptResult=smsHelper.sendMessageRequest(sendPhone, smsContent + record.getF_phone(), "10001", null);
        if (!ptResult.isSucceed()){
            logger.info(new Date().toString()+"|订单生成消息短信提醒失败,客户电话:"+record.getF_phone());
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("orderId",orderId);
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult getById(Long id) {
        PtResult ptResult = orderDao.getById(id);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }
        Order record = ptResult.getObject();
        ptResult.setObject(this.doRevoke(record));
        Map<String, Object> objectMap = new HashMap<>();
        if (record.getF_cartypeid() != null) {
            if (record.getF_cartypeid() != null) {
                PtResult autoResult = autoService.getCarInfo(record.getF_cartypeid());
                if (autoResult.isSucceed()) {
                    CarParam carParam = autoResult.getObject();
                    objectMap.put("guidePrice", carParam.getPrices());
                    objectMap.put("title", carParam.getFullname());
                    objectMap.put("id", carParam.getVid());
                    objectMap.put("image", carParam.getImage());
                }
            }
        }
        int currenttime = (int) (new Date().getTime() / 1000);
        if (record.getF_status() == EnumConstant.OrderStatus.INQUIRE.getValue()) {//报价剩余时间
            record.setF_remiantime(SystemConstant.MAX_WAIT_TIME / 2 - (currenttime - record.getF_createtime()));
        } else if (record.getF_status() == EnumConstant.OrderStatus.OFFER.getValue()) {//支付剩余时间
            record.setF_remiantime(SystemConstant.MAX_WAIT_TIME - (currenttime - record.getF_operatetime()));
        }
        if (record.getF_wechatshare() && record.getF_wechattime() != null) {
            record.setWechatRemainTime(SystemConstant.MAX_WAIT_TIME / 4 - (currenttime - record.getF_wechattime()));
        }
        if (record.getF_blogshare() && record.getF_blogtime() != null) {
            record.setBlogRemainTime(SystemConstant.MAX_WAIT_TIME / 4 - (currenttime - record.getF_blogtime()));
        }
        if (record.getF_baidushare() && record.getF_baidutime() != null) {
            record.setBaiduRemainTime(SystemConstant.MAX_WAIT_TIME / 4 - (currenttime - record.getF_baidutime()));
        }
        objectMap.put("order", record);
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult getListByUserId(Integer userId) {
        PtResult ptResult = orderDao.getListByUserId(userId);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Order> list = ptResult.getObject();
        for (Order record : list) {
            this.doRevoke(record);
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("orderList", list);
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult findByParam(Integer carId, String name, String phone, Long userId, String appId, Integer status, Integer type,
                                 Integer page, Integer pageSize) {
        Order param = new Order();
        param.setF_cartypeid(carId);
        param.setF_name(name);
        param.setF_phone(phone);

        param.setF_mid(userId);
        param.setF_stid(appId);
        param.setF_status(status);

        param.setF_type(type);
        PtResult ptResult = orderDao.findByParam(param, page, pageSize);

        JSONArray jsonArray = new JSONArray();
        if (ptResult.isSucceed()) {
            Map<String, Object> objectMap = ptResult.getObject();
            List<Order> list = (List<Order>) objectMap.get("list");

            for (Order record : list) {
                this.doRevoke(record);
                JSONObject jsonObject = (JSONObject) JSON.toJSON(record);
                if (record.getF_cartypeid() != null) {
                    PtResult autoResult = autoService.getCarInfo(record.getF_cartypeid());
                    if (autoResult.isSucceed()) {
                        CarParam carParam = autoResult.getObject();
                        jsonObject.put("guidePrice", carParam.getPrices());
                        jsonObject.put("title", carParam.getFullname());
                        jsonObject.put("id", carParam.getVid());
                        jsonObject.put("image", carParam.getImage());
                    }
                }
                jsonArray.add(jsonObject);
            }
            objectMap.remove("list");
            objectMap.put("orderlist", jsonArray);
            ptResult.setObject(objectMap);
        }
        return ptResult;
    }

    @Override
    public PtResult editWithInquire(Long orderId, Double bottomPrice, String agency, String salesman, String salePhone, Long operatorId) {
        Order record = new Order();
        record.setF_ofid(orderId);
        return orderDao.edit(record);
    }

    @Override
    public PtResult editWithPay(Long orderId, Double payments, Integer payMode, Integer payItem) {
        Order record = new Order();
        record.setF_ofid(orderId);
        return orderDao.edit(record);
    }

    @Override
    public PtResult modifyOrder(Order order) {
        order.setF_operatetime((int) (new Date().getTime()/1000));
        PtResult ptResult = orderDao.edit(order);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("id", ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult bargainOrderPrice(Long orderId, Boolean bargain, String devicecode) {

        PtResult bresult = bargainDao.getBargainInfoListRequest(orderId, devicecode, 0, 5);
        if (bresult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_SUBMIT, "不能重复砍价", null);
        }

        PtResult ptResult = orderDao.getById(orderId);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }
        Order orderInfo = ptResult.getObject();
        Double derate = orderInfo.getF_derate();

        if (derate >= SystemConstant.MAX_DOWNPRICE) {//不可以砍价
            return new PtResult(PtCommonError.PT_ERROR_SUBMIT, "砍价失败,已达砍价上限", null);
        }

        int bargainPrice = 0;//当次所砍价格
        BargainInfo bargainInfo = new BargainInfo();
        bargainInfo.setF_ofcode(orderInfo.getF_ofcode());
        bargainInfo.setF_operatetime((int) (new Date().getTime() / 1000));
        bargainInfo.setF_devicecode(devicecode);
        bargainInfo.setF_ofid(orderId);

        Random rd = new Random();//5
        int randomNum = rd.nextInt(SystemConstant.MIN_BARGAINPRICE + 1) + SystemConstant.MIN_BARGAINPRICE;
        if (derate + randomNum >= SystemConstant.MAX_DOWNPRICE) {
            bargainPrice = SystemConstant.MAX_DOWNPRICE - derate.intValue();
            bargainInfo.setF_derate(new BigDecimal(bargainPrice));
        } else {
            bargainPrice = randomNum;
            bargainInfo.setF_derate(new BigDecimal(randomNum));
        }

        PtResult bargainResult = bargainDao.addBargainInfoRequest(bargainInfo);
        if (!bargainResult.isSucceed()) {
            return ptResult;
        }

        Order order = new Order();
        order.setF_ofid(orderId);
        order.setF_derate(derate + bargainPrice);
        PtResult result = orderDao.edit(order);
        if (!result.isSucceed()) {
            return result;
        }
        Map<String, Object> objectMap = new HashMap<>();
        PtResult bargainRecordResult = bargainDao.getBargainInfoListRequest(orderId, null, 0, 5);
        if (bargainRecordResult.isSucceed()) {
            objectMap.put("bargainRecords", bargainRecordResult.getObject());
        }
        objectMap.put("orderId", orderId);
        objectMap.put("downPrice", bargainPrice);
        objectMap.put("currentDerate", derate + bargainPrice);

        bargainResult.setObject(objectMap);
        return bargainResult;
    }

    @Override
    public PtResult modifyShareStatus(Long orderId, Boolean wechatshare, Double wdownPrice, Boolean blogshare, Double bdownPrice, Boolean baidushare, Double ddownPrice) {
        PtResult ptResult = orderDao.getById(orderId);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }
        Order orderInfo = ptResult.getObject();
        Double derate = orderInfo.getF_derate();
        Order order = new Order();
        order.setF_ofid(orderInfo.getF_ofid());
        if (wechatshare != null && wdownPrice != null) {
            if (!orderInfo.getF_wechatshare()) {
                order.setF_wechatshare(true);
                order.setF_wechattime((int) (new Date().getTime() / 1000));
                if (derate != null) {
                    derate = derate + wdownPrice;
                } else {
                    derate = wdownPrice;
                }

            }
        }
        if (blogshare != null && bdownPrice != null) {
            if (!orderInfo.getF_blogshare()) {
                order.setF_blogshare(true);
                order.setF_blogtime((int) (new Date().getTime() / 1000));
                if (derate != null) {
                    derate = derate + bdownPrice;
                } else {
                    derate = bdownPrice;
                }
            }
        }
        if (baidushare != null && ddownPrice != null) {
            if (!orderInfo.getF_baidushare()) {
                order.setF_baidushare(true);
                order.setF_baidutime((int) (new Date().getTime() / 1000));
                if (derate != null) {
                    derate = derate + ddownPrice;
                } else {
                    derate = ddownPrice;
                }
            }

        }
        order.setF_derate(derate);
        PtResult shareResult = orderDao.edit(order);
        if (!shareResult.isSucceed()) {
            return shareResult;
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("id", shareResult.getObject());
        return shareResult;
    }

    @Override
    public PtResult getOrderListRequest(Integer status, Integer type,String name, String phone,
                                        String appId,Integer start, Integer limit) {
        PtResult ptResult = orderDao.getOrderListRequest(status, type, name, phone,appId, start, limit);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }
        List<Order> list = ptResult.getObject();
        JSONArray jsonArray = new JSONArray();
        for (Order order : list) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(order);
            Integer cartypeId = order.getF_cartypeid();
            if (cartypeId != null) {
                PtResult autoResult = autoService.getCarInfo(cartypeId);
                if (autoResult.isSucceed()) {
                    CarParam carParam = autoResult.getObject();
                    jsonObject.put("guidePrice", carParam.getPrices());
                    jsonObject.put("title", carParam.getFullname());
                    jsonObject.put("id", carParam.getVid());
                    jsonObject.put("image", carParam.getImage());
                    jsonObject.put("brand", carParam.getBrand());
                    jsonObject.put("carSetName", carParam.getCarsetTitle());
                }
            }
            Long fmid = order.getF_mid();
            if (fmid != null) {
                PtResult userResult = ptUserService.getUserInfo(fmid.intValue());
                if (userResult.isSucceed()) {
                    PtUser ptUser = userResult.getObject();
                    jsonObject.put("name", ptUser.getF_name());
                    jsonObject.put("phone", ptUser.getF_phone());
                }
            }
            jsonArray.add(jsonObject);

        }
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("orderList", jsonArray);
        orderMap.put("total", Integer.parseInt(ptResult.getDescription()));
        ptResult.setObject(orderMap);
        return ptResult;
    }

    @Override
    public PtResult getCarorderListStatus(Long userId, String appId, List<Integer> status) {
        PtResult ptResult = orderDao.getCarorderListStatus(userId, appId, status);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }
        List<Order> orderList = ptResult.getObject();
        JSONArray jsonArray = new JSONArray();
        for (Order record : orderList) {
            this.doRevoke(record);
            JSONObject jsonObject = (JSONObject) JSON.toJSON(record);
            if (record.getF_cartypeid() != null) {
                PtResult autoResult = autoService.getCarInfo(record.getF_cartypeid());
                if (autoResult.isSucceed()) {

                    CarParam carParam = autoResult.getObject();
                    jsonObject.put("guidePrice", carParam.getPrices());
                    jsonObject.put("title", carParam.getFullname());
                    jsonObject.put("id", carParam.getVid());
                    jsonObject.put("image", carParam.getImage());
                }
            }
            jsonArray.add(jsonObject);
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("orderList", jsonArray);
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult revoke(Long id) {
        Order record = new Order();
        record.setF_ofid(id);
        record.setF_status(EnumConstant.OrderStatus.ORERDUE.getValue());
        record.setF_operatetime((int) (new Date().getTime() / 1000));
        record.setF_operator("system");
        return orderDao.edit(record);
    }

    private Order doRevoke(Order record) {
        int now = new Long(new Date().getTime() / 1000).intValue();//获取当前时间的10位时间戳
        if (record.getF_status() == EnumConstant.OrderStatus.OFFER.getValue()) {//已报价   支付等待

            if (now - record.getF_operatetime() > SystemConstant.MAX_WAIT_TIME) {
                this.revoke(record.getF_ofid());
                record.setF_status(EnumConstant.OrderStatus.ORERDUE.getValue());
            }
        }

        if (record.getF_status() == EnumConstant.OrderStatus.INQUIRE.getValue()) {//已询价，报价等待
            if (now - record.getF_createtime() > SystemConstant.MAX_WAIT_TIME / 2) {
                this.revoke(record.getF_ofid());
                record.setF_status(EnumConstant.OrderStatus.ORERDUE.getValue());
            }
        }
        return record;
    }
}
