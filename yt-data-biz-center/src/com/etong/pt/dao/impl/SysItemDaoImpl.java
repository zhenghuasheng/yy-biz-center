package com.etong.pt.dao.impl;

import com.etong.pt.dao.SysItemDao;
import com.etong.pt.data.sysitem.SysItem;
import com.etong.pt.data.sysitem.SysItemExample;
import com.etong.pt.data.sysitem.SysItemMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/11/25.
 */
public class SysItemDaoImpl implements SysItemDao {
    public static final String SysItem_INDEX = "biz_center_sysitem";
    private static final Logger logger = LoggerFactory.getLogger(SysItemDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findByParam(SysItem param) {
        SysItemExample example = new SysItemExample();
        SysItemExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysItemMapper mapper = dbProxy.getMapper(SysItemMapper.class);

        try {
            if(param.getStid() != null) {
                criteria.andStidEqualTo(param.getStid());
            }
            if(param.getItemid() != null) {
                criteria.andItemidEqualTo(param.getItemid());
            }
            if(param.getPitemid() != null) {
                criteria.andPitemidEqualTo(param.getPitemid());
            }
            if(param.getItemcode() != null && !param.getItemcode().isEmpty()) {
                criteria.andItemcodeEqualTo(param.getItemcode());
            }
            if(param.getStatus() != null) {
                criteria.andStatusEqualTo(param.getStatus());
            }

            example.setOrderByClause("f_order asc");
            List<SysItem> list = mapper.selectByExample(example);

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
    public PtResult getById(Integer id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysItemMapper mapper = dbProxy.getMapper(SysItemMapper.class);

        try {
            SysItem record = mapper.selectByPrimaryKey(id);

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
    public PtResult getByCode(String code) {
        SysItemExample example = new SysItemExample();
        example.or().andItemcodeEqualTo(code);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysItemMapper mapper = dbProxy.getMapper(SysItemMapper.class);

        try {
            List<SysItem> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list.get(0));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult add(SysItem record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        //检查数据是否存在
        PtResult ptResult = getByCode(record.getItemcode());
        PtError ptError = ptResult.getPtError();

        if (ptError == PtCommonError.PT_ERROR_SUCCESS) {
            return new PtResult(PtCommonError.PT_ERROR_RECORD_REDUPLICATED, null, null);
        } else if (ptError == PtCommonError.PT_ERROR_DB) {
            return ptResult;
        }

        ptResult = dbIndexNum.generateIndexNum(SysItem_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setItemid(id.intValue());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysItemMapper mapper = dbProxy.getMapper(SysItemMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getItemid());
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
    public PtResult update(SysItem record) {
        SysItemExample example = new SysItemExample();
        example.or().andItemidEqualTo(record.getItemid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysItemMapper mapper = dbProxy.getMapper(SysItemMapper.class);

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
    public PtResult delete(Integer id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysItemMapper mapper = dbProxy.getMapper(SysItemMapper.class);

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
