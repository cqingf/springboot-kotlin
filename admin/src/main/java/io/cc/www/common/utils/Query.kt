package io.cc.www.common.utils

import com.baomidou.mybatisplus.plugins.Page
import io.cc.www.common.xss.SQLFilter
import org.apache.commons.lang.StringUtils
import java.util.LinkedHashMap

class Query<T>(params: Map<String, Any>) : LinkedHashMap<String, Any>() {
    /**
     * mybatis-plus分页参数
     */
    val page: Page<T>
    /**
     * 当前页码
     */
    var currPage = 1
        private set
    /**
     * 每页条数
     */
    var limit = 10
        private set

    init {
        this.putAll(params)

        //分页参数
        if (params["page"] != null) {
            currPage = Integer.parseInt(params["page"] as String)
        }
        if (params["limit"] != null) {
            limit = Integer.parseInt(params["limit"] as String)
        }

        this["offset"] = (currPage - 1) * limit
        this["page"] = currPage
        this["limit"] = limit

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        val sidx = SQLFilter.sqlInject(params["sidx"] as String)
        val order = SQLFilter.sqlInject(params["order"] as String)
        this["sidx"] = sidx
        this["order"] = order

        //mybatis-plus分页
        this.page = Page(currPage, limit)

        //排序
        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
            this.page.orderByField = sidx
            this.page.isAsc = "ASC".equals(order!!, ignoreCase = true)
        }

    }

    companion object {
        private val serialVersionUID = 1L
    }
}
