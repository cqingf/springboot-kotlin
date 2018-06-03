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

package io.cc.www.modules.sys.dao


import com.baomidou.mybatisplus.mapper.BaseMapper
import io.cc.www.modules.sys.entity.SysConfigEntity
import org.apache.ibatis.annotations.Param

/**
 * 系统配置信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:46:16
 */
interface SysConfigDao : BaseMapper<SysConfigEntity> {

    /**
     * 根据key，查询value
     */
    fun queryByKey(paramKey: String): SysConfigEntity

    /**
     * 根据key，更新value
     */
    fun updateValueByKey(@Param("paramKey") paramKey: String, @Param("paramValue") paramValue: String): Int

}
