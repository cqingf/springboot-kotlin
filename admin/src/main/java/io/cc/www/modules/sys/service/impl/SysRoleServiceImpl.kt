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

import com.baomidou.mybatisplus.mapper.EntityWrapper
import com.baomidou.mybatisplus.plugins.Page
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import io.cc.www.common.annotation.DataFilter
import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.PageUtils
import io.cc.www.common.utils.Query
import io.cc.www.modules.sys.dao.SysRoleDao
import io.cc.www.modules.sys.entity.SysDeptEntity
import io.cc.www.modules.sys.entity.SysRoleEntity
import io.cc.www.modules.sys.service.*
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Arrays
import java.util.Date


/**
 * 角色
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
open class SysRoleServiceImpl : ServiceImpl<SysRoleDao, SysRoleEntity>(), SysRoleService {
    @Autowired
    private val sysRoleMenuService: SysRoleMenuService? = null
    @Autowired
    private val sysRoleDeptService: SysRoleDeptService? = null
    @Autowired
    private val sysUserRoleService: SysUserRoleService? = null
    @Autowired
    private val sysDeptService: SysDeptService? = null

    @DataFilter(subDept = true, user = false)
    override fun queryPage(params: Map<String, Any>): PageUtils {
        val roleName = params["roleName"] as String

        val page = this.selectPage(
                Query<SysRoleEntity>(params).page,
                EntityWrapper<SysRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                        .addFilterIfNeed(params[Constant.SQL_FILTER] != null, params[Constant.SQL_FILTER] as String)
        )

        for (sysRoleEntity in page.records) {
            val sysDeptEntity = sysDeptService!!.selectById(sysRoleEntity.deptId)
            if (sysDeptEntity != null) {
                sysRoleEntity.deptName = sysDeptEntity.name
            }
        }

        return PageUtils(page)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    override fun save(role: SysRoleEntity) {
        role.createTime = Date()
        this.insert(role)

        //保存角色与菜单关系
        sysRoleMenuService!!.saveOrUpdate(role.roleId, role.menuIdList!!)

        //保存角色与部门关系
        sysRoleDeptService!!.saveOrUpdate(role.roleId, role.deptIdList!!)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun update(role: SysRoleEntity) {
        this.updateAllColumnById(role)

        //更新角色与菜单关系
        sysRoleMenuService!!.saveOrUpdate(role.roleId, role.menuIdList!!)

        //保存角色与部门关系
        sysRoleDeptService!!.saveOrUpdate(role.roleId, role.deptIdList!!)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun deleteBatch(roleIds: Array<Long>) {
        //删除角色
        this.deleteBatchIds(Arrays.asList(*roleIds))

        //删除角色与菜单关联
        sysRoleMenuService!!.deleteBatch(roleIds)

        //删除角色与部门关联
        sysRoleDeptService!!.deleteBatch(roleIds)

        //删除角色与用户关联
        sysUserRoleService!!.deleteBatch(roleIds)
    }


}
