package com.etong.pt.data.response;

import com.etong.pt.data.response.UserResponse;
import com.etong.pt.data.response.UserResponseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserResponseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int countByExample(UserResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int deleteByExample(UserResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int deleteByPrimaryKey(Long f_rpid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int insert(UserResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int insertSelective(UserResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    List<UserResponse> selectByExample(UserResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    UserResponse selectByPrimaryKey(Long f_rpid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int updateByExampleSelective(@Param("record") UserResponse record, @Param("example") UserResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int updateByExample(@Param("record") UserResponse record, @Param("example") UserResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int updateByPrimaryKeySelective(UserResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_response
     *
     * @mbggenerated Tue Jan 05 15:52:28 CST 2016
     */
    int updateByPrimaryKey(UserResponse record);
}