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
import io.cc.www.modules.sys.entity.SysDeptEntity

/**
 * 部门管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
interface SysDeptService : IService<SysDeptEntity> {

    fun queryList(map: Map<String, Any>): List<SysDeptEntity>

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    fun queryDetpIdList(parentId: Long?): List<Long>

    /**
     * 获取子部门ID，用于数据过滤
     */
    fun getSubDeptIdList(deptId: Long?): List<Long>

}
