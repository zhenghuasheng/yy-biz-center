package com.etong.pt.dao.impl;

import com.etong.pt.dao.AppDao;
import com.etong.pt.data.app.App;
import com.etong.pt.data.app.AppExample;
import com.etong.pt.data.app.AppMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public class AppDaoImpl implements AppDao {
    public static final String APP_INDEX = "biz_center_app";
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findAll() {
        AppExample example = new AppExample();
        AppExample.Criteria criteria = example.createCriteria();


        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppMapper mapper = dbProxy.getMapper(AppMapper.class);

        try {
            example.setOrderByClause("f_stid asc");
            List<App> list = mapper.selectByExample(example);

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
    public PtResult getById(String id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppMapper mapper = dbProxy.getMapper(AppMapper.class);

        try {
            App record = mapper.selectByPrimaryKey(id);

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
    public PtResult add(App record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(APP_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setF_stid(Long.toString(id));

        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppMapper mapper = dbProxy.getMapper(AppMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getF_stid());
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
    public PtResult edit(App record) {
        AppExample example = new AppExample();
        example.or().andF_stidEqualTo(record.getF_stid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppMapper mapper = dbProxy.getMapper(AppMapper.class);

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
    public PtResult remove(String id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        AppMapper mapper = dbProxy.getMapper(AppMapper.class);

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

    @Override
    public PtResult getAppinfoList(String pstid, Integer start, Integer limit) {
        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            AppMapper appMapper=dbProxy.getMapper(AppMapper.class);
            AppExample example=new AppExample();
            if (pstid!=null){
                example.or().andF_pstidEqualTo(pstid);
            }
            Integer total=appMapper.countByExample(example);
            if (start!=null&&limit!=null){
                example.setLimitClause(start, limit);
            }


            List<App>appList=appMapper.selectByExample(example);
            if (appList.isEmpty()){
                return new PtResult(PtCommonError.PT_ERROR_NODATA,null,null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,total.toString(),appList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }

}
