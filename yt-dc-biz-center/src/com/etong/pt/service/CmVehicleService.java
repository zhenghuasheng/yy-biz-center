package com.etong.pt.service;

import com.etong.pt.constants.EnumConstant;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public interface CmVehicleService {
    PtResult findByParam(EnumConstant.PriceRange e, Integer carLever);
}
