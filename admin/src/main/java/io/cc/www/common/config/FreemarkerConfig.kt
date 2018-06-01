package io.cc.www.common.config

import io.cc.www.modules.sys.shiro.ShiroTag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import java.util.*

@Configuration
open class FreemarkerConfig {

    @Bean
    open fun freeMarkerConfigurer(shiroTag: ShiroTag): FreeMarkerConfigurer {
        val configurer = FreeMarkerConfigurer()
        configurer.setTemplateLoaderPath("classpath:/templates")
        val variables = HashMap<String, Any>(1)
        variables["shiro"] = shiroTag
        configurer.setFreemarkerVariables(variables)

        val settings = Properties()
        settings.setProperty("default_encoding", "utf-8")
        settings.setProperty("number_format", "0.##")
        configurer.setFreemarkerSettings(settings)
        return configurer
    }

}
