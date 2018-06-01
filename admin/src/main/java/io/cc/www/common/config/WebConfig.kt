package io.cc.www.common.config

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/")
    }

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>?) {
        val jackson2HttpMessageConverter = MappingJackson2HttpMessageConverter()
        val objectMapper = jackson2HttpMessageConverter.objectMapper

        //生成json时，将所有Long转换成String
        val simpleModule = SimpleModule()
        simpleModule.addSerializer(Long::class.java, ToStringSerializer.instance)
        simpleModule.addSerializer(java.lang.Long.TYPE, ToStringSerializer.instance)
        objectMapper.registerModule(simpleModule)

        jackson2HttpMessageConverter.objectMapper = objectMapper
        converters!!.add(0, jackson2HttpMessageConverter)
    }

}
