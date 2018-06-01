package io.cc.www.modules.oss.cloud

import io.cc.www.common.validator.group.AliyunGroup
import io.cc.www.common.validator.group.QcloudGroup
import io.cc.www.common.validator.group.QiniuGroup
import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CloudStorageConfig : Serializable {

    //类型 1：七牛  2：阿里云  3：腾讯云
    @Range(min = 1, max = 3, message = "类型错误")
    var type: Int? = null

    //七牛绑定的域名
    @NotBlank(message = "七牛绑定的域名不能为空", groups = arrayOf(QiniuGroup::class))
    @URL(message = "七牛绑定的域名格式不正确", groups = arrayOf(QiniuGroup::class))
    var qiniuDomain: String? = null
    //七牛路径前缀
    var qiniuPrefix: String? = null
    //七牛ACCESS_KEY
    @NotBlank(message = "七牛AccessKey不能为空", groups = arrayOf(QiniuGroup::class))
    var qiniuAccessKey: String? = null
    //七牛SECRET_KEY
    @NotBlank(message = "七牛SecretKey不能为空", groups = arrayOf(QiniuGroup::class))
    var qiniuSecretKey: String? = null
    //七牛存储空间名
    @NotBlank(message = "七牛空间名不能为空", groups = arrayOf(QiniuGroup::class))
    var qiniuBucketName: String? = null

    //阿里云绑定的域名
    @NotBlank(message = "阿里云绑定的域名不能为空", groups = arrayOf(AliyunGroup::class))
    @URL(message = "阿里云绑定的域名格式不正确", groups = arrayOf(AliyunGroup::class))
    var aliyunDomain: String? = null
    //阿里云路径前缀
    var aliyunPrefix: String? = null
    //阿里云EndPoint
    @NotBlank(message = "阿里云EndPoint不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunEndPoint: String? = null
    //阿里云AccessKeyId
    @NotBlank(message = "阿里云AccessKeyId不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunAccessKeyId: String? = null
    //阿里云AccessKeySecret
    @NotBlank(message = "阿里云AccessKeySecret不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunAccessKeySecret: String? = null
    //阿里云BucketName
    @NotBlank(message = "阿里云BucketName不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunBucketName: String? = null

    //腾讯云绑定的域名
    @NotBlank(message = "腾讯云绑定的域名不能为空", groups = arrayOf(QcloudGroup::class))
    @URL(message = "腾讯云绑定的域名格式不正确", groups = arrayOf(QcloudGroup::class))
    var qcloudDomain: String? = null
    //腾讯云路径前缀
    var qcloudPrefix: String? = null
    //腾讯云AppId
    @NotNull(message = "腾讯云AppId不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudAppId: Int? = null
    //腾讯云SecretId
    @NotBlank(message = "腾讯云SecretId不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudSecretId: String? = null
    //腾讯云SecretKey
    @NotBlank(message = "腾讯云SecretKey不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudSecretKey: String? = null
    //腾讯云BucketName
    @NotBlank(message = "腾讯云BucketName不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudBucketName: String? = null
    //腾讯云COS所属地区
    @NotBlank(message = "所属地区不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudRegion: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
