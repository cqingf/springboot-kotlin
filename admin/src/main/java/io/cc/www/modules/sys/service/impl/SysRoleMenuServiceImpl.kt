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
import io.cc.www.modules.sys.dao.SysRoleMenuDao
import io.cc.www.modules.sys.entity.SysRoleMenuEntity
import io.cc.www.modules.sys.service.SysRoleMenuService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.ArrayList


/**
 * 角色与菜单对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:44:35
 */
@Service("sysRoleMenuService")
open class SysRoleMenuServiceImpl : ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity>(), SysRoleMenuService {

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun saveOrUpdate(roleId: Long?, menuIdList: List<Long>) {
        //先删除角色与菜单关系
        deleteBatch(arrayOf<Long>(roleId!!))

        if (menuIdList.size == 0) {
            return
        }

        //保存角色与菜单关系
        val list = ArrayList<SysRoleMenuEntity>(menuIdList.size)
        for (menuId in menuIdList) {
            val sysRoleMenuEntity = SysRoleMenuEntity()
            sysRoleMenuEntity.menuId = menuId
            sysRoleMenuEntity.roleId = roleId

            list.add(sysRoleMenuEntity)
        }
        this.insertBatch(list)
    }

    override fun queryMenuIdList(roleId: Long?): List<Long> {
        return baseMapper.queryMenuIdList(roleId)
    }

    override fun deleteBatch(roleIds: Array<Long>): Int {
        return baseMapper.deleteBatch(roleIds)
    }

}
