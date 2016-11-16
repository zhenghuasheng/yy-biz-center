package com.etong.pt.service;

import com.etong.pt.data.bargain.BargainInfo;
import com.etong.pt.utility.PtResult;

/**
 * Created by Administrator on 2015/10/30.
 */
public interface BargainService {

    PtResult addBargainInfoRequest(BargainInfo bargainInfo);

    PtResult getBargainInfoListRequest(Long ofid,String ofcode);
}
