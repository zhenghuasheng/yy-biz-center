package com.etong.pt.data.sysbanner;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysBannerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int countByExample(SysBannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int deleteByExample(SysBannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int deleteByPrimaryKey(Long bnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int insert(SysBanner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int insertSelective(SysBanner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    List<SysBanner> selectByExampleWithBLOBs(SysBannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    List<SysBanner> selectByExample(SysBannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    SysBanner selectByPrimaryKey(Long bnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int updateByExampleSelective(@Param("record") SysBanner record, @Param("example") SysBannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int updateByExampleWithBLOBs(@Param("record") SysBanner record, @Param("example") SysBannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int updateByExample(@Param("record") SysBanner record, @Param("example") SysBannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int updateByPrimaryKeySelective(SysBanner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(SysBanner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_banner
     *
     * @mbggenerated Tue Dec 01 09:44:32 CST 2015
     */
    int updateByPrimaryKey(SysBanner record);
}