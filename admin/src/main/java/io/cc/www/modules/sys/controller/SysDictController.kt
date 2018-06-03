package io.cc.www.modules.sys.controller

import io.cc.www.common.utils.R
import io.cc.www.common.validator.ValidatorUtils
import io.cc.www.modules.sys.entity.SysDictEntity
import io.cc.www.modules.sys.service.SysDictService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("sys/dict")
class SysDictController {
    @Autowired
    private val sysDictService: SysDictService? = null

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = sysDictService!!.queryPage(params)

        return R.ok().put("page", page)!!
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    fun info(@PathVariable("id") id: Long?): R {
        val dict = sysDictService!!.selectById(id)

        return R.ok().put("dict", dict)!!
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    fun save(@RequestBody dict: SysDictEntity): R {
        //校验类型
        ValidatorUtils.validateEntity(dict)

        sysDictService!!.insert(dict)

        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    fun update(@RequestBody dict: SysDictEntity): R {
        //校验类型
        ValidatorUtils.validateEntity(dict)

        sysDictService!!.updateById(dict)

        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        sysDictService!!.deleteBatchIds(Arrays.asList(*ids))

        return R.ok()
    }

}
