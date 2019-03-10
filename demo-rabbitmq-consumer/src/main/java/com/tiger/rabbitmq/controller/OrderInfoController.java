package com.tiger.rabbitmq.controller;

import com.tiger.rabbitmq.dto.AppPageResultObj;
import com.tiger.rabbitmq.dto.AppResultObj;
import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.entity.OrderInfo_;
import com.tiger.rabbitmq.mybatis.base.PageParameter;
import com.tiger.rabbitmq.mybatis.query.Query;
import com.tiger.rabbitmq.service.OrderInfoService;
import com.tiger.rabbitmq.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shell")
@Api("消费者")
public class OrderInfoController {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 查询列表
     *
     * @param page
     * @param count
     * @return
     */
    @GetMapping("/getList")
    @ApiOperation(value = "查询列表", notes = "查询列表")
    public AppResultObj getList(@RequestParam("page") Integer page, @RequestParam("count") Integer count) {
        PageParameter pageParameter = new PageParameter(page == null ? 0 : page, count == null ? 10 : count);
        try {
            List<OrderInfo> listOut = orderInfoService.query(Query.newQuery().where(OrderInfo_.state.equal(1), Query.orderby, OrderInfo_.createDate.asc()), pageParameter);
            logger.info("====查询列表成功！【{}】", JsonUtils.toJson(listOut));
            return AppPageResultObj.success(listOut, pageParameter);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultObj.serverError(e.getMessage());
        }
    }


}
