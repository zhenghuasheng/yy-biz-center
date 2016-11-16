package com.etong.pt.http.controller;

import com.etong.pt.data.response.UserResponse;
import com.etong.pt.service.UserResService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/5.
 */
@Controller
public class ResController {
    @Autowired
    private UserResService userResService;

    private Logger logger= LoggerFactory.getLogger(ResController.class);

    @ResponseBody
    @RequestMapping("/biz/res/add.do")
    public PtResult addUserResRequest(UserResponse userResponse){
        if (userResService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务层服务无效",null);
        }
        if (userResponse.getF_mid()==null||userResponse.getF_stid()==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        userResponse.setF_createtime((int) (new Date().getTime()/1000));
        userResponse.setF_read(false);
        PtResult ptResult=userResService.addUserResponse(userResponse);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping("/biz/res/detail.do")
    public PtResult getUserResRequest(Long id){
        if (userResService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务层服务无效",null);
        }
        if (id==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        PtResult ptResult=userResService.getUserResponse(id);
        return ptResult;
    }
    @ResponseBody
    @RequestMapping("/biz/res/modify.do")
    public PtResult modifyResRequest(UserResponse userResponse){
        if (userResService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务层服务无效",null);
        }
        if (userResponse.getF_rpid()==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        PtResult ptResult=userResService.modifyUserResponse(userResponse);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping("/biz/res/list.do")
    public PtResult getUserResListRequest(String appId, Long mid, Boolean readed, Integer page, Integer pageSize){
        if (userResService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务层服务无效",null);
        }
        Integer start=null;
        if (page!=null &&pageSize!=null){
            start=(page-1)*pageSize;
        }
        PtResult ptResult=userResService.getUserResponseList(appId,mid,readed,start,pageSize);
        return ptResult;
    }
}
