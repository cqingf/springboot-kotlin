package io.cc.www.modules.oss.entity

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*

@TableName("sys_oss")
class SysOssEntity : Serializable {

    /**
     * 获取：
     */
    /**
     * 设置：
     */
    @TableId
    var id: Long? = null
    /**
     * URL地址
     */
    /**
     * 获取：URL地址
     */
    /**
     * 设置：URL地址
     */
    var url: String? = null
    /**
     * 创建时间
     */
    /**
     * 获取：创建时间
     */
    /**
     * 设置：创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createDate: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
