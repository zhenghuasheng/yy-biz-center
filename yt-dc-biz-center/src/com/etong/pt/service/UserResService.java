package com.etong.pt.service;

import com.etong.pt.data.response.UserResponse;
import com.etong.pt.utility.PtResult;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface UserResService {
    PtResult addUserResponse(UserResponse userResponse);

    PtResult getUserResponse(Long id);

    PtResult modifyUserResponse(UserResponse userResponse);

    PtResult getUserResponseList(String  appId,Long mid,Boolean readed ,Integer start,Integer count);
}
