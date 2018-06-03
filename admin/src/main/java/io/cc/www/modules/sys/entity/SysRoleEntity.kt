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

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.io.Serializable
import java.util.Date

/**
 * 角色
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:27:38
 */
@TableName("sys_role")
class SysRoleEntity : Serializable {

    /**
     * 角色ID
     */
    /**
     * 获取：
     * @return Long
     */
    /**
     * 设置：
     * @param roleId
     */
    @TableId
    var roleId: Long? = null

    /**
     * 角色名称
     */
    /**
     * 获取：角色名称
     * @return String
     */
    /**
     * 设置：角色名称
     * @param roleName 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    var roleName: String? = null

    /**
     * 备注
     */
    /**
     * 获取：备注
     * @return String
     */
    /**
     * 设置：备注
     * @param remark 备注
     */
    var remark: String? = null

    /**
     * 部门ID
     */
    @NotNull(message = "部门不能为空")
    var deptId: Long? = null

    /**
     * 部门名称
     */
    @TableField(exist = false)
    var deptName: String? = null

    @TableField(exist = false)
    var menuIdList: List<Long>? = null
    @TableField(exist = false)
    var deptIdList: List<Long>? = null

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
