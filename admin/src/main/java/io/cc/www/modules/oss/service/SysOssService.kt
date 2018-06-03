package io.cc.www.modules.oss.service

import com.baomidou.mybatisplus.service.IService
import io.cc.www.common.utils.PageUtils
import io.cc.www.modules.oss.entity.SysOssEntity


interface SysOssService : IService<SysOssEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils
}
