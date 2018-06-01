package io.cc.www.datasources

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import javax.sql.DataSource

class DynamicDataSource(defaultTargetDataSource: DataSource, targetDataSources: Map<Any, Any>) : AbstractRoutingDataSource() {

    init {
        super.setDefaultTargetDataSource(defaultTargetDataSource)
        super.setTargetDataSources(targetDataSources)
        super.afterPropertiesSet()
    }

    override fun determineCurrentLookupKey(): Any {
        return dataSource
    }

    companion object {
        private val contextHolder = ThreadLocal<String>()

        var dataSource: String
            get() = contextHolder.get()
            set(dataSource) = contextHolder.set(dataSource)

        fun clearDataSource() {
            contextHolder.remove()
        }
    }

}
