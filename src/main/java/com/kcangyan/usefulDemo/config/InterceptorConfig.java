package com.kcangyan.usefulDemo.config;

import com.kcangyan.usefulDemo.model.TestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] add = {
                "/mongo",
                "/mysql"
        };
        String[] exclude = {};
        registry.addInterceptor(new TestInterceptor()).addPathPatterns(add).excludePathPatterns(exclude);
    }
}
