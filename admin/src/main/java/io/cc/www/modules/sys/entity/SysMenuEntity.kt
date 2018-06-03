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

import java.io.Serializable

/**
 * 菜单管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:26:39
 */
@TableName("sys_menu")
class SysMenuEntity : Serializable {

    /**
     * 菜单ID
     */
    @TableId
    var menuId: Long? = null

    /**
     * 父菜单ID，一级菜单为0
     */
    /**
     * 获取：父菜单ID，一级菜单为0
     * @return Long
     */
    /**
     * 设置：父菜单ID，一级菜单为0
     * @param parentId 父菜单ID，一级菜单为0
     */
    var parentId: Long? = null

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    var parentName: String? = null

    /**
     * 菜单名称
     */
    /**
     * 获取：菜单名称
     * @return String
     */
    /**
     * 设置：菜单名称
     * @param name 菜单名称
     */
    var name: String? = null

    /**
     * 菜单URL
     */
    /**
     * 获取：菜单URL
     * @return String
     */
    /**
     * 设置：菜单URL
     * @param url 菜单URL
     */
    var url: String? = null

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    var perms: String? = null

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    var type: Int? = null

    /**
     * 菜单图标
     */
    /**
     * 获取：菜单图标
     * @return String
     */
    /**
     * 设置：菜单图标
     * @param icon 菜单图标
     */
    var icon: String? = null

    /**
     * 排序
     */
    /**
     * 获取：排序
     * @return Integer
     */
    /**
     * 设置：排序
     * @param orderNum 排序
     */
    var orderNum: Int? = null

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
