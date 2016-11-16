package com.etong.pt.dao;

import com.etong.pt.data.collection.CollectionVehicle;
import com.etong.pt.utility.PtResult;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface CollectionDao {
    PtResult addCollectionRequest(CollectionVehicle record);

    PtResult deleteCollectionRequest(Long f_clid);

    PtResult modifyCollectionRequest(CollectionVehicle record);

    PtResult getCollectionRequest(Long f_clid);

    PtResult getCollectionListRequest(Long f_mid,String f_stid);

    PtResult findCollectionsRequest(Long f_mid,String f_stid,Integer f_vid);



}
