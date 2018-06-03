package io.cc.www.modules.sys.controller

import io.cc.www.common.utils.R
import io.cc.www.modules.sys.service.SysLogService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/sys/log")
class SysLogController {
    @Autowired
    private val sysLogService: SysLogService? = null

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = sysLogService!!.queryPage(params)

        return R.ok().put("page", page)!!
    }

}
