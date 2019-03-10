package com.tiger.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Python
 *
 * @author tiger
 * @date 2019年2月20日
 */
@Configuration
@ConfigurationProperties(prefix = "python")
public class PythonString {

    private String path1;
    private String path2;
    private String path1Picture;
    private String path2Picture;

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }

    public String getPath2() {
        return path2;
    }

    public void setPath2(String path2) {
        this.path2 = path2;
    }

    public String getPath1Picture() {
        return path1Picture;
    }

    public void setPath1Picture(String path1Picture) {
        this.path1Picture = path1Picture;
    }

    public String getPath2Picture() {
        return path2Picture;
    }

    public void setPath2Picture(String path2Picture) {
        this.path2Picture = path2Picture;
    }
}
