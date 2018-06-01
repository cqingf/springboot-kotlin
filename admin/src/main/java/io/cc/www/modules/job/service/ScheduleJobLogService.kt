package io.cc.www.modules.job.service

import com.baomidou.mybatisplus.service.IService
import io.cc.www.common.utils.PageUtils
import io.cc.www.modules.job.entity.ScheduleJobLogEntity

interface ScheduleJobLogService : IService<ScheduleJobLogEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils

}
