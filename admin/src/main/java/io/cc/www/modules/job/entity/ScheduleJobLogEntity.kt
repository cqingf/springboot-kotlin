package io.cc.www.modules.job.entity

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*

@TableName("schedule_job_log")
class ScheduleJobLogEntity : Serializable {

    /**
     * 日志id
     */
    @TableId
    var logId: Long? = null

    /**
     * 任务id
     */
    var jobId: Long? = null

    /**
     * spring bean名称
     */
    var beanName: String? = null

    /**
     * 方法名
     */
    var methodName: String? = null

    /**
     * 参数
     */
    var params: String? = null

    /**
     * 任务状态    0：成功    1：失败
     */
    var status: Int? = null

    /**
     * 失败信息
     */
    var error: String? = null

    /**
     * 耗时(单位：毫秒)
     */
    var times: Int? = null

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }

}
