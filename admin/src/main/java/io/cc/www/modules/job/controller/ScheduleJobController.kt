package io.cc.www.modules.job.controller

import io.cc.www.common.annotation.SysLog
import io.cc.www.common.utils.R
import io.cc.www.common.validator.ValidatorUtils
import io.cc.www.modules.job.entity.ScheduleJobEntity
import io.cc.www.modules.job.service.ScheduleJobService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sys/schedule")
class ScheduleJobController {
    @Autowired
    private val scheduleJobService: ScheduleJobService? = null

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    fun list(@RequestParam params: Map<String, Any>): R? {
        val page = scheduleJobService!!.queryPage(params)

        return R.ok().put("page", page)
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    fun info(@PathVariable("jobId") jobId: Long?): R? {
        val schedule = scheduleJobService!!.selectById(jobId)

        return R.ok().put("schedule", schedule)
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    fun save(@RequestBody scheduleJob: ScheduleJobEntity): R {
        ValidatorUtils.validateEntity(scheduleJob)

        scheduleJobService!!.save(scheduleJob)

        return R.ok()
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    fun update(@RequestBody scheduleJob: ScheduleJobEntity): R {
        ValidatorUtils.validateEntity(scheduleJob)

        scheduleJobService!!.update(scheduleJob)

        return R.ok()
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    fun delete(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService!!.deleteBatch(jobIds)

        return R.ok()
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    fun run(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService!!.run(jobIds)

        return R.ok()
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    fun pause(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService!!.pause(jobIds)

        return R.ok()
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    fun resume(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService!!.resume(jobIds)

        return R.ok()
    }

}
