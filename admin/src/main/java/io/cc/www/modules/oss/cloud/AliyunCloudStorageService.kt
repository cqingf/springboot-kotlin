package io.cc.www.modules.oss.cloud

import com.aliyun.oss.OSSClient
import io.cc.www.common.exception.RRException
import java.io.ByteArrayInputStream
import java.io.InputStream

class AliyunCloudStorageService(config: CloudStorageConfig) : CloudStorageService() {
    private var client: OSSClient? = null

    init {
        this.config = config

        //初始化
        init()
    }

    private fun init() {
        client = OSSClient(config!!.aliyunEndPoint,
                config!!.aliyunAccessKeyId,
                config!!.aliyunAccessKeySecret)
    }

    override fun upload(data: ByteArray, path: String): String {
        return upload(ByteArrayInputStream(data), path)
    }

    override fun upload(inputStream: InputStream, path: String): String {
        try {
            client!!.putObject(config!!.aliyunBucketName, path, inputStream)
        } catch (e: Exception) {
            throw RRException("上传文件失败，请检查配置信息", e)
        }

        return config!!.aliyunDomain + "/" + path
    }

    override fun uploadSuffix(data: ByteArray, suffix: String): String {
        return upload(data, getPath(config!!.aliyunPrefix!!, suffix))
    }

    override fun uploadSuffix(inputStream: InputStream, suffix: String): String {
        return upload(inputStream, getPath(config!!.aliyunPrefix!!, suffix))
    }
}
