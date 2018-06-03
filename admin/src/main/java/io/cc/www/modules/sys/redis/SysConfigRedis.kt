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

package io.cc.www.modules.sys.redis


import io.cc.www.common.utils.RedisKeys
import io.cc.www.common.utils.RedisUtils
import io.cc.www.modules.sys.entity.SysConfigEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * 系统配置Redis
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/7/18 21:08
 */
@Component
class SysConfigRedis {
    @Autowired
    private val redisUtils: RedisUtils? = null

    fun saveOrUpdate(config: SysConfigEntity?) {
        if (config == null) {
            return
        }
        val key = RedisKeys.getSysConfigKey(config.paramKey!!)
        redisUtils!!.set(key, config)
    }

    fun delete(configKey: String) {
        val key = RedisKeys.getSysConfigKey(configKey)
        redisUtils!!.delete(key)
    }

    operator fun get(configKey: String): SysConfigEntity {
        val key = RedisKeys.getSysConfigKey(configKey)
        return redisUtils!![key, SysConfigEntity::class.java]!!
    }
}
