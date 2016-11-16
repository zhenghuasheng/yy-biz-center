package com.etong.pt.service;

import com.etong.pt.data.appvehicle.AppVehicle;
import com.etong.pt.utility.PtResult;

/**
 * 可用品牌
 * Created by chenlinyang on 2015/11/26.
 */
public interface AppVehicleService {
    PtResult getAppBrand(String appId, Boolean status);
    PtResult editAppBrand(String appId, Integer brandId, Boolean status);
    PtResult findExistBrands(String appId);
    PtResult getAppCar(String appId, Integer brandId, Integer carsetId, Boolean status,
                       Integer page, Integer pageSize);
    PtResult editAppCar(String appId, Integer brandId, Integer carsetId, Integer carId, Boolean status);
    /*PtResult findExistCarset(String appId, Integer carsetId);
    PtResult findExistCar(String appId, Integer carId);*/
}
