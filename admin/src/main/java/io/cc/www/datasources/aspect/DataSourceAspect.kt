package io.cc.www.datasources.aspect

import io.cc.www.datasources.DataSourceNames
import io.cc.www.datasources.DynamicDataSource
import io.cc.www.datasources.annotation.DataSource
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component

@Aspect
@Component
class DataSourceAspect : Ordered {
    protected var logger = LoggerFactory.getLogger(javaClass)

    @Pointcut("@annotation(io.cc.www.datasources.annotation.DataSource)")
    fun dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint): Any {
        val signature = point.signature as MethodSignature
        val method = signature.method

        val ds = method.getAnnotation(DataSource::class.java)
        if (ds == null) {
            DynamicDataSource.setDataSource(DataSourceNames.FIRST)
            logger.debug("set datasource is " + DataSourceNames.FIRST)
        } else {
            DynamicDataSource.setDataSource(ds.name)
            logger.debug("set datasource is " + ds.name)
        }

        try {
            return point.proceed()
        } finally {
            DynamicDataSource.clearDataSource()
            logger.debug("clean datasource")
        }
    }

    override fun getOrder(): Int {
        return 1
    }
}
