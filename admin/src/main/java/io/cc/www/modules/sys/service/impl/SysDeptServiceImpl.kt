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
import io.cc.www.modules.sys.dao.SysDeptDao
import io.cc.www.modules.sys.entity.SysDeptEntity
import io.cc.www.modules.sys.service.SysDeptService
import org.springframework.stereotype.Service

import java.util.ArrayList


@Service("sysDeptService")
class SysDeptServiceImpl : ServiceImpl<SysDeptDao, SysDeptEntity>(), SysDeptService {

    @DataFilter(subDept = true, user = false)
    override fun queryList(params: Map<String, Any>): List<SysDeptEntity> {
        val deptList = this.selectList(EntityWrapper<SysDeptEntity>()
                .addFilterIfNeed(params[Constant.SQL_FILTER] != null, params[Constant.SQL_FILTER] as String))

        for (sysDeptEntity in deptList) {
            val parentDeptEntity = this.selectById(sysDeptEntity.parentId)
            if (parentDeptEntity != null) {
                sysDeptEntity.parentName = parentDeptEntity.name
            }
        }
        return deptList
    }

    override fun queryDetpIdList(parentId: Long?): List<Long> {
        return baseMapper.queryDetpIdList(parentId)
    }

    override fun getSubDeptIdList(deptId: Long?): List<Long> {
        //部门及子部门ID列表
        val deptIdList = ArrayList<Long>()

        //获取子部门ID
        val subIdList = queryDetpIdList(deptId)
        getDeptTreeList(subIdList, deptIdList)

        return deptIdList
    }

    /**
     * 递归
     */
    private fun getDeptTreeList(subIdList: List<Long>, deptIdList: MutableList<Long>) {
        for (deptId in subIdList) {
            val list = queryDetpIdList(deptId)
            if (list.size > 0) {
                getDeptTreeList(list, deptIdList)
            }

            deptIdList.add(deptId)
        }
    }
}
