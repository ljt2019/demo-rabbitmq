package com.tiger.rabbitmq.mybatis.query;

import java.util.LinkedList;
import java.util.List;

/**
 * Create by ljt on 20190306
 */
public class CriteriaList {

    private LinkedList<Criteria> list;

    public CriteriaList() {
        list = new LinkedList<Criteria>();
    }

    public boolean and(Criteria e) {
        if (list.size() != 0)
            list.add(Query.and);
        return list.add(e);
    }

    public boolean or(Criteria e) {
        if (list.size() != 0)
            list.add(Query.or);
        return list.add(e);
    }

    public boolean add(Criteria e) {
        return list.add(e);
    }

    public List<Criteria> getData() {
        return this.list;
    }

}
