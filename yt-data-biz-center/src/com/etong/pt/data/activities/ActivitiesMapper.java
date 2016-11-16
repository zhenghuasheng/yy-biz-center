package com.etong.pt.data.activities;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivitiesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int countByExample(ActivitiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int deleteByExample(ActivitiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int deleteByPrimaryKey(Long acid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int insert(Activities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int insertSelective(Activities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    List<Activities> selectByExampleWithBLOBs(ActivitiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    List<Activities> selectByExample(ActivitiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    Activities selectByPrimaryKey(Long acid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int updateByExampleSelective(@Param("record") Activities record, @Param("example") ActivitiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int updateByExampleWithBLOBs(@Param("record") Activities record, @Param("example") ActivitiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int updateByExample(@Param("record") Activities record, @Param("example") ActivitiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int updateByPrimaryKeySelective(Activities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(Activities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_activities
     *
     * @mbggenerated Tue Dec 01 13:44:14 CST 2015
     */
    int updateByPrimaryKey(Activities record);
}