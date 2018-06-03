package io.cc.www.modules.oss.service.impl

import com.baomidou.mybatisplus.service.impl.ServiceImpl
import io.cc.www.common.utils.PageUtils
import io.cc.www.common.utils.Query
import io.cc.www.modules.oss.dao.SysOssDao
import io.cc.www.modules.oss.entity.SysOssEntity
import io.cc.www.modules.oss.service.SysOssService
import org.springframework.stereotype.Service


@Service("sysOssService")
class SysOssServiceImpl : ServiceImpl<SysOssDao, SysOssEntity>(), SysOssService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.selectPage(
                Query<SysOssEntity>(params).page
        )

        return PageUtils(page)
    }

}
