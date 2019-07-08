package com.shouzan.gate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName: com.shouzan.gate.config.FileConfig
 * @Author: bin.yang
 * @Date: 2019/5/17 11:14
 * @Description: TODO
 */
//@Configuration
//public class FileConfig extends WebMvcConfigurationSupport {
//
//    //配置文件配置的物理保存地址
//    @Value("${filePath}")
//    private String filePath;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/").addResourceLocations("file:" + filePath);
//        super.addResourceHandlers(registry);
//    }
//
//    @Bean
//    public RequestContextListener requestContextListener() {
//        return new RequestContextListener();
//    }
//
//}
