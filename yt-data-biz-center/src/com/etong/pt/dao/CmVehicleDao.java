package com.etong.pt.dao;

import com.etong.pt.data.cmvehicle.CmVehicle;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/28.
 */
public interface CmVehicleDao {
    PtResult findAll();
    PtResult findByParam(CmVehicle param);
}
