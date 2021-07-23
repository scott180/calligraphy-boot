package com.xu.calligraphy.boot.common.advice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author xyq
 * @date 2021/7/23 14:55
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    //通过重写配置方法覆盖

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mapper/**").addResourceLocations("classpath:/mapper/");//mapper.xml
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }
}
