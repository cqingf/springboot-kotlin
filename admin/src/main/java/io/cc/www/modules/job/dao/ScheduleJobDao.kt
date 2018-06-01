package io.cc.www.modules.job.dao

import com.baomidou.mybatisplus.mapper.BaseMapper
import io.cc.www.modules.job.entity.ScheduleJobEntity

interface ScheduleJobDao : BaseMapper<ScheduleJobEntity> {

    /**
     * 批量更新状态
     */
    fun updateBatch(map: Map<String, Any>): Int
}
