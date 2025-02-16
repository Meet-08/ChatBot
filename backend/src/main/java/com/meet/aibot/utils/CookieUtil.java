package com.meet.aibot.utils;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie createCookie(String name, String value, int maxAge, boolean httpOnly, boolean secure, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        cookie.setPath(path);
        return cookie;
    }
}
