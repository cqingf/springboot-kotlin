package io.cc.www.modules.job.controller

import io.cc.www.common.utils.R
import io.cc.www.modules.job.service.ScheduleJobLogService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sys/scheduleLog")
class ScheduleJobLogController {
    @Autowired
    private val scheduleJobLogService: ScheduleJobLogService? = null

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    fun list(@RequestParam params: Map<String, Any>): R? {
        val page = scheduleJobLogService!!.queryPage(params)

        return R.ok().put("page", page)
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    fun info(@PathVariable("logId") logId: Long?): R? {
        val log = scheduleJobLogService!!.selectById(logId)

        return R.ok().put("log", log)
    }
}
