package com.xxl.sso.demo.config;

import com.xxl.sso.core.conf.Conf;
import com.xxl.sso.core.filter.XxlSsoTokenFilter;
import com.xxl.sso.core.filter.XxlSsoWebFilter;
import com.xxl.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuxueli 2018-11-15
 */
@Configuration
public class XxlSsoConfig implements DisposableBean {


    @Value("${xxl.sso.server}")
    private String xxlSsoServer;

    @Value("${xxl.sso.logout.path}")
    private String xxlSsoLogoutPath;

    @Value("${xxl.sso.redis.address}")
    private String xxlSsoRedisAddress;

    @Value("${xxl-sso.excluded.paths}")
    private String xxlSsoExcludedPaths;


    @Bean
    public FilterRegistrationBean xxlSsoFilterRegistration() {

        // xxl-sso, redis init
        JedisUtil.init(xxlSsoRedisAddress);

        // xxl-sso, filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("XxlSsoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new XxlSsoTokenFilter());
//        registration.setFilter(new XxlSsoWebFilter());
        //xxlSsoServer用来拼接sso server登录/登出接口,进行重定向跳转,而且只要到登录界面就要带上回调地址,否则回跳到服务器内部默认页面
        registration.addInitParameter(Conf.SSO_SERVER, xxlSsoServer);
        //xxlSsoLogoutPath用来指定登出的路径,相对contextPath
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, xxlSsoLogoutPath);
        //xxlSsoExcludedPaths用来指定不需要校验的路径,相对contextPath,多个用逗号分隔,支持ANT表达式
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, xxlSsoExcludedPaths);

        return registration;
    }

    @Override
    public void destroy() throws Exception {

        // xxl-sso, redis close
        JedisUtil.close();
    }

}
