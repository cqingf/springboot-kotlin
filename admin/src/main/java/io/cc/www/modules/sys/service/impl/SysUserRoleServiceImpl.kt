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
import io.cc.www.common.utils.MapUtils
import io.cc.www.modules.sys.dao.SysUserRoleDao
import io.cc.www.modules.sys.entity.SysUserRoleEntity
import io.cc.www.modules.sys.service.SysUserRoleService
import org.springframework.stereotype.Service


/**
 * 用户与角色对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
class SysUserRoleServiceImpl : ServiceImpl<SysUserRoleDao, SysUserRoleEntity>(), SysUserRoleService {
    override fun saveOrUpdate(userId: Long?, roleIdList: List<Long>) {
                //先删除用户与角色关系
        this.deleteByMap(MapUtils().put("user_id", userId!!))

        if (roleIdList.isEmpty()) {
            return
        }

        //保存用户与角色关系
        val list = ArrayList<SysUserRoleEntity>(roleIdList.size)
        for (roleId in roleIdList) {
            val sysUserRoleEntity = SysUserRoleEntity()
            sysUserRoleEntity.userId = userId
            sysUserRoleEntity.roleId = roleId

            list.add(sysUserRoleEntity)
        }
        this.insertBatch(list)
    }


    override fun queryRoleIdList(userId: Long?): List<Long> {
        return baseMapper.queryRoleIdList(userId)
    }

    override fun deleteBatch(roleIds: Array<Long>): Int {
        return baseMapper.deleteBatch(roleIds)
    }
}
