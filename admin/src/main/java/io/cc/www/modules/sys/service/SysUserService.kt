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
import io.cc.www.common.utils.PageUtils
import io.cc.www.modules.sys.entity.SysUserEntity


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
interface SysUserService : IService<SysUserEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils

    /**
     * 查询用户的所有菜单ID
     */
    fun queryAllMenuId(userId: Long?): List<Long>

    /**
     * 保存用户
     */
    fun save(user: SysUserEntity)

    /**
     * 修改用户
     */
    fun update(user: SysUserEntity)

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    fun updatePassword(userId: Long?, password: String, newPassword: String): Boolean
}
