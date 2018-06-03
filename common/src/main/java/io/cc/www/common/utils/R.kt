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

import java.util.HashMap

/**
 * 返回数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
class R : HashMap<String, Any>() {
    init {
        put("code", 0)
        put("msg", "success")
    }

    override fun put(key: String, value: Any): R? {
        super.put(key, value)
        return this
    }

    companion object {
        private val serialVersionUID = 1L

        fun error(msg: String): R {
            return error(500, msg)
        }

        @JvmOverloads
        fun error(code: Int = 500, msg: String = "未知异常，请联系管理员"): R {
            val r = R()
            r["code"] = code
            r["msg"] = msg
            return r
        }

        fun ok(msg: String): R {
            val r = R()
            r["msg"] = msg
            return r
        }

        fun ok(map: Map<String, Any>): R {
            val r = R()
            r.putAll(map)
            return r
        }

        fun ok(): R {
            return R()
        }
    }
}
