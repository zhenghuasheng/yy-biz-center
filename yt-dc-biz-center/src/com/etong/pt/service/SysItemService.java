package com.etong.pt.service;

import com.etong.pt.data.sysitem.SysItem;
import com.etong.pt.utility.PtResult;

/**
 * UI定义
 * Created by chenlinyang on 2015/11/26.
 */
public interface SysItemService {
    PtResult findTreeByAppId(String appId);
    PtResult findByParam(SysItem param);
    PtResult add(SysItem record);
    PtResult update(SysItem record);
}
