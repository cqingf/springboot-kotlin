package io.cc.www.modules.sys.controller

import io.cc.www.common.annotation.SysLog
import io.cc.www.common.exception.RRException
import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.R
import io.cc.www.modules.sys.entity.SysMenuEntity
import io.cc.www.modules.sys.service.SysMenuService
import org.apache.commons.lang.StringUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sys/menu")
class SysMenuController : AbstractController() {
    @Autowired
    private val sysMenuService: SysMenuService? = null

    /**
     * 导航菜单
     */
    @RequestMapping("/nav")
    fun nav(): R {
        val menuList = sysMenuService!!.getUserMenuList(userId)
        return R.ok().put("menuList", menuList)!!
    }

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    fun list(): List<SysMenuEntity> {
        val menuList = sysMenuService!!.selectList(null)
        for (sysMenuEntity in menuList) {
            val parentMenuEntity = sysMenuService.selectById(sysMenuEntity.parentId)
            if (parentMenuEntity != null) {
                sysMenuEntity.parentName = parentMenuEntity.name
            }
        }

        return menuList
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    fun select(): R {
        //查询列表数据
        val menuList = sysMenuService!!.queryNotButtonList()

        //添加顶级菜单
        val root = SysMenuEntity()
        root.menuId = 0L
        root.name = "一级菜单"
        root.parentId = -1L
        root.open = true
        menuList as MutableList
        menuList.add(root)

        return R.ok().put("menuList", menuList)!!
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    fun info(@PathVariable("menuId") menuId: Long?): R {
        val menu = sysMenuService!!.selectById(menuId)
        return R.ok().put("menu", menu)!!
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    fun save(@RequestBody menu: SysMenuEntity): R {
        //数据校验
        verifyForm(menu)

        sysMenuService!!.insert(menu)

        return R.ok()
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    fun update(@RequestBody menu: SysMenuEntity): R {
        //数据校验
        verifyForm(menu)

        sysMenuService!!.updateById(menu)

        return R.ok()
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    fun delete(menuId: Long): R {
        if (menuId <= 31) {
            return R.error("系统菜单，不能删除")
        }

        //判断是否有子菜单或按钮
        val menuList = sysMenuService!!.queryListParentId(menuId)
        if (menuList.size > 0) {
            return R.error("请先删除子菜单或按钮")
        }

        sysMenuService.delete(menuId)

        return R.ok()
    }

    /**
     * 验证参数是否正确
     */
    private fun verifyForm(menu: SysMenuEntity) {
        if (StringUtils.isBlank(menu.name)) {
            throw RRException("菜单名称不能为空")
        }

        if (menu.parentId == null) {
            throw RRException("上级菜单不能为空")
        }

        //菜单
        if (menu.type == Constant.MenuType.MENU.value) {
            if (StringUtils.isBlank(menu.url)) {
                throw RRException("菜单URL不能为空")
            }
        }

        //上级菜单类型
        var parentType = Constant.MenuType.CATALOG.value
        if (!menu.parentId!!.equals(0)) {
            val parentMenu = sysMenuService!!.selectById(menu.parentId)
            parentType = parentMenu.type!!
        }

        //目录、菜单
        if (menu.type == Constant.MenuType.CATALOG.value || menu.type == Constant.MenuType.MENU.value) {
            if (parentType != Constant.MenuType.CATALOG.value) {
                throw RRException("上级菜单只能为目录类型")
            }
            return
        }

        //按钮
        if (menu.type == Constant.MenuType.BUTTON.value) {
            if (parentType != Constant.MenuType.MENU.value) {
                throw RRException("上级菜单只能为菜单类型")
            }
            return
        }
    }
}