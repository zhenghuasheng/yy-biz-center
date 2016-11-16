package com.etong.pt.service.impl;

import com.etong.pt.dao.UserResponseDao;
import com.etong.pt.data.response.UserResponse;
import com.etong.pt.service.UserResService;
import com.etong.pt.utility.PtResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/5.
 */
public class UserResServiceImpl implements UserResService {
    private UserResponseDao userResponseDao;

    public void setUserResponseDao(UserResponseDao userResponseDao) {
        this.userResponseDao = userResponseDao;
    }

    @Override
    public PtResult addUserResponse(UserResponse userResponse) {
        PtResult ptResult=userResponseDao.addUserResponse(userResponse);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("resId",ptResult.getObject());
        ptResult.setObject(resultMap);
        return ptResult;
    }

    @Override
    public PtResult getUserResponse(Long id) {
        PtResult ptResult=userResponseDao.getUserResponse(id);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>objectMap=new HashMap<>();
        objectMap.put("resInfo",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult modifyUserResponse(UserResponse userResponse) {
        PtResult ptResult=userResponseDao.modifyUserResponse(userResponse);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>objectMap=new HashMap<>();
        objectMap.put("resId",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult getUserResponseList(String appId, Long mid, Boolean readed, Integer start, Integer count) {
        PtResult ptResult=userResponseDao.getUserResponseList(appId,mid,readed,start,count);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>objectMap=new HashMap<>();
        objectMap.put("total",ptResult.getDescription());
        objectMap.put("resList",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }
}
