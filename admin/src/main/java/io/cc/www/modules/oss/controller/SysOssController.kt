package io.cc.www.modules.oss.controller

import com.google.gson.Gson
import io.cc.www.common.exception.RRException
import io.cc.www.common.utils.ConfigConstant
import io.cc.www.common.utils.Constant
import io.cc.www.common.utils.R
import io.cc.www.common.validator.ValidatorUtils
import io.cc.www.common.validator.group.AliyunGroup
import io.cc.www.common.validator.group.QcloudGroup
import io.cc.www.common.validator.group.QiniuGroup
import io.cc.www.modules.oss.cloud.CloudStorageConfig
import io.cc.www.modules.oss.cloud.OSSFactory
import io.cc.www.modules.oss.entity.SysOssEntity
import io.cc.www.modules.oss.service.SysOssService
import io.cc.www.modules.sys.service.SysConfigService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("sys/oss")
class SysOssController {
    @Autowired
    private val sysOssService: SysOssService? = null
    @Autowired
    private val sysConfigService: SysConfigService? = null

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    fun list(@RequestParam params: Map<String, Any>): R? {
        val page = sysOssService!!.queryPage(params)

        return R.ok().put("page", page)
    }


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    fun config(): R? {
        val config = sysConfigService!!.getConfigObject(KEY, CloudStorageConfig::class.java)

        return R.ok().put("config", config)
    }


    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    fun saveConfig(@RequestBody config: CloudStorageConfig): R {
        //校验类型
        ValidatorUtils.validateEntity(config)

        if (config.type == Constant.CloudService.QINIU.value) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup::class.java)
        } else if (config.type == Constant.CloudService.ALIYUN.value) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup::class.java)
        } else if (config.type == Constant.CloudService.QCLOUD.value) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup::class.java)
        }

        sysConfigService!!.updateValueByKey(KEY, Gson().toJson(config))

        return R.ok()
    }


    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    @Throws(Exception::class)
    fun upload(@RequestParam("file") file: MultipartFile): R? {
        if (file.isEmpty) {
            throw RRException("上传文件不能为空")
        }

        //上传文件
        val suffix = file.originalFilename.substring(file.originalFilename.lastIndexOf("."))
        val url = OSSFactory.build()!!.uploadSuffix(file.bytes, suffix)

        //保存文件信息
        val ossEntity = SysOssEntity()
        ossEntity.url = url
        ossEntity.createDate = Date()
        sysOssService!!.insert(ossEntity)

        return R.ok().put("url", url)
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    fun delete(@RequestBody ids: Array<Long>): R {
        sysOssService!!.deleteBatchIds(Arrays.asList(*ids))

        return R.ok()
    }

    companion object {

        private val KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY
    }

}
