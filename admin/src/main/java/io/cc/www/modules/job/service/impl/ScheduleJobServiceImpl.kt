package io.cc.www.modules.job.service.impl

import com.baomidou.mybatisplus.mapper.EntityWrapper
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.PageUtils
import io.cc.www.common.utils.Query
import io.cc.www.modules.job.dao.ScheduleJobDao
import io.cc.www.modules.job.entity.ScheduleJobEntity
import io.cc.www.modules.job.service.ScheduleJobService
import io.cc.www.modules.job.utils.ScheduleUtils
import org.apache.commons.lang.StringUtils
import org.quartz.Scheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.annotation.PostConstruct

@Service("scheduleJobService")
open class ScheduleJobServiceImpl : ServiceImpl<ScheduleJobDao, ScheduleJobEntity>(), ScheduleJobService {
    @Autowired
    private val scheduler: Scheduler? = null

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    fun init() {
        val scheduleJobList = this.selectList(null)
        for (scheduleJob in scheduleJobList) {
            val cronTrigger = ScheduleUtils.getCronTrigger(scheduler!!, scheduleJob.jobId)
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob)
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob)
            }
        }
    }

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val beanName = params["beanName"] as String

        val page = this.selectPage(
                Query<ScheduleJobEntity>(params).page,
                EntityWrapper<ScheduleJobEntity>().like(StringUtils.isNotBlank(beanName), "bean_name", beanName)
        )

        return PageUtils(page)
    }


    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun save(scheduleJob: ScheduleJobEntity) {
        scheduleJob.createTime = Date()
        scheduleJob.status = Constant.ScheduleStatus.NORMAL.value
        this.insert(scheduleJob)

        ScheduleUtils.createScheduleJob(scheduler!!, scheduleJob)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun update(scheduleJob: ScheduleJobEntity) {
        ScheduleUtils.updateScheduleJob(scheduler!!, scheduleJob)

        this.updateById(scheduleJob)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun deleteBatch(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler!!, jobId)
        }

        //删除数据
        this.deleteBatchIds(Arrays.asList(*jobIds))
    }

    override fun updateBatch(jobIds: Array<Long>, status: Int): Int {
        val map = HashMap<String, Any>()
        map["list"] = jobIds
        map["status"] = status
        return baseMapper.updateBatch(map)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun run(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.run(scheduler!!, this.selectById(jobId))
        }
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun pause(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.pauseJob(scheduler!!, jobId)
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.value)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun resume(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.resumeJob(scheduler!!, jobId)
        }

        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.value)
    }

}
