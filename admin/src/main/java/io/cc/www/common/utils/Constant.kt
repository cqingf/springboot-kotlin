package io.cc.www.common.utils

object Constant {
    /** 超级管理员ID  */
    val SUPER_ADMIN = 1
    /** 数据权限过滤  */
    val SQL_FILTER = "sql_filter"


    /**
     * 菜单类型
     */
    enum class MenuType(val value: Int) {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2)
    }

    /**
     * 定时任务状态
     */
    enum class ScheduleStatus(val value: Int) {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1)
    }

    /**
     * 云服务商
     */
    enum class CloudService(val value: Int) {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3)
    }

}
