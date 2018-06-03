package io.cc.www

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan(basePackages = ["io.cc.www.modules.*.dao"])
open class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}


