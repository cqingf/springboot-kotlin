package io.cc.www.common.aspect


import com.google.gson.Gson
import io.cc.www.common.annotation.SysLog
import io.cc.www.common.utils.HttpContextUtils
import io.cc.www.common.utils.IPUtils
import io.cc.www.modules.sys.entity.SysLogEntity
import io.cc.www.modules.sys.entity.SysUserEntity
import io.cc.www.modules.sys.service.SysLogService
import org.apache.shiro.SecurityUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
class SysLogAspect {
    @Autowired
    private val sysLogService: SysLogService? = null

    @Pointcut("@annotation(io.cc.www.common.annotation.SysLog)")
    fun logPointCut() {

    }

    @Around("logPointCut()")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint): Any {
        val beginTime = System.currentTimeMillis()
        //执行方法
        val result = point.proceed()
        //执行时长(毫秒)
        val time = System.currentTimeMillis() - beginTime

        //保存日志
        saveSysLog(point, time)

        return result
    }

    private fun saveSysLog(joinPoint: ProceedingJoinPoint, time: Long) {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method

        val sysLog = SysLogEntity()
        val syslog = method.getAnnotation(SysLog::class.java)
        if (syslog != null) {
            //注解上的描述
            sysLog.operation = syslog.value
        }

        //请求的方法名
        val className = joinPoint.target.javaClass.name
        val methodName = signature.name
        sysLog.method = "$className.$methodName()"

        //请求的参数
        val args = joinPoint.args
        try {
            val params = Gson().toJson(args[0])
            sysLog.params = params
        } catch (e: Exception) {

        }

        //获取request
        val request = HttpContextUtils.httpServletRequest
        //设置IP地址
        sysLog.ip = IPUtils.getIpAddr(request)

        //用户名
        val username = (SecurityUtils.getSubject().principal as SysUserEntity).username
        sysLog.username = username

        sysLog.time = time
        sysLog.createDate = Date()
        //保存系统日志
        sysLogService!!.insert(sysLog)
    }
}
