package io.cc.www.modules.oss.cloud

import io.cc.www.common.utils.ConfigConstant
import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.SpringContextUtils
import io.cc.www.modules.sys.service.SysConfigService

object OSSFactory {
    private var sysConfigService: SysConfigService? = null

    init {
        OSSFactory.sysConfigService = SpringContextUtils.getBean("sysConfigService") as SysConfigService
    }

    fun build(): CloudStorageService? {
        //获取云存储配置信息
        val config = sysConfigService!!.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig::class.java)

        if (config.type == Constant.CloudService.QINIU.value) {
            return QiniuCloudStorageService(config)
        } else if (config.type == Constant.CloudService.ALIYUN.value) {
            return AliyunCloudStorageService(config)
        } else if (config.type == Constant.CloudService.QCLOUD.value) {
            return QcloudCloudStorageService(config)
        }

        return null
    }

}
