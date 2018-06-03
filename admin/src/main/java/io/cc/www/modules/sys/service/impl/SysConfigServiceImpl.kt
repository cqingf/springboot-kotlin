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
import com.google.gson.Gson
import io.cc.www.common.exception.RRException
import io.cc.www.common.utils.PageUtils
import io.cc.www.common.utils.Query
import io.cc.www.modules.sys.dao.SysConfigDao
import io.cc.www.modules.sys.entity.SysConfigEntity
import io.cc.www.modules.sys.redis.SysConfigRedis
import io.cc.www.modules.sys.service.SysConfigService
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Arrays

@Service("sysConfigService")
open class SysConfigServiceImpl : ServiceImpl<SysConfigDao, SysConfigEntity>(), SysConfigService {
    @Autowired
    private val sysConfigRedis: SysConfigRedis? = null

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val paramKey = params["paramKey"] as String

        val page = this.selectPage(
                Query<SysConfigEntity>(params).page,
                EntityWrapper<SysConfigEntity>()
                        .like(StringUtils.isNotBlank(paramKey), "param_key", paramKey)
                        .eq("status", 1)
        )

        return PageUtils(page)
    }

    override fun save(config: SysConfigEntity) {
        this.insert(config)
        sysConfigRedis!!.saveOrUpdate(config)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    override fun update(config: SysConfigEntity) {
        this.updateAllColumnById(config)
        sysConfigRedis!!.saveOrUpdate(config)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    override fun updateValueByKey(key: String, value: String) {
        baseMapper.updateValueByKey(key, value)
        sysConfigRedis!!.delete(key)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    override fun deleteBatch(ids: Array<Long>) {
        for (id in ids) {
            val config = this.selectById(id)
            sysConfigRedis!!.delete(config.paramKey!!)
        }

        this.deleteBatchIds(Arrays.asList(*ids))
    }

    override fun getValue(key: String): String? {
        var config: SysConfigEntity? = sysConfigRedis!![key]
        if (config == null) {
            config = baseMapper.queryByKey(key)
            sysConfigRedis.saveOrUpdate(config)
        }

        return config.paramValue
    }

    override fun <T> getConfigObject(key: String, clazz: Class<T>): T {
        val value = getValue(key)
        if (StringUtils.isNotBlank(value)) {
            return Gson().fromJson(value, clazz)
        }

        try {
            return clazz.newInstance()
        } catch (e: Exception) {
            throw RRException("获取参数失败")
        }

    }
}
