package io.cc.www.modules.sys.controller

import io.cc.www.modules.sys.entity.SysUserEntity
import org.apache.shiro.SecurityUtils
import org.slf4j.LoggerFactory

abstract class AbstractController {
    protected var logger = LoggerFactory.getLogger(javaClass)

    protected val user: SysUserEntity
        get() = SecurityUtils.getSubject().principal as SysUserEntity

    protected val userId: Long?
        get() = user.userId

    protected val deptId: Long?
        get() = user.deptId
}
