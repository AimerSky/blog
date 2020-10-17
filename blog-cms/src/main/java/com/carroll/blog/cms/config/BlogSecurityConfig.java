package com.carroll.blog.cms.config;

import com.carroll.blog.cms.service.CmsManagerService;
import com.carroll.blog.cms.service.CmsResourceService;
import com.carroll.blog.mbg.model.CmsResource;
import com.carroll.blog.security.component.DynamicSecurityService;
import com.carroll.blog.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * blog-security模块相关配置
 * Created by carroll on 2020/5/30.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BlogSecurityConfig extends SecurityConfig {

    @Autowired
    private CmsManagerService cmsManagerService;
    @Autowired
    private CmsResourceService cmsResourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> cmsManagerService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<CmsResource> resourceList = cmsResourceService.listAll();
                for (CmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getResourceId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
