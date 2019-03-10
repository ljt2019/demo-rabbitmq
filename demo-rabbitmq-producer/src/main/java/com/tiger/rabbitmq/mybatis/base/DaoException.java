package com.tiger.rabbitmq.mybatis.base;

/**
 * Create by ljt on 20190306
 */
public class DaoException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 6871023031971047106L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
