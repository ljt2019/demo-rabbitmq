package com.tiger.rabbitmq.dto;

/**
 * Create by tiger on 2019/3/6
 */
public class QueryInDTO  extends PageInDTO {

    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
