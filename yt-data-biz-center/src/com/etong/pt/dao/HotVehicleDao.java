package com.etong.pt.dao;

import com.etong.pt.data.hotvehicle.HotVehicle;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public interface HotVehicleDao {
    PtResult findAll();
    PtResult findByParam(HotVehicle param);
    PtResult add(HotVehicle record);
    PtResult delete(Integer id);
}
