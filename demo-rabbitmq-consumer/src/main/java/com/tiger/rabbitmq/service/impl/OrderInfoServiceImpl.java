package com.tiger.rabbitmq.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tiger.rabbitmq.dao.OrderInfoMapper;
import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.entity.OrderInfoExample;
import com.tiger.rabbitmq.service.OrderInfoService;
import com.tiger.rabbitmq.mybatis.base.IBaseMapper;
import com.tiger.rabbitmq.mybatis.base.BaseServiceImpl;

import java.util.List;
import com.tiger.rabbitmq.mybatis.query.Query;
import com.tiger.rabbitmq.mybatis.base.PageParameter;

@Service
@Transactional
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, OrderInfoExample> implements OrderInfoService {

    @Autowired
	private OrderInfoMapper baseMapper;



	@Override
	public IBaseMapper<OrderInfo, OrderInfoExample> getBaseMapper() {
		return baseMapper;
	}

	@Override
    public List<OrderInfo> query(Query query, PageParameter pageParameter) {
    	return baseMapper.query(query,pageParameter);
    }
}