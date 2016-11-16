package com.etong.pt.data.collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionVehicleExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public CollectionVehicleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andF_clidIsNull() {
            addCriterion("f_clid is null");
            return (Criteria) this;
        }

        public Criteria andF_clidIsNotNull() {
            addCriterion("f_clid is not null");
            return (Criteria) this;
        }

        public Criteria andF_clidEqualTo(Long value) {
            addCriterion("f_clid =", value, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidNotEqualTo(Long value) {
            addCriterion("f_clid <>", value, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidGreaterThan(Long value) {
            addCriterion("f_clid >", value, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidGreaterThanOrEqualTo(Long value) {
            addCriterion("f_clid >=", value, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidLessThan(Long value) {
            addCriterion("f_clid <", value, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidLessThanOrEqualTo(Long value) {
            addCriterion("f_clid <=", value, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidIn(List<Long> values) {
            addCriterion("f_clid in", values, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidNotIn(List<Long> values) {
            addCriterion("f_clid not in", values, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidBetween(Long value1, Long value2) {
            addCriterion("f_clid between", value1, value2, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_clidNotBetween(Long value1, Long value2) {
            addCriterion("f_clid not between", value1, value2, "f_clid");
            return (Criteria) this;
        }

        public Criteria andF_vidIsNull() {
            addCriterion("f_vid is null");
            return (Criteria) this;
        }

        public Criteria andF_vidIsNotNull() {
            addCriterion("f_vid is not null");
            return (Criteria) this;
        }

        public Criteria andF_vidEqualTo(Integer value) {
            addCriterion("f_vid =", value, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidNotEqualTo(Integer value) {
            addCriterion("f_vid <>", value, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidGreaterThan(Integer value) {
            addCriterion("f_vid >", value, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidGreaterThanOrEqualTo(Integer value) {
            addCriterion("f_vid >=", value, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidLessThan(Integer value) {
            addCriterion("f_vid <", value, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidLessThanOrEqualTo(Integer value) {
            addCriterion("f_vid <=", value, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidIn(List<Integer> values) {
            addCriterion("f_vid in", values, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidNotIn(List<Integer> values) {
            addCriterion("f_vid not in", values, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidBetween(Integer value1, Integer value2) {
            addCriterion("f_vid between", value1, value2, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_vidNotBetween(Integer value1, Integer value2) {
            addCriterion("f_vid not between", value1, value2, "f_vid");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeIsNull() {
            addCriterion("f_collecttime is null");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeIsNotNull() {
            addCriterion("f_collecttime is not null");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeEqualTo(Integer value) {
            addCriterion("f_collecttime =", value, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeNotEqualTo(Integer value) {
            addCriterion("f_collecttime <>", value, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeGreaterThan(Integer value) {
            addCriterion("f_collecttime >", value, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("f_collecttime >=", value, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeLessThan(Integer value) {
            addCriterion("f_collecttime <", value, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeLessThanOrEqualTo(Integer value) {
            addCriterion("f_collecttime <=", value, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeIn(List<Integer> values) {
            addCriterion("f_collecttime in", values, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeNotIn(List<Integer> values) {
            addCriterion("f_collecttime not in", values, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeBetween(Integer value1, Integer value2) {
            addCriterion("f_collecttime between", value1, value2, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_collecttimeNotBetween(Integer value1, Integer value2) {
            addCriterion("f_collecttime not between", value1, value2, "f_collecttime");
            return (Criteria) this;
        }

        public Criteria andF_midIsNull() {
            addCriterion("f_mid is null");
            return (Criteria) this;
        }

        public Criteria andF_midIsNotNull() {
            addCriterion("f_mid is not null");
            return (Criteria) this;
        }

        public Criteria andF_midEqualTo(Long value) {
            addCriterion("f_mid =", value, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midNotEqualTo(Long value) {
            addCriterion("f_mid <>", value, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midGreaterThan(Long value) {
            addCriterion("f_mid >", value, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midGreaterThanOrEqualTo(Long value) {
            addCriterion("f_mid >=", value, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midLessThan(Long value) {
            addCriterion("f_mid <", value, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midLessThanOrEqualTo(Long value) {
            addCriterion("f_mid <=", value, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midIn(List<Long> values) {
            addCriterion("f_mid in", values, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midNotIn(List<Long> values) {
            addCriterion("f_mid not in", values, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midBetween(Long value1, Long value2) {
            addCriterion("f_mid between", value1, value2, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_midNotBetween(Long value1, Long value2) {
            addCriterion("f_mid not between", value1, value2, "f_mid");
            return (Criteria) this;
        }

        public Criteria andF_stidIsNull() {
            addCriterion("f_stid is null");
            return (Criteria) this;
        }

        public Criteria andF_stidIsNotNull() {
            addCriterion("f_stid is not null");
            return (Criteria) this;
        }

        public Criteria andF_stidEqualTo(String value) {
            addCriterion("f_stid =", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidNotEqualTo(String value) {
            addCriterion("f_stid <>", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidGreaterThan(String value) {
            addCriterion("f_stid >", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidGreaterThanOrEqualTo(String value) {
            addCriterion("f_stid >=", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidLessThan(String value) {
            addCriterion("f_stid <", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidLessThanOrEqualTo(String value) {
            addCriterion("f_stid <=", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidLike(String value) {
            addCriterion("f_stid like", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidNotLike(String value) {
            addCriterion("f_stid not like", value, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidIn(List<String> values) {
            addCriterion("f_stid in", values, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidNotIn(List<String> values) {
            addCriterion("f_stid not in", values, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidBetween(String value1, String value2) {
            addCriterion("f_stid between", value1, value2, "f_stid");
            return (Criteria) this;
        }

        public Criteria andF_stidNotBetween(String value1, String value2) {
            addCriterion("f_stid not between", value1, value2, "f_stid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated do_not_delete_during_merge Wed Oct 28 14:04:38 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_bd_collectionvehicle
     *
     * @mbggenerated Wed Oct 28 14:04:38 CST 2015
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}