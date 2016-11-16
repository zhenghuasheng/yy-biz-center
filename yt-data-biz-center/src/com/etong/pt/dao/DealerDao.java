package com.etong.pt.dao;

import com.etong.pt.data.dealer.Dealer;
import com.etong.pt.data.dealer.DealerWithBLOBs;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/12/1.
 * 经销商管理
 */
public interface DealerDao {
    PtResult findByParam(Dealer param);
    PtResult getById(Integer id);
    PtResult add(DealerWithBLOBs record);
    PtResult update(DealerWithBLOBs record);
    PtResult delete(Integer id);
}
