package com.etong.pt.dao;

import com.etong.pt.data.sysitem.SysItem;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/25.
 */
public interface SysItemDao {
    /**
     * appId
     * available
     * delete
     * */
    PtResult findByParam(SysItem param);
    PtResult getById(Integer id);
    PtResult getByCode(String code);
    PtResult add(SysItem record);
    PtResult update(SysItem record);
    PtResult delete(Integer id);
}
