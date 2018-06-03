package io.cc.www.modules.sys.controller

import com.google.code.kaptcha.Constants
import com.google.code.kaptcha.Producer
import io.cc.www.common.utils.R
import io.cc.www.modules.sys.shiro.ShiroUtils
import org.apache.shiro.authc.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletResponse

@Controller
class SysLoginController {
    @Autowired
    private val producer: Producer? = null

    @RequestMapping("captcha.jpg")
    @Throws(IOException::class)
    fun captcha(response: HttpServletResponse) {
        response.setHeader("Cache-Control", "no-store, no-cache")
        response.contentType = "image/jpeg"

        //生成文字验证码
        val text = producer!!.createText()
        //生成图片验证码
        val image = producer.createImage(text)
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text)

        val out = response.outputStream
        ImageIO.write(image, "jpg", out)
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = ["/sys/login"], method = [(RequestMethod.POST)])
    fun login(username: String, password: String, captcha: String): R {
        val kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY)
        if (!captcha.equals(kaptcha, ignoreCase = true)) {
            return R.error("验证码不正确")
        }

        try {
            val subject = ShiroUtils.subject
            val token = UsernamePasswordToken(username, password)
            subject.login(token)
        } catch (e: UnknownAccountException) {
            return R.error(e.message!!)
        } catch (e: IncorrectCredentialsException) {
            return R.error("账号或密码不正确")
        } catch (e: LockedAccountException) {
            return R.error("账号已被锁定,请联系管理员")
        } catch (e: AuthenticationException) {
            return R.error("账户验证失败")
        }

        return R.ok()
    }

    /**
     * 退出
     */
    @RequestMapping(value = ["logout"], method = [(RequestMethod.GET)])
    fun logout(): String {
        ShiroUtils.logout()
        return "redirect:login.html"
    }

}
