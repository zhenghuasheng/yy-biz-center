package com.etong.pt.dao;

import com.etong.pt.data.appvehicle.AppVehicle;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/25.
 */
public interface AppVehicleDao {
    PtResult findByParam(AppVehicle param);
    PtResult getById(Long id);
    PtResult add(AppVehicle record);
    PtResult update(AppVehicle record);
    PtResult delete(Long id);
}
