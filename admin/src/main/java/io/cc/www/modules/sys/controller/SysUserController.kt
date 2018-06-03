package io.cc.www.modules.sys.controller

import io.cc.www.common.annotation.SysLog
import io.cc.www.common.utils.R
import io.cc.www.common.validator.Assert
import io.cc.www.common.validator.ValidatorUtils
import io.cc.www.common.validator.group.AddGroup
import io.cc.www.common.validator.group.UpdateGroup
import io.cc.www.modules.sys.entity.SysUserEntity
import io.cc.www.modules.sys.service.SysUserRoleService
import io.cc.www.modules.sys.service.SysUserService
import io.cc.www.modules.sys.shiro.ShiroUtils
import org.apache.commons.lang.ArrayUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/sys/user")
class SysUserController : AbstractController() {
    @Autowired
    private val sysUserService: SysUserService? = null
    @Autowired
    private val sysUserRoleService: SysUserRoleService? = null

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = sysUserService!!.queryPage(params)

        return R.ok().put("page", page)!!
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    fun info(): R {
        return R.ok().put("user", user)!!
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    fun password(password: String, newPassword: String): R {
        var password = password
        var newPassword = newPassword
        Assert.isBlank(newPassword, "新密码不为能空")

        //原密码
        password = ShiroUtils.sha256(password, user.salt!!)
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, user.salt!!)

        //更新密码
        val flag = sysUserService!!.updatePassword(userId, password, newPassword)
        return if (!flag) {
            R.error("原密码不正确")
        } else R.ok()

    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    fun info(@PathVariable("userId") userId: Long?): R {
        val user = sysUserService!!.selectById(userId)

        //获取用户所属的角色列表
        val roleIdList = sysUserRoleService!!.queryRoleIdList(userId)
        user.roleIdList = roleIdList

        return R.ok().put("user", user)!!
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    fun save(@RequestBody user: SysUserEntity): R {
        ValidatorUtils.validateEntity(user, AddGroup::class.java)

        sysUserService!!.save(user)

        return R.ok()
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    fun update(@RequestBody user: SysUserEntity): R {
        ValidatorUtils.validateEntity(user, UpdateGroup::class.java)

        sysUserService!!.update(user)

        return R.ok()
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    fun delete(@RequestBody userIds: Array<Long>): R {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除")
        }

        if (ArrayUtils.contains(userIds, userId)) {
            return R.error("当前用户不能删除")
        }

        sysUserService!!.deleteBatchIds(Arrays.asList(*userIds))

        return R.ok()
    }
}
