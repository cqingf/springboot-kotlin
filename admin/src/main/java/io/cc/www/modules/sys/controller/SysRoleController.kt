package io.cc.www.modules.sys.controller

import io.cc.www.common.annotation.SysLog
import io.cc.www.common.utils.R
import io.cc.www.common.validator.ValidatorUtils
import io.cc.www.modules.sys.entity.SysRoleEntity
import io.cc.www.modules.sys.service.SysRoleDeptService
import io.cc.www.modules.sys.service.SysRoleMenuService
import io.cc.www.modules.sys.service.SysRoleService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sys/role")
class SysRoleController : AbstractController() {
    @Autowired
    private val sysRoleService: SysRoleService? = null
    @Autowired
    private val sysRoleMenuService: SysRoleMenuService? = null
    @Autowired
    private val sysRoleDeptService: SysRoleDeptService? = null

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = sysRoleService!!.queryPage(params)

        return R.ok().put("page", page)!!
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    fun select(): R {
        val list = sysRoleService!!.selectList(null)

        return R.ok().put("list", list)!!
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    fun info(@PathVariable("roleId") roleId: Long?): R {
        val role = sysRoleService!!.selectById(roleId)

        //查询角色对应的菜单
        val menuIdList = sysRoleMenuService!!.queryMenuIdList(roleId)
        role.menuIdList = menuIdList

        //查询角色对应的部门
        val deptIdList = sysRoleDeptService!!.queryDeptIdList(arrayOf(roleId!!))
        role.deptIdList = deptIdList

        return R.ok().put("role", role)!!
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    fun save(@RequestBody role: SysRoleEntity): R {
        ValidatorUtils.validateEntity(role)

        sysRoleService!!.save(role)

        return R.ok()
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    fun update(@RequestBody role: SysRoleEntity): R {
        ValidatorUtils.validateEntity(role)

        sysRoleService!!.update(role)

        return R.ok()
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    fun delete(@RequestBody roleIds: Array<Long>): R {
        sysRoleService!!.deleteBatch(roleIds)

        return R.ok()
    }
}
