package com.wondersgroup.springbootshirodemo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CommonHelper {
    public static String getSessionCasUrl(HttpServletRequest request, String casLoginUrl) {
        HttpSession session = request.getSession();
        Object o = session.getAttribute("shiro_cas_url");
        if (o != null) {
            return o.toString();
        }
        session.setAttribute("shiro_cas_url", casLoginUrl);
        return casLoginUrl;
    }
}
