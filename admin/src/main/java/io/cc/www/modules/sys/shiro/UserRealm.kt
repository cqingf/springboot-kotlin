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

package io.cc.www.modules.sys.shiro


import io.cc.www.common.utils.Constant
import io.cc.www.modules.sys.dao.SysMenuDao
import io.cc.www.modules.sys.dao.SysUserDao
import io.cc.www.modules.sys.entity.SysUserEntity
import org.apache.commons.lang.StringUtils
import org.apache.shiro.authc.*
import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.util.ByteSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * 认证
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 上午11:55:49
 */
@Component
class UserRealm : AuthorizingRealm() {
    @Autowired
    private val sysUserDao: SysUserDao? = null
    @Autowired
    private val sysMenuDao: SysMenuDao? = null

    /**
     * 授权(验证权限时调用)
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo {
        val user = principals.primaryPrincipal as SysUserEntity
        val userId = user.userId

        val permsList: MutableList<String>

        //系统管理员，拥有最高权限
        if (userId?.equals(Constant.SUPER_ADMIN) == true) {
            val menuList = sysMenuDao!!.selectList(null)
            permsList = ArrayList(menuList.size)
            for (menu in menuList) {
                permsList.add(menu.perms!!)
            }
        } else {
            permsList = sysUserDao!!.queryAllPerms(userId) as MutableList<String>
        }

        //用户权限列表
        val permsSet = HashSet<String>()
        for (perms in permsList) {
            if (StringUtils.isBlank(perms)) {
                continue
            }
            permsSet.addAll(Arrays.asList(*perms.trim { it <= ' ' }.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
        }

        val info = SimpleAuthorizationInfo()
        info.stringPermissions = permsSet
        return info
    }

    /**
     * 认证(登录时调用)
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(
            authcToken: AuthenticationToken): AuthenticationInfo {
        val token = authcToken as UsernamePasswordToken

        //查询用户信息
        var user: SysUserEntity? = SysUserEntity()
        user!!.username = token.username
        user = sysUserDao!!.selectOne(user)

        //账号不存在
        if (user == null) {
            throw UnknownAccountException("账号或密码不正确")
        }

        //账号锁定
        if (user.status == 0) {
            throw LockedAccountException("账号已被锁定,请联系管理员")
        }

        return SimpleAuthenticationInfo(user, user.password, ByteSource.Util.bytes(user.salt!!), name)
    }

    override fun setCredentialsMatcher(credentialsMatcher: CredentialsMatcher) {
        val shaCredentialsMatcher = HashedCredentialsMatcher()
        shaCredentialsMatcher.hashAlgorithmName = ShiroUtils.hashAlgorithmName
        shaCredentialsMatcher.hashIterations = ShiroUtils.hashIterations
        super.setCredentialsMatcher(shaCredentialsMatcher)
    }
}
