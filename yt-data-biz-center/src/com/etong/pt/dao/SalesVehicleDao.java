package com.etong.pt.dao;

import com.etong.pt.data.salesvehicle.SalesVehicle;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/28.
 */
public interface SalesVehicleDao {
    PtResult findAll();
    PtResult findByParam(SalesVehicle param);
    PtResult getById(Integer id);
    PtResult add(SalesVehicle record);
    PtResult update(SalesVehicle record);
    PtResult delete(Integer id);
}
