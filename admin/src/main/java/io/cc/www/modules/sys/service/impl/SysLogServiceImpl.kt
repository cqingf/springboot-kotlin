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
import io.cc.www.common.utils.PageUtils
import io.cc.www.common.utils.Query
import io.cc.www.modules.sys.dao.SysLogDao
import io.cc.www.modules.sys.entity.SysLogEntity
import io.cc.www.modules.sys.service.SysLogService
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service


@Service("sysLogService")
class SysLogServiceImpl : ServiceImpl<SysLogDao, SysLogEntity>(), SysLogService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val key = params["key"] as String

        val page = this.selectPage(
                Query<SysLogEntity>(params).page,
                EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key), "username", key)
        )

        return PageUtils(page)
    }
}
