package io.cc.www.common.config

import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
open class KaptchaConfig {

    @Bean
    open fun producer(): DefaultKaptcha {
        val properties = Properties()
        properties["kaptcha.border"] = "no"
        properties["kaptcha.textproducer.font.color"] = "black"
        properties["kaptcha.textproducer.char.space"] = "5"
        val config = Config(properties)
        val defaultKaptcha = DefaultKaptcha()
        defaultKaptcha.config = config
        return defaultKaptcha
    }
}
