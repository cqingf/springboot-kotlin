package io.cc.www.common.config

import io.cc.www.common.xss.XssFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.DelegatingFilterProxy
import javax.servlet.DispatcherType
import javax.servlet.Filter

@Configuration
open class FilterConfig {

    @Bean
    open fun shiroFilterRegistration(): FilterRegistrationBean<*> {
        val registration = FilterRegistrationBean<Filter>()
        registration.setFilter(DelegatingFilterProxy("shiroFilter"))
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true")
        registration.setEnabled(true)
        registration.setOrder(Integer.MAX_VALUE - 1)
        registration.addUrlPatterns("/*")
        return registration
    }

    @Bean
    open fun xssFilterRegistration(): FilterRegistrationBean<*> {
        val registration = FilterRegistrationBean<Filter>()
        registration.setDispatcherTypes(DispatcherType.REQUEST)
        registration.setFilter(XssFilter())
        registration.addUrlPatterns("/*")
        registration.setName("xssFilter")
        registration.setOrder(Integer.MAX_VALUE)
        return registration
    }
}
