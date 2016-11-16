package com.etong.pt.data.dealer;

import com.etong.pt.data.dealer.Dealer;
import com.etong.pt.data.dealer.DealerExample;
import com.etong.pt.data.dealer.DealerWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DealerMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int countByExample(DealerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int deleteByExample(DealerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int deleteByPrimaryKey(Integer ncid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int insert(DealerWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int insertSelective(DealerWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	List<DealerWithBLOBs> selectByExampleWithBLOBs(DealerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	List<Dealer> selectByExample(DealerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	DealerWithBLOBs selectByPrimaryKey(Integer ncid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int updateByExampleSelective(@Param("record") DealerWithBLOBs record, @Param("example") DealerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int updateByExampleWithBLOBs(@Param("record") DealerWithBLOBs record, @Param("example") DealerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int updateByExample(@Param("record") Dealer record, @Param("example") DealerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int updateByPrimaryKeySelective(DealerWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int updateByPrimaryKeyWithBLOBs(DealerWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_um_newcar
	 * @mbggenerated  Thu Feb 25 11:29:12 CST 2016
	 */
	int updateByPrimaryKey(Dealer record);
}