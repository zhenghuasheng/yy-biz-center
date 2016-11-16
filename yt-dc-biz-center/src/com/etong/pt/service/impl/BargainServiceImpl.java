package com.etong.pt.service.impl;

import com.etong.pt.dao.BargainDao;
import com.etong.pt.data.bargain.BargainInfo;
import com.etong.pt.service.BargainService;
import com.etong.pt.utility.PtResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/30.
 */
public class BargainServiceImpl implements BargainService {

    private BargainDao bargainDao;

    public void setBargainDao(BargainDao bargainDao) {
        this.bargainDao = bargainDao;
    }

    @Override
    public PtResult addBargainInfoRequest(BargainInfo bargainInfo) {
        PtResult ptResult=bargainDao.addBargainInfoRequest(bargainInfo);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>objectMap=new HashMap<>();
        objectMap.put("id",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult getBargainInfoListRequest(Long ofid, String ofcode) {
        return null;
    }
}
