package com.etong.pt.data.hotvehicle;

import com.etong.pt.data.hotvehicle.HotVehicle;
import com.etong.pt.data.hotvehicle.HotVehicleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HotVehicleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int countByExample(HotVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int deleteByExample(HotVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int deleteByPrimaryKey(Integer f_hvid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int insert(HotVehicle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int insertSelective(HotVehicle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    List<HotVehicle> selectByExample(HotVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    HotVehicle selectByPrimaryKey(Integer f_hvid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int updateByExampleSelective(@Param("record") HotVehicle record, @Param("example") HotVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int updateByExample(@Param("record") HotVehicle record, @Param("example") HotVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int updateByPrimaryKeySelective(HotVehicle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_hotvehicle
     *
     * @mbggenerated Wed Oct 21 09:42:00 CST 2015
     */
    int updateByPrimaryKey(HotVehicle record);
}