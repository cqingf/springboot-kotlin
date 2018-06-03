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

import io.cc.www.common.exception.RRException
import io.cc.www.modules.sys.entity.SysUserEntity
import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.session.Session
import org.apache.shiro.subject.Subject

/**
 * Shiro工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月12日 上午9:49:19
 */
object ShiroUtils {
    /**
     * 加密算法
     */
    val hashAlgorithmName = "SHA-256"
    /**
     * 循环次数
     */
    val hashIterations = 16

    val session: Session
        get() = SecurityUtils.getSubject().session

    val subject: Subject
        get() = SecurityUtils.getSubject()

    val userEntity: SysUserEntity
        get() = SecurityUtils.getSubject().principal as SysUserEntity

    val userId: Long?
        get() = userEntity.userId

    val isLogin: Boolean
        get() = SecurityUtils.getSubject().principal != null

    fun sha256(password: String, salt: String): String {
        return SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString()
    }

    fun setSessionAttribute(key: Any, value: Any) {
        session.setAttribute(key, value)
    }

    fun getSessionAttribute(key: Any): Any? {
        return session.getAttribute(key)
    }

    fun logout() {
        SecurityUtils.getSubject().logout()
    }

    fun getKaptcha(key: String): String {
        val kaptcha = getSessionAttribute(key) ?: throw RRException("验证码已失效")
        session.removeAttribute(key)
        return kaptcha.toString()
    }

}
