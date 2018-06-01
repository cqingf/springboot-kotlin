package io.cc.www.modules.oss.cloud

import io.cc.www.common.utils.DateUtils
import org.apache.commons.lang.StringUtils
import java.io.InputStream
import java.util.*

abstract class CloudStorageService {
    /** 云存储配置信息  */
    internal var config: CloudStorageConfig? = null

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    fun getPath(prefix: String, suffix: String): String {
        //生成uuid
        val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")
        //文件路径
        var path = DateUtils.format(Date(), "yyyyMMdd") + "/" + uuid

        if (StringUtils.isNotBlank(prefix)) {
            path = "$prefix/$path"
        }

        return path + suffix
    }

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    abstract fun upload(data: ByteArray, path: String): String

    /**
     * 文件上传
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return         返回http地址
     */
    abstract fun uploadSuffix(data: ByteArray, suffix: String): String

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    abstract fun upload(inputStream: InputStream, path: String): String

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return             返回http地址
     */
    abstract fun uploadSuffix(inputStream: InputStream, suffix: String): String

}