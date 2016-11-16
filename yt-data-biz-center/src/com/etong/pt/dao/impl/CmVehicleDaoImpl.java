package com.etong.pt.dao.impl;

import com.etong.pt.dao.CmVehicleDao;
import com.etong.pt.data.cmvehicle.CmVehicle;
import com.etong.pt.data.cmvehicle.CmVehicleExample;
import com.etong.pt.data.cmvehicle.CmVehicleMapper;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/28.
 */
public class CmVehicleDaoImpl implements CmVehicleDao {
    private static final Logger logger = LoggerFactory.getLogger(CmVehicleDaoImpl.class);
    private DbManager dbManager;

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findAll() {
        CmVehicleExample example = new CmVehicleExample();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        CmVehicleMapper mapper = dbProxy.getMapper(CmVehicleMapper.class);

        try {
            example.setOrderByClause("f_trueorder asc");
            List<CmVehicle> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult findByParam(CmVehicle param) {
        CmVehicleExample example = new CmVehicleExample();
        CmVehicleExample.Criteria criteria = example.createCriteria();


        DbProxy dbProxy = dbManager.getDbProxy(true);
        CmVehicleMapper mapper = dbProxy.getMapper(CmVehicleMapper.class);

        try {
            //数据等级
            if(param.getF_level() != null) {
                criteria.andF_levelEqualTo(param.getF_level());
            }
            //车型级别
            if(param.getF_models_level() != null) {
                criteria.andF_models_levelEqualTo(param.getF_models_level());
            }
            //最低价
            if(param.getMinPrice() != null) {
                criteria.andF_minguideGreaterThanOrEqualTo(param.getMinPrice());
            }
            //最高价
            if(param.getMaxPrice() != null) {
                criteria.andF_maxguideLessThanOrEqualTo(param.getMaxPrice());
            }

            example.setOrderByClause("f_trueorder asc");
            List<CmVehicle> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }
}
