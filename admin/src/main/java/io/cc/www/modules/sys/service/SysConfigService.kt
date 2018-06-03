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
import io.cc.www.common.utils.PageUtils
import io.cc.www.modules.sys.entity.SysConfigEntity

/**
 * 系统配置信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:49:01
 */
interface SysConfigService : IService<SysConfigEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils

    /**
     * 保存配置信息
     */
    fun save(config: SysConfigEntity)

    /**
     * 更新配置信息
     */
    fun update(config: SysConfigEntity)

    /**
     * 根据key，更新value
     */
    fun updateValueByKey(key: String, value: String)

    /**
     * 删除配置信息
     */
    fun deleteBatch(ids: Array<Long>)

    /**
     * 根据key，获取配置的value值
     *
     * @param key           key
     */
    fun getValue(key: String): String?

    /**
     * 根据key，获取value的Object对象
     * @param key    key
     * @param clazz  Object对象
     */
    fun <T> getConfigObject(key: String, clazz: Class<T>): T

}
