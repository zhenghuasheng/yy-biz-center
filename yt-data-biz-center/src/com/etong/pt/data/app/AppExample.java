package com.etong.pt.data.app;

import java.util.ArrayList;
import java.util.List;

public class AppExample {
    protected String limitClause;

    public String getLimitClause() {
        return limitClause;
    }

    public void setLimitClause(Integer start,Integer limit) {
        this.limitClause =String.format("%s,%s",start,limit);
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public AppExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
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
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
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

        public Criteria andF_stnameIsNull() {
            addCriterion("f_stname is null");
            return (Criteria) this;
        }

        public Criteria andF_stnameIsNotNull() {
            addCriterion("f_stname is not null");
            return (Criteria) this;
        }

        public Criteria andF_stnameEqualTo(String value) {
            addCriterion("f_stname =", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameNotEqualTo(String value) {
            addCriterion("f_stname <>", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameGreaterThan(String value) {
            addCriterion("f_stname >", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameGreaterThanOrEqualTo(String value) {
            addCriterion("f_stname >=", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameLessThan(String value) {
            addCriterion("f_stname <", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameLessThanOrEqualTo(String value) {
            addCriterion("f_stname <=", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameLike(String value) {
            addCriterion("f_stname like", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameNotLike(String value) {
            addCriterion("f_stname not like", value, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameIn(List<String> values) {
            addCriterion("f_stname in", values, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameNotIn(List<String> values) {
            addCriterion("f_stname not in", values, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameBetween(String value1, String value2) {
            addCriterion("f_stname between", value1, value2, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_stnameNotBetween(String value1, String value2) {
            addCriterion("f_stname not between", value1, value2, "f_stname");
            return (Criteria) this;
        }

        public Criteria andF_pstidIsNull() {
            addCriterion("f_pstid is null");
            return (Criteria) this;
        }

        public Criteria andF_pstidIsNotNull() {
            addCriterion("f_pstid is not null");
            return (Criteria) this;
        }

        public Criteria andF_pstidEqualTo(String value) {
            addCriterion("f_pstid =", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidNotEqualTo(String value) {
            addCriterion("f_pstid <>", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidGreaterThan(String value) {
            addCriterion("f_pstid >", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidGreaterThanOrEqualTo(String value) {
            addCriterion("f_pstid >=", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidLessThan(String value) {
            addCriterion("f_pstid <", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidLessThanOrEqualTo(String value) {
            addCriterion("f_pstid <=", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidLike(String value) {
            addCriterion("f_pstid like", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidNotLike(String value) {
            addCriterion("f_pstid not like", value, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidIn(List<String> values) {
            addCriterion("f_pstid in", values, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidNotIn(List<String> values) {
            addCriterion("f_pstid not in", values, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidBetween(String value1, String value2) {
            addCriterion("f_pstid between", value1, value2, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_pstidNotBetween(String value1, String value2) {
            addCriterion("f_pstid not between", value1, value2, "f_pstid");
            return (Criteria) this;
        }

        public Criteria andF_accreditIsNull() {
            addCriterion("f_accredit is null");
            return (Criteria) this;
        }

        public Criteria andF_accreditIsNotNull() {
            addCriterion("f_accredit is not null");
            return (Criteria) this;
        }

        public Criteria andF_accreditEqualTo(String value) {
            addCriterion("f_accredit =", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditNotEqualTo(String value) {
            addCriterion("f_accredit <>", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditGreaterThan(String value) {
            addCriterion("f_accredit >", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditGreaterThanOrEqualTo(String value) {
            addCriterion("f_accredit >=", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditLessThan(String value) {
            addCriterion("f_accredit <", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditLessThanOrEqualTo(String value) {
            addCriterion("f_accredit <=", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditLike(String value) {
            addCriterion("f_accredit like", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditNotLike(String value) {
            addCriterion("f_accredit not like", value, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditIn(List<String> values) {
            addCriterion("f_accredit in", values, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditNotIn(List<String> values) {
            addCriterion("f_accredit not in", values, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditBetween(String value1, String value2) {
            addCriterion("f_accredit between", value1, value2, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_accreditNotBetween(String value1, String value2) {
            addCriterion("f_accredit not between", value1, value2, "f_accredit");
            return (Criteria) this;
        }

        public Criteria andF_urlIsNull() {
            addCriterion("f_url is null");
            return (Criteria) this;
        }

        public Criteria andF_urlIsNotNull() {
            addCriterion("f_url is not null");
            return (Criteria) this;
        }

        public Criteria andF_urlEqualTo(String value) {
            addCriterion("f_url =", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlNotEqualTo(String value) {
            addCriterion("f_url <>", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlGreaterThan(String value) {
            addCriterion("f_url >", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlGreaterThanOrEqualTo(String value) {
            addCriterion("f_url >=", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlLessThan(String value) {
            addCriterion("f_url <", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlLessThanOrEqualTo(String value) {
            addCriterion("f_url <=", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlLike(String value) {
            addCriterion("f_url like", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlNotLike(String value) {
            addCriterion("f_url not like", value, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlIn(List<String> values) {
            addCriterion("f_url in", values, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlNotIn(List<String> values) {
            addCriterion("f_url not in", values, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlBetween(String value1, String value2) {
            addCriterion("f_url between", value1, value2, "f_url");
            return (Criteria) this;
        }

        public Criteria andF_urlNotBetween(String value1, String value2) {
            addCriterion("f_url not between", value1, value2, "f_url");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_pm_system
     *
     * @mbggenerated do_not_delete_during_merge Mon Oct 19 15:04:31 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_pm_system
     *
     * @mbggenerated Mon Oct 19 15:04:31 CST 2015
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