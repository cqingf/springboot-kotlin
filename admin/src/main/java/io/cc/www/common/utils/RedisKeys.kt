package io.cc.www.common.utils

object RedisKeys {

    fun getSysConfigKey(key: String): String {
        return "sys:config:$key"
    }

    fun getShiroSessionKey(key: String): String {
        return "sessionid:$key"
    }
}
