package com.epolsoft.brest;

import com.epolsoft.brest.config.AppConfig;
import com.epolsoft.brest.service.api.ResaveService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ResaveService resaveService = (ResaveService) ctx.getBean("resaveService");
        resaveService.readFileName();
        resaveService.service();
    }
}
