package com.tiger.rabbitmq.mybatis.query;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by ljt on 20190306
 */
public class Query {

    private List<SQLFragment> fragments = new ArrayList<>();

    public Query() {

    }

    public Query(Criteria... criterias) {
        this.where(criterias);
    }

    public Query where(Criteria... criterias) {
        if (criterias != null && criterias.length > 0) {
            fragments.add(new SQLFragment(" where "));
            for (Criteria criteria : criterias) {
                fragments.addAll(criteria.getSQLFragments());
            }
        }
        return this;
    }

    public Query where(List<Criteria> criterias) {
        if (criterias != null && criterias.size() > 0) {
            fragments.add(new SQLFragment(" where "));
            for (Criteria criteria : criterias) {
                fragments.addAll(criteria.getSQLFragments());
            }
        }
        return this;
    }

    public static Query newQuery() {
        return new Query();
    }

    public Query orderBy(Criteria... criterias) {
        if (criterias != null && criterias.length > 0) {
            fragments.add(new SQLFragment(" order by "));
            for (Criteria criteria : criterias) {
                fragments.addAll(criteria.getSQLFragments());
                fragments.addAll(new Criterion(" , ").getSQLFragments());
            }
            fragments.remove(fragments.size() - 1);
        }
        return this;
    }

    public Query groupBy(Property... propertys) {
        if (propertys != null && propertys.length > 0) {
            fragments.add(new SQLFragment(" group by "));
            for (Property property : propertys) {
                fragments.addAll(new Criterion(property.getColumn()).getSQLFragments());
                fragments.addAll(new Criterion(" , ").getSQLFragments());
            }
            fragments.remove(fragments.size() - 1);
        }
        return this;
    }

    public boolean isValid() {
        return CollectionUtils.isNotEmpty(this.fragments);
    }

    public String preview() {
        StringBuffer stringBuffer = new StringBuffer();
        for (SQLFragment sqlFragment : this.fragments) {
            if (sqlFragment.isSqlFragment()) {
                stringBuffer.append(sqlFragment.getSql());
            }
            if (sqlFragment.isValueFragment()) {
                stringBuffer.append(sqlFragment.getValue());
            }
        }
        return stringBuffer.toString();
    }


    /*public static Criteria and() {
        return new Criterion(" and ");
    }*/
    public static final Criteria and = new Criterion(" and ");

    public static final Criteria orderby = new Criterion(" order by ");

    public static final Criteria constant = new Criterion(" 1=1 ");
    public static final Criteria leftBracket = new Criterion(" ( ");
    public static final Criteria rightBracket = new Criterion(" ) ");

    public static final Criteria binary = new Criterion(" BINARY ");

    /*public static Criteria or() {
        return new Criterion(" or ");
    }*/

    public static final Criteria or = new Criterion(" or ");

    public static Criteria collection(Criteria... criterias) {
        return new CollectionCriterion(criterias);
    }

    public static Criteria like(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " like ", value);
    }

    public static Criteria notLike(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " not like ", value);
    }

    public static Criteria equal(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " = ", value);
    }

    public static Criteria notEqual(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " != ", value);
    }

    public static Criteria less(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " < ", value);
    }

    public static Criteria lessOrEqual(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " <= ", value);
    }

    public static Criteria greater(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " > ", value);
    }

    public static Criteria greaterOrEqual(Property property, Object value) {
        return new Criterion(" " + property.getColumn() + " >= ", value);
    }

    public static Criteria isNull(Property property) {
        return new Criterion(" " + property.getColumn() + " is null ");
    }

    public static Criteria isNotNull(Property property) {
        return new Criterion(" " + property.getColumn() + " is not null ");
    }

    public static Criteria in(Property property, List<?> value) {
        return new Criterion(" " + property.getColumn() + " in ", value);
    }

    public static Criteria notIn(Property property, List<?> value) {
        return new Criterion(" " + property.getColumn() + " not in ", value);
    }

    public static Criteria between(Property property, Object value1, Object value2) {
        return new Criterion(" " + property.getColumn() + " between ", value1, value2);
    }

    public static Criteria notBetween(Property property, Object value1, Object value2) {
        return new Criterion(" " + property.getColumn() + " not between ", value1, value2);
    }

    public static Criteria desc(Property property) {
        return new Criterion(" " + property.getColumn() + " desc ");
    }

    public static Criteria asc(Property property) {
        return new Criterion(" " + property.getColumn() + " asc ");
    }

    public List<SQLFragment> getFragments() {
        return fragments;
    }

    public static Criteria limt(int property) {
        return new Criterion(" LIMIT " + property);
    }

    public static Criteria limt(int property1, int property2) {
        return new Criterion(" LIMIT " + property1 + " , " + property2);
    }

    public static Criteria content(String str) {
        return new Criterion(str);
    }

    public static Criteria comma(int property) {
        return new Criterion(" , " + property);
    }


    private static class CollectionCriterion implements Criteria {

        private Criteria[] criterias;

        public CollectionCriterion(Criteria... criterias) {
            this.criterias = criterias;
        }

        @Override
        public List<SQLFragment> getSQLFragments() {
            if (this.criterias != null && criterias.length != 0) {
                List<SQLFragment> fragments = new ArrayList<>();
                fragments.add(new SQLFragment(" ( "));
                for (Criteria criteria : criterias) {
                    if (criteria != null && CollectionUtils.isNotEmpty(criteria.getSQLFragments())) {
                        fragments.addAll(criteria.getSQLFragments());
                    }
                }
                fragments.add(new SQLFragment(" ) "));
                return fragments;
            }
            return null;
        }
    }

    public static class Criterion implements Criteria {

        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        public Criterion(String condition, Object value, String typeHandler) {
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

        public Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        public Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        public Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }

        @Override
        public List<SQLFragment> getSQLFragments() {
            List<SQLFragment> sqlFragments = new ArrayList<>();
            sqlFragments.add(new SQLFragment(this.condition));
            if (this.singleValue) {
                sqlFragments.add(new SQLFragment(this.value));
            } else if (this.betweenValue) {
                sqlFragments.add(new SQLFragment(this.value));
                sqlFragments.add(new SQLFragment(" and "));
                sqlFragments.add(new SQLFragment(this.secondValue));
            } else if (this.listValue) {
                sqlFragments.add(new SQLFragment(" ( "));
                List<?> values = (List<?>) this.value;
                for (Object value : values) {
                    sqlFragments.add(new SQLFragment(value));
                    sqlFragments.add(new SQLFragment(" , "));
                }
                sqlFragments.remove(sqlFragments.size() - 1);
                sqlFragments.add(new SQLFragment(" ) "));
            }
            return sqlFragments;
        }
    }


}
