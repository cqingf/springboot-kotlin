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


import com.baomidou.mybatisplus.annotations.TableField
import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.cc.www.common.validator.group.AddGroup
import io.cc.www.common.validator.group.UpdateGroup

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.io.Serializable
import java.util.Date

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:55
 */
@TableName("sys_user")
class SysUserEntity : Serializable {

    /**
     * 用户ID
     */
    /**
     * 获取：
     *
     * @return Long
     */
    /**
     * 设置：
     *
     * @param userId
     */
    @TableId
    var userId: Long? = null

    /**
     * 用户名
     */
    /**
     * 获取：用户名
     *
     * @return String
     */
    /**
     * 设置：用户名
     *
     * @param username 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = arrayOf(AddGroup::class, UpdateGroup::class))
    var username: String? = null

    /**
     * 密码
     */
    /**
     * 获取：密码
     *
     * @return String
     */
    /**
     * 设置：密码
     *
     * @param password 密码
     */
    @NotBlank(message = "密码不能为空", groups = arrayOf(AddGroup::class))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String? = null

    /**
     * 盐
     */
    var salt: String? = null

    /**
     * 邮箱
     */
    /**
     * 获取：邮箱
     *
     * @return String
     */
    /**
     * 设置：邮箱
     *
     * @param email 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = arrayOf(AddGroup::class, UpdateGroup::class))
    @Email(message = "邮箱格式不正确", groups = arrayOf(AddGroup::class, UpdateGroup::class))
    var email: String? = null

    /**
     * 手机号
     */
    /**
     * 获取：手机号
     *
     * @return String
     */
    /**
     * 设置：手机号
     *
     * @param mobile 手机号
     */
    var mobile: String? = null

    /**
     * 状态  0：禁用   1：正常
     */
    /**
     * 获取：状态  0：禁用   1：正常
     *
     * @return Integer
     */
    /**
     * 设置：状态  0：禁用   1：正常
     *
     * @param status 状态  0：禁用   1：正常
     */
    var status: Int? = null

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    var roleIdList: List<Long>? = null

    /**
     * 创建时间
     */
    /**
     * 获取：创建时间
     *
     * @return Date
     */
    /**
     * 设置：创建时间
     *
     * @param createTime 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: Date? = null

    /**
     * 部门ID
     */
    @NotNull(message = "部门不能为空", groups = arrayOf(AddGroup::class, UpdateGroup::class))
    var deptId: Long? = null

    /**
     * 部门名称
     */
    @TableField(exist = false)
    var deptName: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
