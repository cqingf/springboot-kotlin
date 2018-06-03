package io.cc.www.modules.sys.controller

import io.cc.www.common.annotation.SysLog
import io.cc.www.common.utils.R
import io.cc.www.common.validator.ValidatorUtils
import io.cc.www.modules.sys.entity.SysConfigEntity
import io.cc.www.modules.sys.service.SysConfigService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sys/config")
class SysConfigController : AbstractController() {
    @Autowired
    private val sysConfigService: SysConfigService? = null

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = sysConfigService!!.queryPage(params)

        return R.ok().put("page", page)!!
    }


    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    fun info(@PathVariable("id") id: Long?): R {
        val config = sysConfigService!!.selectById(id)

        return R.ok().put("config", config)!!
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    fun save(@RequestBody config: SysConfigEntity): R {
        ValidatorUtils.validateEntity(config)

        sysConfigService!!.save(config)

        return R.ok()
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    fun update(@RequestBody config: SysConfigEntity): R {
        ValidatorUtils.validateEntity(config)

        sysConfigService!!.update(config)

        return R.ok()
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        sysConfigService!!.deleteBatch(ids)

        return R.ok()
    }

}
