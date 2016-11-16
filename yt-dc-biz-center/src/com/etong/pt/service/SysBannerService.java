package com.etong.pt.service;

import com.etong.pt.data.sysbanner.SysBanner;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/30.
 */
public interface SysBannerService {
    PtResult findByAppId(String appId);
    PtResult add(SysBanner record);
    PtResult update(SysBanner record);
    PtResult delete(Long id);
}
