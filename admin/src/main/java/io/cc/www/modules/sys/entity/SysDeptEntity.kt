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
import com.baomidou.mybatisplus.annotations.TableLogic
import com.baomidou.mybatisplus.annotations.TableName

import java.io.Serializable


/**
 * 部门管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@TableName("sys_dept")
class SysDeptEntity : Serializable {

    //部门ID
    @TableId
    var deptId: Long? = null
    //上级部门ID，一级部门为0
    /**
     * 获取：上级部门ID，一级部门为0
     */
    /**
     * 设置：上级部门ID，一级部门为0
     */
    var parentId: Long? = null
    //部门名称
    /**
     * 获取：部门名称
     */
    /**
     * 设置：部门名称
     */
    var name: String? = null
    //上级部门名称
    @TableField(exist = false)
    var parentName: String? = null
    //排序
    /**
     * 获取：排序
     */
    /**
     * 设置：排序
     */
    var orderNum: Int? = null

    @TableLogic
    var delFlag: Int? = null
    /**
     * ztree属性
     */
    @TableField(exist = false)
    var open: Boolean? = null
    @TableField(exist = false)
    var list: List<*>? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
