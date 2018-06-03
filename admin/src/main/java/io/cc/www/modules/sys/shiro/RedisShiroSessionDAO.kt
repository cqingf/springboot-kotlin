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

import io.cc.www.common.utils.RedisKeys
import org.apache.shiro.session.Session
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

import java.io.Serializable
import java.util.concurrent.TimeUnit

/**
 * shiro session dao
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/9/27 21:35
 */
@Component
class RedisShiroSessionDAO : EnterpriseCacheSessionDAO() {
    @Autowired
    private val redisTemplate: RedisTemplate<Any, Any>? = null

    //创建session
    override fun doCreate(session: Session): Serializable {
        val sessionId = super.doCreate(session)
        val key = RedisKeys.getShiroSessionKey(sessionId.toString())
        setShiroSession(key, session)
        return sessionId
    }

    //获取session
    override fun doReadSession(sessionId: Serializable?): Session {
        var session: Session? = super.doReadSession(sessionId)
        if (session == null) {
            val key = RedisKeys.getShiroSessionKey(sessionId!!.toString())
            session = getShiroSession(key)
        }
        return session
    }

    //更新session
    override fun doUpdate(session: Session?) {
        super.doUpdate(session)
        val key = RedisKeys.getShiroSessionKey(session!!.id.toString())
        setShiroSession(key, session)
    }

    //删除session
    override fun doDelete(session: Session?) {
        super.doDelete(session)
        val key = RedisKeys.getShiroSessionKey(session!!.id.toString())
        redisTemplate!!.delete(key)
    }

    private fun getShiroSession(key: String): Session {
        return redisTemplate!!.opsForValue().get(key) as Session
    }

    private fun setShiroSession(key: String, session: Session) {
        redisTemplate!!.opsForValue().set(key, session)
        //60分钟过期
        redisTemplate.expire(key, 60, TimeUnit.MINUTES)
    }

}
