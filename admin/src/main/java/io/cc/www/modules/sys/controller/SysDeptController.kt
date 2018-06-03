package io.cc.www.modules.sys.controller

import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.R
import io.cc.www.modules.sys.entity.SysDeptEntity
import io.cc.www.modules.sys.service.SysDeptService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/sys/dept")
class SysDeptController : AbstractController() {
    @Autowired
    private val sysDeptService: SysDeptService? = null

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    fun list(): List<SysDeptEntity> {

        return sysDeptService!!.queryList(HashMap())
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    fun select(): R {
        val deptList = sysDeptService!!.queryList(HashMap())

        //添加一级部门
        if (userId!!.equals(Constant.SUPER_ADMIN)) {
            val root = SysDeptEntity()
            root.deptId = 0L
            root.name = "一级部门"
            root.parentId = -1L
            root.open = true
            deptList as MutableList
            deptList.add(root)
        }

        return R.ok().put("deptList", deptList)!!
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    fun info(): R {
        var deptId: Long = 0
        if (!userId!!.equals(Constant.SUPER_ADMIN)) {
            val deptList = sysDeptService!!.queryList(HashMap())
            var parentId: Long? = null
            for (sysDeptEntity in deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.parentId
                    continue
                }

                if (parentId > sysDeptEntity.parentId!!.toLong()) {
                    parentId = sysDeptEntity.parentId
                }
            }
            deptId = parentId!!
        }

        return R.ok().put("deptId", deptId)!!
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    fun info(@PathVariable("deptId") deptId: Long?): R {
        val dept = sysDeptService!!.selectById(deptId)

        return R.ok().put("dept", dept)!!
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    fun save(@RequestBody dept: SysDeptEntity): R {
        sysDeptService!!.insert(dept)

        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    fun update(@RequestBody dept: SysDeptEntity): R {
        sysDeptService!!.updateById(dept)

        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    fun delete(deptId: Long): R {
        //判断是否有子部门
        val deptList = sysDeptService!!.queryDetpIdList(deptId)
        if (deptList.size > 0) {
            return R.error("请先删除子部门")
        }

        sysDeptService.deleteById(deptId)

        return R.ok()
    }

}