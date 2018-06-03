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
import io.cc.www.modules.sys.dao.SysRoleDeptDao
import io.cc.www.modules.sys.entity.SysRoleDeptEntity
import io.cc.www.modules.sys.service.SysRoleDeptService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.ArrayList


/**
 * 角色与部门对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:42:30
 */
@Service("sysRoleDeptService")
open class SysRoleDeptServiceImpl : ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity>(), SysRoleDeptService {

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun saveOrUpdate(roleId: Long?, deptIdList: List<Long>) {
        //先删除角色与部门关系
        deleteBatch(arrayOf<Long>(roleId!!))

        if (deptIdList.size == 0) {
            return
        }

        //保存角色与菜单关系
        val list = ArrayList<SysRoleDeptEntity>(deptIdList.size)
        for (deptId in deptIdList) {
            val sysRoleDeptEntity = SysRoleDeptEntity()
            sysRoleDeptEntity.deptId = deptId
            sysRoleDeptEntity.roleId = roleId

            list.add(sysRoleDeptEntity)
        }
        this.insertBatch(list)
    }

    override fun queryDeptIdList(roleIds: Array<Long>): List<Long> {
        return baseMapper.queryDeptIdList(roleIds)
    }

    override fun deleteBatch(roleIds: Array<Long>): Int {
        return baseMapper.deleteBatch(roleIds)
    }
}
