package com.wondersgroup.springbootshirodemo.controller;

import com.wondersgroup.springbootshirodemo.shiro.MyUsernameToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.shiro.authc.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wondersgroup.springbootshirodemo.utils.CommonHelper.getSessionCasUrl;

@Controller
public class LoginController {
    @RequestMapping(value = {"404"})
    public String error404() {
        return "/common/404";
    }

    @RequestMapping(value = {"500"})
    public String error500(String message, Model model) {
        model.addAttribute("message", message);
        return "/common/500";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @RequestMapping("/index")
    public String index(Model model){
        //获取登录信息
        Subject subject = SecurityUtils.getSubject();
        Object v = subject.getPrincipal();
        return "/index";
    }

    @ResponseBody
    @RequestMapping("logout")
    public String logout(Model model, HttpServletResponse response, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (Exception e) {
            return "no";
        }
        return "ok";
    }

    @Value("${cas_login_url}")
    private String casLoginUrl;

    @RequestMapping("/login")
    public String login(String token, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        String casLogin = getSessionCasUrl(request, casLoginUrl);
        if (StringUtils.isBlank(token)) {
            redirectAttributes.addAttribute("message", "token不能为空");
            return "redirect:" + casLogin;
        }

        Subject currentUser = SecurityUtils.getSubject();
        MyUsernameToken userToken = new MyUsernameToken(token, true);
        try {
            currentUser.login(userToken);   //执行认证doGetAuthenticationInfo
        } catch (AuthenticationException ae) {
            redirectAttributes.addAttribute("message", ae.getMessage());
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", "验证异常");
        }
        if (currentUser.isAuthenticated()) {
            return "redirect:/index";
        } else {
            return "redirect:" + casLogin;
        }
    }
}
