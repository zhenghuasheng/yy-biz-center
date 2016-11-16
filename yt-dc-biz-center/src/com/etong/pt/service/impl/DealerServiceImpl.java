package com.etong.pt.service.impl;

import com.etong.pt.dao.DealerDao;
import com.etong.pt.data.dealer.Dealer;
import com.etong.pt.data.dealer.DealerWithBLOBs;
import com.etong.pt.service.DealerService;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/12/1.
 */
public class DealerServiceImpl implements DealerService {
    private DealerDao dealerDao;

    public void setDealerDao(DealerDao dealerDao) {
        this.dealerDao = dealerDao;
    }

    @Override
    public PtResult findByAppId(String appId) {
        Dealer param = new Dealer();
        param.setStid(appId);
        return dealerDao.findByParam(param);
    }

    @Override
    public PtResult findByParam(Dealer param) {
        return dealerDao.findByParam(param);
    }

    @Override
    public PtResult getById(Integer id) {
        return dealerDao.getById(id);
    }

    @Override
    public PtResult add(DealerWithBLOBs record) {
        return dealerDao.add(record);
    }

    @Override
    public PtResult update(DealerWithBLOBs record) {
        return dealerDao.update(record);
    }

    @Override
    public PtResult delete(Integer id) {
        PtResult ptResult = dealerDao.delete(id);
        return ptResult;
    }
}
