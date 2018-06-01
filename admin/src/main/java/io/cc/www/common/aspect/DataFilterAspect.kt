package io.cc.www.common.aspect

import io.cc.www.common.annotation.DataFilter
import io.cc.www.common.exception.RRException
import io.cc.www.common.utils.Constant
import io.cc.www.modules.sys.entity.SysUserEntity
import io.cc.www.modules.sys.service.SysDeptService
import io.cc.www.modules.sys.service.SysRoleDeptService
import io.cc.www.modules.sys.service.SysUserRoleService
import io.cc.www.modules.sys.shiro.ShiroUtils
import org.apache.commons.lang.StringUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Aspect
@Component
class DataFilterAspect {

    @Autowired
    private val sysDeptService: SysDeptService? = null
    @Autowired
    private val sysUserRoleService: SysUserRoleService? = null
    @Autowired
    private val sysRoleDeptService: SysRoleDeptService? = null

    @Pointcut("@annotation(io.cc.www.common.annotation.DataFilter)")
    fun dataFilterCut() {

    }

    @Before("dataFilterCut()")
    @Throws(Throwable::class)
    fun dataFilter(point: JoinPoint) {
        val params = point.args[0]
        if (params != null && params is Map<*, *>) {
            val user = ShiroUtils.getUserEntity()

            //如果不是超级管理员，则进行数据过滤
            if (user.userId != (Constant.SUPER_ADMIN).toLong()) {

                val hashMap = HashMap(params)

                hashMap[Constant.SQL_FILTER] = getSQLFilter(user, point)
            }

            return
        }

        throw RRException("数据权限接口，只能是Map类型参数，且不能为NULL")
    }

    /**
     * 获取数据过滤的SQL
     */
    private fun getSQLFilter(user: SysUserEntity, point: JoinPoint): String {
        val signature = point.signature as MethodSignature
        val dataFilter = signature.method.getAnnotation(DataFilter::class.java)
        //获取表的别名
        var tableAlias = dataFilter.tableAlias
        if (StringUtils.isNotBlank(tableAlias)) {
            tableAlias += "."
        }

        //部门ID列表
        val deptIdList = HashSet<Long>()

        //用户角色对应的部门ID列表
        val roleIdList = sysUserRoleService!!.queryRoleIdList(user.userId)
        if (roleIdList.size > 0) {
            val userDeptIdList = sysRoleDeptService!!.queryDeptIdList(roleIdList.toTypedArray())
            deptIdList.addAll(userDeptIdList)
        }

        //用户子部门ID列表
        if (dataFilter.subDept) {
            val subDeptIdList = sysDeptService!!.getSubDeptIdList(user.deptId)
            deptIdList.addAll(subDeptIdList)
        }

        val sqlFilter = StringBuilder()
        sqlFilter.append(" (")

        if (deptIdList.size > 0) {
            sqlFilter.append(tableAlias).append(dataFilter.deptId).append(" in(").append(StringUtils.join(deptIdList, ",")).append(")")
        }

        //没有本部门数据权限，也能查询本人数据
        if (dataFilter.user) {
            if (deptIdList.size > 0) {
                sqlFilter.append(" or ")
            }
            sqlFilter.append(tableAlias).append(dataFilter.userId).append("=").append(user.userId)
        }

        sqlFilter.append(")")

        return sqlFilter.toString()
    }


}
