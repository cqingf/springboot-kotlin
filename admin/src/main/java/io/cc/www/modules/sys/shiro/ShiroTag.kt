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

import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.springframework.stereotype.Component

/**
 * Shiro权限标签
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月3日 下午11:32:47
 */
@Component
class ShiroTag {

    /**
     * 是否拥有该权限
     *
     * @param permission 权限标识
     * @return true：是     false：否
     */
    fun hasPermission(permission: String): Boolean {
        val subject = SecurityUtils.getSubject()
        return subject != null && subject.isPermitted(permission)
    }

}
