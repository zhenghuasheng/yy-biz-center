package com.etong.pt.dao;

import com.etong.pt.data.bargain.BargainInfo;
import com.etong.pt.utility.PtResult;

/**
 * Created by Administrator on 2015/10/30.
 */
public interface BargainDao {
    PtResult addBargainInfoRequest(BargainInfo bargainInfo);

    PtResult getBargainInfoListRequest(Long ofid,String ofcode,Integer start,Integer limit);
}
