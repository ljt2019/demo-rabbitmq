package com.tiger.rabbitmq.mybatis.query;

import java.util.List;

/**
 * Create by ljt on 20190306
 */
public class Property {

    private String column;

    public Property() {

    }

    public Property(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Criteria like(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " like ", value);
    }

    public Criteria notLike(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " not like ", value);
    }

    public Criteria equal(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " = ", value);
    }

    public Criteria notEqual(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " != ", value);
    }

    public Criteria less(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " < ", value);
    }

    public Criteria lessOrEqual(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " <= ", value);
    }

    public Criteria greater(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " > ", value);
    }

    public Criteria greaterOrEqual(Object value) {
        return new Query.Criterion(" " + this.getColumn() + " >= ", value);
    }

    public Criteria isNull() {
        return new Query.Criterion(" " + this.getColumn() + " is null ");
    }

    public Criteria isNotNull() {
        return new Query.Criterion(" " + this.getColumn() + " is not null ");
    }

    public Criteria in(List<?> value) {
        return new Query.Criterion(" " + this.getColumn() + " in ", value);
    }

    public Criteria notIn(List<?> value) {
        return new Query.Criterion(" " + this.getColumn() + " not in ", value);
    }

    public Criteria between(Object value1, Object value2) {
        return new Query.Criterion(" " + this.getColumn() + " between ", value1, value2);
    }

    public Criteria notBetween(Object value1, Object value2) {
        return new Query.Criterion(" " + this.getColumn() + " not between ", value1, value2);
    }

    public Criteria desc() {
        return new Query.Criterion(" " + this.getColumn() + " desc ");
    }

    public Criteria asc() {
        return new Query.Criterion(" " + this.getColumn() + " asc ");
    }

    public Criteria groupBy() {
        return new Query.Criterion(" group by " + this.getColumn());
    }
}
