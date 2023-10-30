package com.springCrud.CrudSpringBackEnd.jwt;

import com.springCrud.CrudSpringBackEnd.Service.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

@Component

public class JwtUtils {

    @Value("${springCrudApp.app.jwtSecret}")
    private String jwtSecret;

    @Value("${springCrudApp.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${springCrudApp.app.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

}
