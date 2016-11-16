package com.etong.pt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.pt.dao.CarLeverDao;
import com.etong.pt.data.carlever.CarLever;
import com.etong.pt.service.CarLeverService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public class CarLeverServiceImpl implements CarLeverService {
    private CarLeverDao carLeverDao;

    public void setCarLeverDao(CarLeverDao carLeverDao) {
        this.carLeverDao = carLeverDao;
    }

    @Override
    public PtResult findAll() {
        PtResult ptResult = carLeverDao.findAll();
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        else {
            List<CarLever> list = ptResult.getObject();
            JSONArray jsonArray = new JSONArray(list.size());
            JSONObject jsonObject = null;
            for (CarLever record : list) {
                jsonObject = new JSONObject();
                jsonObject.put("id", record.getF_id());
                jsonObject.put("title", record.getF_title());
                jsonArray.add(jsonObject);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
        }
    }
}
