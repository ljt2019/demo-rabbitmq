package com.tiger.rabbitmq.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tiger.rabbitmq.dao.BrokerMessageLogMapper;
import com.tiger.rabbitmq.entity.BrokerMessageLog;
import com.tiger.rabbitmq.entity.BrokerMessageLogExample;
import com.tiger.rabbitmq.service.BrokerMessageLogService;
import com.tiger.rabbitmq.mybatis.base.IBaseMapper;
import com.tiger.rabbitmq.mybatis.base.BaseServiceImpl;

import java.util.List;
import com.tiger.rabbitmq.mybatis.query.Query;
import com.tiger.rabbitmq.mybatis.base.PageParameter;

@Service
@Transactional
public class BrokerMessageLogServiceImpl extends BaseServiceImpl<BrokerMessageLog, BrokerMessageLogExample> implements BrokerMessageLogService {

    @Autowired
	private BrokerMessageLogMapper baseMapper;



	@Override
	public IBaseMapper<BrokerMessageLog, BrokerMessageLogExample> getBaseMapper() {
		return baseMapper;
	}

	@Override
    public List<BrokerMessageLog> query(Query query, PageParameter pageParameter) {
    	return baseMapper.query(query,pageParameter);
    }
}