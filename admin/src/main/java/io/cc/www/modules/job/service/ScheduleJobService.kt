package io.cc.www.modules.job.service

import com.baomidou.mybatisplus.service.IService
import io.cc.www.common.utils.PageUtils
import io.cc.www.modules.job.entity.ScheduleJobEntity

interface ScheduleJobService : IService<ScheduleJobEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils

    /**
     * 保存定时任务
     */
    fun save(scheduleJob: ScheduleJobEntity)

    /**
     * 更新定时任务
     */
    fun update(scheduleJob: ScheduleJobEntity)

    /**
     * 批量删除定时任务
     */
    fun deleteBatch(jobIds: Array<Long>)

    /**
     * 批量更新定时任务状态
     */
    fun updateBatch(jobIds: Array<Long>, status: Int): Int

    /**
     * 立即执行
     */
    fun run(jobIds: Array<Long>)

    /**
     * 暂停运行
     */
    fun pause(jobIds: Array<Long>)

    /**
     * 恢复运行
     */
    fun resume(jobIds: Array<Long>)
}
