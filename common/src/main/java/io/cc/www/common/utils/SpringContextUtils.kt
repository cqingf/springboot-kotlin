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

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * Spring Context 工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月29日 下午11:45:51
 */
@Component
class SpringContextUtils : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextUtils.applicationContext = applicationContext
    }

    companion object {
        lateinit var applicationContext: ApplicationContext

        fun getBean(name: String): Any {
            return applicationContext.getBean(name)
        }

        fun <T> getBean(name: String, requiredType: Class<T>): T {
            return applicationContext.getBean(name, requiredType)
        }

        fun containsBean(name: String): Boolean {
            return applicationContext.containsBean(name)
        }

        fun isSingleton(name: String): Boolean {
            return applicationContext.isSingleton(name)
        }

        fun getType(name: String): Class<out Any> {
            return applicationContext.getType(name)
        }
    }

}