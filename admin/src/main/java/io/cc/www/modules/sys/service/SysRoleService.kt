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
import io.cc.www.modules.sys.entity.SysRoleEntity


/**
 * 角色
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
interface SysRoleService : IService<SysRoleEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils

    fun save(role: SysRoleEntity)

    fun update(role: SysRoleEntity)

    fun deleteBatch(roleIds: Array<Long>)

}
