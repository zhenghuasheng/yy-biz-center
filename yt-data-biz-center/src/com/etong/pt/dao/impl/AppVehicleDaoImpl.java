package com.etong.pt.dao.impl;

import com.etong.pt.dao.AppVehicleDao;
import com.etong.pt.data.appvehicle.AppVehicle;
import com.etong.pt.data.appvehicle.AppVehicleExample;
import com.etong.pt.data.appvehicle.AppVehicleMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/11/25.
 */
public class AppVehicleDaoImpl implements AppVehicleDao {
    public static final String SysItem_INDEX = "biz_center_appvehicle";
    private static final Logger logger = LoggerFactory.getLogger(AppVehicleDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findByParam(AppVehicle param) {
        AppVehicleExample example = new AppVehicleExample();
        AppVehicleExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppVehicleMapper mapper = dbProxy.getMapper(AppVehicleMapper.class);

        try {
            if(param.getStid() != null) {
                criteria.andStidEqualTo(param.getStid());
            }
            if(param.getCarbrandid() != null) {
                criteria.andCarbrandidEqualTo(param.getCarbrandid());
            }
            if(param.getCarsetid() != null) {
                criteria.andCarsetidEqualTo(param.getCarsetid());
            }
            if(param.getCartypeid() != null) {
                criteria.andCartypeidEqualTo(param.getCartypeid());
            }
            if(param.getLevel() != null) {
                criteria.andLevelEqualTo(param.getLevel());
            }
            if(param.getStatus() != null) {
                criteria.andStatusEqualTo(param.getStatus());
            }

            List<AppVehicle> list = mapper.selectByExample(example);

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
    public PtResult getById(Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppVehicleMapper mapper = dbProxy.getMapper(AppVehicleMapper.class);

        try {
            AppVehicle record = mapper.selectByPrimaryKey(id);

            if (record == null) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult add(AppVehicle record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(SysItem_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setDvid(id);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppVehicleMapper mapper = dbProxy.getMapper(AppVehicleMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getDvid());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
        return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
    }

    @Override
    public PtResult update(AppVehicle record) {
        AppVehicleExample example = new AppVehicleExample();
        example.or().andDvidEqualTo(record.getDvid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppVehicleMapper mapper = dbProxy.getMapper(AppVehicleMapper.class);

        try {
            int result = mapper.updateByExampleSelective(record, example);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult delete(Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppVehicleMapper mapper = dbProxy.getMapper(AppVehicleMapper.class);

        try {
            int result = mapper.deleteByPrimaryKey(id);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            }{
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

}
