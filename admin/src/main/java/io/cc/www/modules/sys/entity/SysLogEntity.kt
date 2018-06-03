/**
 * Copyright 2018 人人开源 http://www.renren.io
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cc.www.modules.sys.entity

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.fasterxml.jackson.annotation.JsonFormat

import java.io.Serializable
import java.util.Date


/**
 * 系统日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
@TableName("sys_log")
class SysLogEntity : Serializable {
    /**
     * 获取：
     */
    /**
     * 设置：
     */
    @TableId
    var id: Long? = null
    //用户名
    /**
     * 获取：用户名
     */
    /**
     * 设置：用户名
     */
    var username: String? = null
    //用户操作
    /**
     * 获取：用户操作
     */
    /**
     * 设置：用户操作
     */
    var operation: String? = null
    //请求方法
    /**
     * 获取：请求方法
     */
    /**
     * 设置：请求方法
     */
    var method: String? = null
    //请求参数
    /**
     * 获取：请求参数
     */
    /**
     * 设置：请求参数
     */
    var params: String? = null
    //执行时长(毫秒)
    var time: Long? = null
    //IP地址
    /**
     * 获取：IP地址
     */
    /**
     * 设置：IP地址
     */
    var ip: String? = null
    //创建时间
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
