package com.etong.pt.dao.impl;

import com.etong.pt.dao.UserResponseDao;
import com.etong.pt.data.response.UserResponse;
import com.etong.pt.data.response.UserResponseExample;
import com.etong.pt.data.response.UserResponseMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public class UserResponseDaoImpl implements UserResponseDao {
    public static final String RESPONSE_INDEX = "biz_center_resp";
    private static final Logger logger = LoggerFactory.getLogger(UserResponseDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult addUserResponse(UserResponse userResponse) {
        PtResult ptResult = dbIndexNum.generateIndexNum(RESPONSE_INDEX);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        userResponse.setF_rpid((Long) ptResult.getObject());
        DbProxy dbProxy = null;
        try {
            dbProxy = dbManager.getDbProxy(true);

            UserResponseMapper mapper = dbProxy.getMapper(UserResponseMapper.class);
            int result = mapper.insertSelective(userResponse);
            if (result < 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT, "插入用户反馈信息失败", null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, userResponse.getF_rpid());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            if (dbProxy != null) {
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult getUserResponse(Long id) {
        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);

            UserResponseMapper mapper=dbProxy.getMapper(UserResponseMapper.class);
            UserResponse userResponse= mapper.selectByPrimaryKey(id);
            if (userResponse==null){
                return new PtResult(PtCommonError.PT_ERROR_NODATA,null,null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,userResponse);
        } catch (Exception e) {
            logger.error(e.getMessage());
           return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult modifyUserResponse(UserResponse userResponse) {
        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            UserResponseMapper mapper=dbProxy.getMapper(UserResponseMapper.class);
            int result=mapper.updateByPrimaryKeySelective(userResponse);

            if (result<1){
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT,"反馈信息修改失败",null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,userResponse.getF_rpid());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }


    @Override
    public PtResult getUserResponseList(String appId, Long mid,Boolean readed, Integer start, Integer count) {
        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            UserResponseMapper mapper=dbProxy.getMapper(UserResponseMapper.class);

            UserResponseExample example=new UserResponseExample();
            UserResponseExample.Criteria criteria=example.createCriteria();
            if (appId!=null){
                criteria.andF_stidEqualTo(appId);
            }
            if (mid!=null){
                criteria.andF_midEqualTo(mid);
            }
            if (readed!=null){
                criteria.andF_readEqualTo(readed);
            }
            Integer total=mapper.countByExample(example);

            example.setOrderByClause("f_createtime desc");
            example.setLimitClause(start, count);
            List<UserResponse>list=mapper.selectByExample(example);

            if (list.isEmpty()){
                return new PtResult(PtCommonError.PT_ERROR_NODATA,null,null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,total.toString(),list);
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
