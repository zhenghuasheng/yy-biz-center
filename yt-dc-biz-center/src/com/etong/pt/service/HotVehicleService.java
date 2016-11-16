package com.etong.pt.service;

import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public interface HotVehicleService {
    /**热门品牌*/
    PtResult getHotBrand(String appId);
    PtResult addHotBrand(String appId, Integer brandId);

    /**热门车型*/
    PtResult getHotCarset(String appId);
    PtResult addHotCarset(String appId, Integer carsetId);

    PtResult deleteHot(Integer id);
}
