package io.cc.www.modules.job.entity

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotBlank

@TableName("schedule_job")
class ScheduleJobEntity : Serializable {

    /**
     * 任务id
     */
    /**
     * 获取：任务id
     * @return Long
     */
    /**
     * 设置：任务id
     * @param jobId 任务id
     */
    @TableId
    var jobId: Long? = null

    /**
     * spring bean名称
     */
    @NotBlank(message = "bean名称不能为空")
    var beanName: String? = null

    /**
     * 方法名
     */
    @NotBlank(message = "方法名称不能为空")
    var methodName: String? = null

    /**
     * 参数
     */
    var params: String? = null

    /**
     * cron表达式
     */
    /**
     * 获取：cron表达式
     * @return String
     */
    /**
     * 设置：cron表达式
     * @param cronExpression cron表达式
     */
    @NotBlank(message = "cron表达式不能为空")
    var cronExpression: String? = null

    /**
     * 任务状态
     */
    /**
     * 获取：任务状态
     * @return String
     */
    /**
     * 设置：任务状态
     * @param status 任务状态
     */
    var status: Int? = null

    /**
     * 备注
     */
    var remark: String? = null

    /**
     * 创建时间
     */
    /**
     * 获取：创建时间
     * @return Date
     */
    /**
     * 设置：创建时间
     * @param createTime 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L

        /**
         * 任务调度参数key
         */
        val JOB_PARAM_KEY = "JOB_PARAM_KEY"
    }
}
