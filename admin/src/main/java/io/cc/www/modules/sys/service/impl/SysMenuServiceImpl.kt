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

package io.cc.www.modules.sys.service.impl


import com.baomidou.mybatisplus.service.impl.ServiceImpl
import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.MapUtils
import io.cc.www.modules.sys.dao.SysMenuDao
import io.cc.www.modules.sys.entity.SysMenuEntity
import io.cc.www.modules.sys.service.SysMenuService
import io.cc.www.modules.sys.service.SysRoleMenuService
import io.cc.www.modules.sys.service.SysUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.ArrayList


@Service("sysMenuService")
class SysMenuServiceImpl : ServiceImpl<SysMenuDao, SysMenuEntity>(), SysMenuService {

    @Autowired
    private val sysUserService: SysUserService? = null
    @Autowired
    private val sysRoleMenuService: SysRoleMenuService? = null

    override fun queryListParentId(parentId: Long?, menuIdList: List<Long>): List<SysMenuEntity> {
        val menuList = queryListParentId(parentId)

        val userMenuList = ArrayList<SysMenuEntity>()
        for (menu in menuList) {
            if (menuIdList.contains(menu.menuId)) {
                userMenuList.add(menu)
            }
        }
        return userMenuList
    }

    override fun queryListParentId(parentId: Long?): List<SysMenuEntity> {
        return baseMapper.queryListParentId(parentId)
    }

    override fun queryNotButtonList(): List<SysMenuEntity> {
        return baseMapper.queryNotButtonList()
    }

    override fun getUserMenuList(userId: Long?): List<SysMenuEntity> {
        //系统管理员，拥有最高权限
        if (userId?.equals(Constant.SUPER_ADMIN) == true) {
            return getAllMenuList(null)
        }

        //用户菜单列表
        val menuIdList = sysUserService!!.queryAllMenuId(userId)
        return getAllMenuList(menuIdList)
    }

    override fun delete(menuId: Long?) {
        //删除菜单
        this.deleteById(menuId)
        //删除菜单与角色关联
        sysRoleMenuService!!.deleteByMap(MapUtils().put("menu_id", menuId!!))
    }

    /**
     * 获取所有菜单列表
     */
    private fun getAllMenuList(menuIdList: List<Long>?): List<SysMenuEntity> {
        //查询根菜单列表
        val menuList = queryListParentId(0L, menuIdList!!)
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList)

        return menuList
    }

    /**
     * 递归
     */
    private fun getMenuTreeList(menuList: List<SysMenuEntity>, menuIdList: List<Long>?): List<SysMenuEntity> {
        val subMenuList = ArrayList<SysMenuEntity>()

        for (entity in menuList) {
            //目录
            if (entity.type == Constant.MenuType.CATALOG.value) {
                entity.list = getMenuTreeList(queryListParentId(entity.menuId, menuIdList!!), menuIdList)
            }
            subMenuList.add(entity)
        }

        return subMenuList
    }
}
