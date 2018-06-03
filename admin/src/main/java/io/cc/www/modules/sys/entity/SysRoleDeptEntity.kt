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

import java.io.Serializable

/**
 * 角色与部门对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:28:13
 */
@TableName("sys_role_dept")
class SysRoleDeptEntity : Serializable {
    /**
     * 获取：
     * @return Long
     */
    /**
     * 设置：
     * @param id
     */
    @TableId
    var id: Long? = null

    /**
     * 角色ID
     */
    /**
     * 获取：角色ID
     * @return Long
     */
    /**
     * 设置：角色ID
     * @param roleId 角色ID
     */
    var roleId: Long? = null

    /**
     * 部门ID
     */
    /**
     * 获取：部门ID
     * @return Long
     */
    /**
     * 设置：部门ID
     * @param deptId 部门ID
     */
    var deptId: Long? = null

    companion object {
        private const val serialVersionUID = 1L
    }

}
