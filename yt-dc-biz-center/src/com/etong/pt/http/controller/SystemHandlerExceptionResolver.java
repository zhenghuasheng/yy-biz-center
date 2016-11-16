package com.etong.pt.http.controller;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONException;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Administrator on 2015/4/29.
 */
@ControllerAdvice
public class SystemHandlerExceptionResolver {

    @ExceptionHandler
    @ResponseBody
    public PtResult ptExceptionRequest(HttpServletResponse response, Exception exception) {
        if (exception instanceof SQLException) {
            return new PtResult(PtCommonError.PT_ERROR_DB, exception.getMessage(), null);
        } else if (exception instanceof JSONException) {
            return new PtResult(PtCommonError.PT_ERROR_JSON_PARSE, exception.getMessage(), null);
        } else if (exception instanceof RpcException) {
            return new PtResult(PtCommonError.PT_ERROR_RPC, exception.getMessage(), null);
        } else {
            exception.printStackTrace();
            return new PtResult(PtCommonError.PT_ERROR_UNKOWN, exception.getMessage() + "---" + new Date().toString(), null);
        }
    }
}
