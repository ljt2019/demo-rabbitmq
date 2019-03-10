package com.tiger.rabbitmq.controller;

import com.tiger.rabbitmq.dto.AppPageResultObj;
import com.tiger.rabbitmq.dto.AppResultObj;
import com.tiger.rabbitmq.dto.OrderInfoInDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mq")
@Api("mq学习")
public class OrderInfoController {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    OrderInfoService orderInfoService;

//    @Autowired
//    OrderSender orderSender;

    /**
     * 查询订单列表
     *
     * @param page
     * @param count
     * @return
     */
    @GetMapping("/getList")
    @ApiOperation(value = "查询订单列表", notes = "查询订单列表")
    public AppResultObj getList(@RequestParam("page") Integer page, @RequestParam("count") Integer count) {
        PageParameter pageParameter = new PageParameter(page == null ? 0 : page, count == null ? 10 : count);
        try {
            List<OrderInfo> listOut = orderInfoService.query(Query.newQuery().where(OrderInfo_.state.equal(1), Query.orderby, OrderInfo_.createDate.asc()), pageParameter);
            logger.info("====查询订单列表成功！【{}】", JsonUtils.toJson(listOut));
            return AppPageResultObj.success(listOut, pageParameter);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultObj.serverError(e.getMessage());
        }
    }

    /**
     * 创建订单
     *
     * @param param
     * @return
     */
    @PostMapping("/createOrder")
    @ApiOperation(value = "创建订单", notes = "创建订单")
    public AppResultObj createOrder(@RequestBody OrderInfoInDTO param) {
        logger.info("创建订单入参：【{}】", JsonUtils.toJson(param));
        try {
            OrderInfo orderInfo = new OrderInfo();
            BeanUtils.copyProperties(param, orderInfo);
            int flag = orderInfoService.createOrder(orderInfo);
            if (flag == 1) {

                return AppResultObj.success();
            } else {
                return AppResultObj.serverError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultObj.serverError(e.getMessage());
        }
    }

    /**
     * 查询订单
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    @ApiOperation(value = "查询订单", notes = "查询订单")
    public AppResultObj getById(@RequestParam("id") String id) {
        try {
            OrderInfo out = orderInfoService.selectByPrimaryKey(id);
            if (out != null) {
                return AppPageResultObj.success(out);
            } else {
                return AppPageResultObj.cannotFindData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultObj.serverError(e.getMessage());
        }
    }

}
