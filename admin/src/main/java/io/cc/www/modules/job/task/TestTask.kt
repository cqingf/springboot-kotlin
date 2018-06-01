package io.cc.www.modules.job.task

import io.cc.www.modules.sys.service.SysUserService
import org.apache.commons.lang.builder.ToStringBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("testTask")
class TestTask {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private val sysUserService: SysUserService? = null

    fun test(params: String) {
        logger.info("我是带参数的test方法，正在被执行，参数为：$params")

        try {
            Thread.sleep(1000L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val user = sysUserService!!.selectById(1L)
        println(ToStringBuilder.reflectionToString(user))

    }


    fun test2() {
        logger.info("我是不带参数的test2方法，正在被执行")
    }
}
