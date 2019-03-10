package com.tiger.rabbitmq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PageController {

//    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    /**
     * 转发到index页面
     *
     * @return
     */
    @GetMapping("/")
    public ModelAndView toIndex() {
        return new ModelAndView("html/index");
    }

    /**
     * 转发到index页面
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "html/index";
    }

    /**
     * 转发到 配置 页面
     *
     * @return
     */
    @GetMapping("/manager")
    public String toManager() {
        return "html/manager";
    }

}
