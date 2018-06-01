package io.cc.www.modules.job.service.impl

import com.baomidou.mybatisplus.mapper.EntityWrapper
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import io.cc.www.common.utils.PageUtils
import io.cc.www.common.utils.Query
import io.cc.www.modules.job.dao.ScheduleJobLogDao
import io.cc.www.modules.job.entity.ScheduleJobLogEntity
import io.cc.www.modules.job.service.ScheduleJobLogService
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

@Service("scheduleJobLogService")
class ScheduleJobLogServiceImpl : ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity>(), ScheduleJobLogService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val jobId = params["jobId"] as String

        val page = this.selectPage(
                Query<ScheduleJobLogEntity>(params).page,
                EntityWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId), "job_id", jobId)
        )

        return PageUtils(page)
    }

}
