package com.etong.pt.http.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.pt.constants.EnumConstant;
import com.etong.pt.data.order.Order;
import com.etong.pt.service.OrderService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.pt.utility.SmsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2015/10/20.
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private SmsHelper smsHelper;

    @ResponseBody
    @RequestMapping(value = "/biz/carorder/create.do")
    public PtResult addOrderRequest(Order order){
        if (order.getF_phone()==null||order.getF_mid()==null||order.getF_stid()==null||order.getF_cartypeid()==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=orderService.add(order);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/carorder/get.do")
    public PtResult getOrderDetailRequest(Long orderId){
        if (orderId==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=orderService.getById(orderId);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/carorder/getmy.do")
    public PtResult getOrderListRequest(Long userId,String appId,Integer status){
        if (userId==null||appId==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=orderService.findByParam(null, null, null, userId, appId, status, null, null, null);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/order/bargain.do")
    public PtResult helpBargainRequest(Boolean bargain,Long ofid,String devicecode){
        if (ofid==null||devicecode==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        if (bargain){
            PtResult ptResult=orderService.bargainOrderPrice(ofid,bargain,devicecode);
            return ptResult;
        }else {
            return new PtResult(PtCommonError.PT_ERROR_UNKOWN,"非法操作",null);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/biz/order/share.do")
    public PtResult modifyShareStatus(Long orderId,Boolean wechatshare,Double wdownPrice,Boolean blogshare,Double bdownPrice,Boolean baidushare,Double ddownPrice){
        if (orderId==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (!(wechatshare||baidushare||blogshare)){
            return new PtResult(PtCommonError.PT_ERROR_UNKOWN,"无效请求",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=orderService.modifyShareStatus(orderId,wechatshare,wdownPrice,blogshare,bdownPrice,baidushare,ddownPrice);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/order/cancel.do")
    public PtResult cancelOrderRequest(Long orderId,Long operatorId,String operator){
        if (orderId==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        Order order=new Order();
        order.setF_ofid(orderId);
        order.setF_status(EnumConstant.OrderStatus.REVOKE.getValue());
        order.setF_operatetime((int) (new Date().getTime() / 1000));
        order.setF_operatorid(operatorId);
        order.setF_operator(operator);
        PtResult ptResult=orderService.modifyOrder(order);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/order/report.do")
    public PtResult bottompriceRequest(Order order){
        if (order.getF_ofid()==null||order.getF_bottomprice()==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        order.setF_status(EnumConstant.OrderStatus.OFFER.getValue());
        order.setF_operatetime((int) (new Date().getTime() / 1000));
        PtResult ptResult=orderService.modifyOrder(order);

        PtResult detailResult=orderService.getById(order.getF_ofid());
        if (detailResult.isSucceed()){
            HashMap<String,Object>detailResultObject=detailResult.getObject();
            Order record= (Order) detailResultObject.get("order");
            String phone=record.getF_phone();
            if (phone!=null){
                smsHelper.sendMessageRequest(phone,"您的车辆订单有报价信息,请注意查看！","10001",null);
            }
        }
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/carorder/pay.do")
    public PtResult prepayRequest(Order order){
        if (order.getF_ofid()==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        order.setF_paytime((int) (new Date().getTime() / 1000));
        order.setF_status(EnumConstant.OrderStatus.PAY.getValue());
        PtResult ptResult=orderService.modifyOrder(order);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/order/requery.do")
    public PtResult requeryPriceRequest(Long ofid){
        if (ofid==null){
            return new  PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        Order order=new Order();
        order.setF_ofid(ofid);
        order.setF_status(EnumConstant.OrderStatus.INQUIRE.getValue());
        order.setF_operatetime((int) (new Date().getTime() / 1000));
        order.setF_createtime((int) (new Date().getTime() / 1000));
        PtResult ptResult=orderService.modifyOrder(order);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping("/biz/order/confirm.do")
    public PtResult confirmPickcar(Long ofid){
        if (ofid==null){
            return new  PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        Order order=new Order();
        order.setF_ofid(ofid);
        order.setF_status(EnumConstant.OrderStatus.OVER.getValue());
        order.setF_operatetime((int) (new Date().getTime() / 1000));
        PtResult ptResult=orderService.modifyOrder(order);
        return ptResult;
    }


    @ResponseBody
    @RequestMapping("/biz/order/list.do")
    public PtResult getCarorderListRequest(@RequestParam(required = false)Integer orderType,@RequestParam(required = false)Integer orderStatus,
                                           @RequestParam(required = false)String name,@RequestParam(required = false)String phone,
                                           @RequestParam(required = false)String appId,Integer start,Integer limit){

        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=orderService.getOrderListRequest(orderStatus, orderType,name,phone, appId, start, limit);
        return ptResult;
    }

    /****
     * 多状态查询，状态为json数组字符串
     * @param userId
     * @param appId
     * @param statusList
     * @return
     */
    @ResponseBody
    @RequestMapping("/biz/order/mulstatus/list.do")
    public PtResult getCarorderListStatus(Long userId,String appId,String statusList){
        if (userId==null||appId==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        JSONArray jsonArray= JSON.parseArray(statusList);
        List<Integer> list=new ArrayList<>();
        if (!jsonArray.isEmpty()){
            for (int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Integer status=jsonObject.getInteger("status");
                list.add(status);
            }
        }
        PtResult ptResult=orderService.getCarorderListStatus(userId, appId, list);
        return ptResult;
    }


    @ResponseBody
    @RequestMapping("/biz/order/modify.do")
    public PtResult modifyOrderInfo(Order order){
        if (order.getF_ofid()==null||order.getF_operator()==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (orderService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }

        PtResult ptResult=orderService.modifyOrder(order);
        return ptResult;

    }

}
