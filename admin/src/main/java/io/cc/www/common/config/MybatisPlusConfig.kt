package io.cc.www.common.config

import com.baomidou.mybatisplus.plugins.PaginationInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    open fun paginationInterceptor(): PaginationInterceptor {
        return PaginationInterceptor()
    }
}
