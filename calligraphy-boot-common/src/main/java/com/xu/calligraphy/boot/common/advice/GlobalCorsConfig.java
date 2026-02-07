package com.xu.calligraphy.boot.common.advice;

/**
 * @author xyq
 * @date 2026/2/7 17:40
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局CORS配置（解决跨域问题）
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有前端域名（开发环境用*，生产环境替换为具体域名如http://your-domain.com）
//        config.addAllowedOriginPattern("*");
        config.addAllowedOrigin("*");
        // 允许跨域携带Cookie（如需登录态则开启）
        config.setAllowCredentials(true);
        // 允许所有请求方法（GET/POST/PUT/DELETE等）
        config.addAllowedMethod("*");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 预检请求有效期（秒），避免频繁OPTIONS请求
        config.setMaxAge(3600L);

        // 2. 配置生效路径（所有接口）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 3. 返回CORS过滤器
        return new CorsFilter(source);
    }
}