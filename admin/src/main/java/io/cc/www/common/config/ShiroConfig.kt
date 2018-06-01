package io.cc.www.common.config

import io.cc.www.modules.sys.shiro.RedisShiroSessionDAO
import io.cc.www.modules.sys.shiro.UserRealm
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.session.mgt.SessionManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.LinkedHashMap

@Configuration
open class ShiroConfig {

    @Bean("sessionManager")
    open fun sessionManager(redisShiroSessionDAO: RedisShiroSessionDAO,
                            @Value("\${cc.redis.open}") redisOpen: Boolean,
                            @Value("\${cc.shiro.redis}") shiroRedis: Boolean): SessionManager {
        val sessionManager = DefaultWebSessionManager()
        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.globalSessionTimeout = (60 * 60 * 1000).toLong()
        sessionManager.isSessionValidationSchedulerEnabled = true
        sessionManager.isSessionIdUrlRewritingEnabled = false

        //如果开启redis缓存且renren.shiro.redis=true，则shiro session存到redis里
        if (redisOpen && shiroRedis) {
            sessionManager.sessionDAO = redisShiroSessionDAO
        }
        return sessionManager
    }

    @Bean("securityManager")
    open fun securityManager(userRealm: UserRealm, sessionManager: SessionManager): SecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(userRealm)
        securityManager.sessionManager = sessionManager

        return securityManager
    }


    @Bean("shiroFilter")
    open fun shiroFilter(securityManager: SecurityManager): ShiroFilterFactoryBean {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.securityManager = securityManager
        shiroFilter.loginUrl = "/login.html"
        shiroFilter.unauthorizedUrl = "/"

        val filterMap = LinkedHashMap<String, String>()
        filterMap["/swagger/**"] = "anon"
        filterMap["/v2/api-docs"] = "anon"
        filterMap["/swagger-ui.html"] = "anon"
        filterMap["/webjars/**"] = "anon"
        filterMap["/swagger-resources/**"] = "anon"
        filterMap["/statics/**"] = "anon"
        filterMap["/login.html"] = "anon"
        filterMap["/sys/login"] = "anon"
        filterMap["/favicon.ico"] = "anon"
        filterMap["/captcha.jpg"] = "anon"
        filterMap["/**"] = "authc"
        shiroFilter.filterChainDefinitionMap = filterMap
        return shiroFilter
    }

    @Bean("lifecycleBeanPostProcessor")
    open fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }

    @Bean
    open fun defaultAdvisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        val proxyCreator = DefaultAdvisorAutoProxyCreator()
        proxyCreator.isProxyTargetClass = true
        return proxyCreator
    }

    @Bean
    open fun authorizationAttributeSourceAdvisor(securityManager: SecurityManager): AuthorizationAttributeSourceAdvisor {
        val advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }
}
