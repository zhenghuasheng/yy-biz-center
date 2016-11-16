package com.etong.pt.dao.impl;

import com.etong.pt.dao.CarLeverDao;
import com.etong.pt.data.carlever.CarLever;
import com.etong.pt.data.carlever.CarLeverExample;
import com.etong.pt.data.carlever.CarLeverMapper;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.ReadThroughAssignCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/28.
 */
public class CarLeverDaoImpl implements CarLeverDao {
    private static final Logger logger = LoggerFactory.getLogger(CarLeverDaoImpl.class);

    private DbManager dbManager;

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    @ReadThroughAssignCache(assignedKey = "CarLeverKey", namespace = "pt:carlever", expiration = 3000)
    public PtResult findAll() {
        CarLeverExample example = new CarLeverExample();
        //CarLeverExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        CarLeverMapper mapper = dbProxy.getMapper(CarLeverMapper.class);

        try {
            example.setOrderByClause("f_trueorder asc");
            List<CarLever> list = mapper.selectByExample(example);

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
