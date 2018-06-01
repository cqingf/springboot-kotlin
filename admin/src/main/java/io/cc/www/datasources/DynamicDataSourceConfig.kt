package io.cc.www.datasources

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.HashMap
import javax.sql.DataSource

@Configuration
open class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    open fun firstDataSource(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    open fun secondDataSource(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }

    @Bean
    @Primary
    open fun dataSource(firstDataSource: DataSource, secondDataSource: DataSource): DynamicDataSource {
        val targetDataSources = HashMap<Any, Any>()
        targetDataSources[DataSourceNames.FIRST] = firstDataSource
        targetDataSources[DataSourceNames.SECOND] = secondDataSource
        return DynamicDataSource(firstDataSource, targetDataSources)
    }
}
