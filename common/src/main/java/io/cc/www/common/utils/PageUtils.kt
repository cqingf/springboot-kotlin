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

import com.baomidou.mybatisplus.plugins.Page

import java.io.Serializable

/**
 * 分页工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月4日 下午12:59:00
 */
class PageUtils : Serializable {
    //总记录数
    var totalCount: Int = 0
    //每页记录数
    var pageSize: Int = 0
    //总页数
    var totalPage: Int = 0
    //当前页数
    var currPage: Int = 0
    //列表数据
    var list: List<*>? = null

    /**
     * 分页
     * @param list        列表数据
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param currPage    当前页数
     */
    constructor(list: List<*>, totalCount: Int, pageSize: Int, currPage: Int) {
        this.list = list
        this.totalCount = totalCount
        this.pageSize = pageSize
        this.currPage = currPage
        this.totalPage = Math.ceil(totalCount.toDouble() / pageSize).toInt()
    }

    /**
     * 分页
     */
    constructor(page: Page<*>) {
        this.list = page.records
        this.totalCount = page.total
        this.pageSize = page.size
        this.currPage = page.current
        this.totalPage = page.pages
    }

    companion object {
        private const val serialVersionUID = 1L
    }

}
