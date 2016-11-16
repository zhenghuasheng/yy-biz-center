package com.etong.pt.service;

import com.etong.pt.data.salesvehicle.SalesVehicle;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public interface SalesVehicleService {
    /**促销车款*/
    PtResult getSales(String appId);

    PtResult findByParam(SalesVehicle param);
    PtResult add( SalesVehicle record);
    PtResult update( SalesVehicle record);
    PtResult delete(Integer id);
}
