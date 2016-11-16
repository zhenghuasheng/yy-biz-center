package com.etong.pt.http.controller;

import com.etong.pt.data.app.App;
import com.etong.pt.service.AppService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2015/11/25.
 */
@Controller
public class AppController {
    @Autowired
    private AppService appService;

    @ResponseBody
    @RequestMapping(value = "/biz/app/add.do")
    public PtResult addAppRequest(App app){
        if (appService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"系统服务无效",null);
        }
    PtResult ptResult=appService.addApp(app);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/list.do")
    public PtResult getAppListRequest(String psid,Integer start,Integer limit){
        if (appService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"系统服务无效",null);
        }
        PtResult ptResult=appService.getAppList(psid, start, limit);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/edit.do")
    public PtResult editAppRequest(App app){
        if (appService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"系统服务无效",null);
        }
        PtResult ptResult=appService.edit(app);
        return ptResult;
    }
}
