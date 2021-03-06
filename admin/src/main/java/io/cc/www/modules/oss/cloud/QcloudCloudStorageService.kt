package io.cc.www.modules.oss.cloud

import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.request.UploadFileRequest
import com.qcloud.cos.sign.Credentials
import io.cc.www.common.exception.RRException
import net.sf.json.JSONObject
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

class QcloudCloudStorageService(config: CloudStorageConfig) : CloudStorageService() {
    private var client: COSClient? = null

    init {
        this.config = config

        //初始化
        init()
    }

    private fun init() {
        val credentials = Credentials(config!!.qcloudAppId!!.toLong(), config!!.qcloudSecretId,
                config!!.qcloudSecretKey)

        //初始化客户端配置
        val clientConfig = ClientConfig()
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(config!!.qcloudRegion)

        client = COSClient(clientConfig, credentials)
    }

    override fun upload(data: ByteArray, path: String): String {
        var path = path
        //腾讯云必需要以"/"开头
        if (!path.startsWith("/")) {
            path = "/$path"
        }

        //上传到腾讯云
        val request = UploadFileRequest(config!!.qcloudBucketName, path, data)
        val response = client!!.uploadFile(request)

        val jsonObject = JSONObject.fromObject(response)
        if (jsonObject.getInt("code") != 0) {
            throw RRException("文件上传失败，" + jsonObject.getString("message"))
        }

        return config!!.qcloudDomain + path
    }

    override fun upload(inputStream: InputStream, path: String): String {
        try {
            val data = IOUtils.toByteArray(inputStream)
            return this.upload(data, path)
        } catch (e: IOException) {
            throw RRException("上传文件失败", e)
        }

    }

    override fun uploadSuffix(data: ByteArray, suffix: String): String {
        return upload(data, getPath(config!!.qcloudPrefix!!, suffix))
    }

    override fun uploadSuffix(inputStream: InputStream, suffix: String): String {
        return upload(inputStream, getPath(config!!.qcloudPrefix!!, suffix))
    }
}
