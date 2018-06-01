package io.cc.www.modules.job.utils

import io.cc.www.common.exception.RRException
import io.cc.www.common.utils.SpringContextUtils
import org.apache.commons.lang.StringUtils
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method

class ScheduleRunnable @Throws(NoSuchMethodException::class, SecurityException::class)
constructor(beanName: String, methodName: String, private val params: String) : Runnable {
    private val target: Any
    private var method: Method? = null

    init {
        this.target = SpringContextUtils.getBean(beanName)

        if (StringUtils.isNotBlank(params)) {
            this.method = target.javaClass.getDeclaredMethod(methodName, String::class.java)
        } else {
            this.method = target.javaClass.getDeclaredMethod(methodName)
        }
    }

    override fun run() {
        try {
            ReflectionUtils.makeAccessible(method!!)
            if (StringUtils.isNotBlank(params)) {
                method!!.invoke(target, params)
            } else {
                method!!.invoke(target)
            }
        } catch (e: Exception) {
            throw RRException("执行定时任务失败", e)
        }

    }

}
