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
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import io.cc.www.common.annotation.DataFilter
import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.PageUtils
import io.cc.www.common.utils.Query
import io.cc.www.modules.sys.dao.SysUserDao
import io.cc.www.modules.sys.entity.SysUserEntity
import io.cc.www.modules.sys.service.SysDeptService
import io.cc.www.modules.sys.service.SysUserRoleService
import io.cc.www.modules.sys.service.SysUserService
import io.cc.www.modules.sys.shiro.ShiroUtils
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
open class SysUserServiceImpl : ServiceImpl<SysUserDao, SysUserEntity>(), SysUserService {
    @Autowired
    private val sysUserRoleService: SysUserRoleService? = null
    @Autowired
    private val sysDeptService: SysDeptService? = null

    override fun queryAllMenuId(userId: Long?): List<Long> {
        return baseMapper.queryAllMenuId(userId)
    }

    @DataFilter(subDept = true, user = false)
    override fun queryPage(params: Map<String, Any>): PageUtils {
        val username = params["username"] as String

        val page = this.selectPage(
                Query<SysUserEntity>(params).page,
                EntityWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .addFilterIfNeed(params[Constant.SQL_FILTER] != null, params[Constant.SQL_FILTER] as String)
        )

        for (sysUserEntity in page.records) {
            val sysDeptEntity = sysDeptService!!.selectById(sysUserEntity.deptId)
            sysUserEntity.deptName = sysDeptEntity.name
        }

        return PageUtils(page)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun save(user: SysUserEntity) {
        user.createTime = Date()
        //sha256加密
        val salt = RandomStringUtils.randomAlphanumeric(20)
        user.salt = salt
        user.password = ShiroUtils.sha256(user.password!!, user.salt!!)
        this.insert(user)

        //保存用户与角色关系
        sysUserRoleService!!.saveOrUpdate(user.userId, user.roleIdList!!)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    override fun update(user: SysUserEntity) {
        if (StringUtils.isBlank(user.password)) {
            user.password = null
        } else {
            user.password = ShiroUtils.sha256(user.password!!, user.salt!!)
        }
        this.updateById(user)

        //保存用户与角色关系
        sysUserRoleService!!.saveOrUpdate(user.userId, user.roleIdList!!)
    }


    override fun updatePassword(userId: Long?, password: String, newPassword: String): Boolean {
        val userEntity = SysUserEntity()
        userEntity.password = newPassword
        return this.update(userEntity,
                EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password))
    }

}
