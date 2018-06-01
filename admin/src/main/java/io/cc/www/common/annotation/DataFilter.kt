package io.cc.www.common.annotation

/**
 * 数据过滤
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0 2017-09-17
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class DataFilter(
        /**  表的别名  */
        val tableAlias: String = "",
        /**  true：没有本部门数据权限，也能查询本人数据  */
        val user: Boolean = true,
        /**  true：拥有子部门数据权限  */
        val subDept: Boolean = false,
        /**  部门ID  */
        val deptId: String = "dept_id",
        /**  用户ID  */
        val userId: String = "user_id"
)



