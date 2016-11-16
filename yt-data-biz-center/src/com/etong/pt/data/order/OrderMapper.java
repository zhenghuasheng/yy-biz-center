package com.etong.pt.data.order;

import com.etong.pt.data.order.Order;
import com.etong.pt.data.order.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int countByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int deleteByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int deleteByPrimaryKey(Long f_ofid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    List<Order> selectByExampleWithBLOBs(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    List<Order> selectByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    Order selectByPrimaryKey(Long f_ofid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int updateByExampleWithBLOBs(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_orderform
     *
     * @mbggenerated Wed Oct 21 15:40:14 CST 2015
     */
    int updateByPrimaryKey(Order record);
}