package io.cc.www.modules.sys.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SysPageController {

    @RequestMapping("modules/{module}/{url}.html")
    fun module(@PathVariable("module") module: String, @PathVariable("url") url: String): String {
        return "modules/$module/$url"
    }

    @RequestMapping(value = ["/", "index.html"])
    fun index(): String {
        return "index"
    }

    @RequestMapping("index1.html")
    fun index1(): String {
        return "index1"
    }

    @RequestMapping("login.html")
    fun login(): String {
        return "login"
    }

    @RequestMapping("main.html")
    fun main(): String {
        return "main"
    }

    @RequestMapping("404.html")
    fun notFound(): String {
        return "404"
    }

}
