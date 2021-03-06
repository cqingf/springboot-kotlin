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

package io.cc.www.modules.sys.service


import com.baomidou.mybatisplus.service.IService
import io.cc.www.modules.sys.entity.SysMenuEntity


/**
 * 菜单管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:16
 */
interface SysMenuService : IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    fun queryListParentId(parentId: Long?, menuIdList: List<Long>): List<SysMenuEntity>

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    fun queryListParentId(parentId: Long?): List<SysMenuEntity>

    /**
     * 获取不包含按钮的菜单列表
     */
    fun queryNotButtonList(): List<SysMenuEntity>

    /**
     * 获取用户菜单列表
     */
    fun getUserMenuList(userId: Long?): List<SysMenuEntity>

    /**
     * 删除
     */
    fun delete(menuId: Long?)
}
