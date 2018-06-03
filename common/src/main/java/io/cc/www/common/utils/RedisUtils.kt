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

package io.cc.www.common.utils

import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.*
import org.springframework.stereotype.Component

import javax.annotation.Resource
import java.util.concurrent.TimeUnit

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
class RedisUtils {
    @Autowired
    private val redisTemplate: RedisTemplate<Any, Any>? = null
    @Resource(name = "redisTemplate")
    private val valueOperations: ValueOperations<String, String>? = null
    @Resource(name = "redisTemplate")
    private val hashOperations: HashOperations<String, String, Any>? = null
    @Resource(name = "redisTemplate")
    private val listOperations: ListOperations<String, Any>? = null
    @Resource(name = "redisTemplate")
    private val setOperations: SetOperations<String, Any>? = null
    @Resource(name = "redisTemplate")
    private val zSetOperations: ZSetOperations<String, Any>? = null

    @JvmOverloads
    fun set(key: String, value: Any, expire: Long = DEFAULT_EXPIRE) {
        valueOperations!!.set(key, toJson(value))
        if (expire != NOT_EXPIRE) {
            redisTemplate!!.expire(key, expire, TimeUnit.SECONDS)
        }
    }

    operator fun <T> get(key: String, clazz: Class<T>, expire: Long): T? {
        val value = valueOperations!!.get(key)
        if (expire != NOT_EXPIRE) {
            redisTemplate!!.expire(key, expire, TimeUnit.SECONDS)
        }
        return if (value == null) null else fromJson(value, clazz)
    }

    operator fun <T> get(key: String, clazz: Class<T>): T? {
        return get(key, clazz, NOT_EXPIRE)
    }

    @JvmOverloads
    operator fun get(key: String, expire: Long = NOT_EXPIRE): String {
        val value = valueOperations!!.get(key)
        if (expire != NOT_EXPIRE) {
            redisTemplate!!.expire(key, expire, TimeUnit.SECONDS)
        }
        return value
    }

    fun delete(key: String) {
        redisTemplate!!.delete(key)
    }

    /**
     * Object转成JSON数据
     */
    private fun toJson(`object`: Any): String {
        return if (`object` is Int || `object` is Long || `object` is Float ||
                `object` is Double || `object` is Boolean || `object` is String) {
            `object`.toString()
        } else JSON.toJSONString(`object`)
    }

    /**
     * JSON数据，转成Object
     */
    private fun <T> fromJson(json: String, clazz: Class<T>): T {
        return JSON.parseObject(json, clazz)
    }

    companion object {
        /**  默认过期时长，单位：秒  */
        val DEFAULT_EXPIRE = (60 * 60 * 24).toLong()
        /**  不设置过期时长  */
        val NOT_EXPIRE: Long = -1
    }
}
