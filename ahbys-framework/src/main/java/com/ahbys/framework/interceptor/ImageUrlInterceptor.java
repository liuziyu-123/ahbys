package com.ahbys.framework.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
@Component
public class ImageUrlInterceptor implements HandlerInterceptor {

    private static final String SECRET_KEY = "1234567890"; // 安全密钥，应存储在安全位置
    private static final long EXPIRATION_TIME = 60 * 1000; // 1分钟过期时间

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/images/")) {
            return true;
        }

        // 解析查询参数
        String queryString = request.getQueryString();
        if (queryString == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        Map<String, String> params = parseQueryString(queryString);
        String expire = params.get("expire");
        String signature = params.get("sig");
        String filename = requestURI.substring(requestURI.lastIndexOf('/') + 1);

        if (expire == null || signature == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // 验证过期时间
        long currentTime = System.currentTimeMillis();
        long expireTime = Long.parseLong(expire);
        if (currentTime > expireTime) {
            response.sendError(HttpServletResponse.SC_GONE, "URL已过期");
            return false;
        }

        // 验证签名
        String expectedSignature = generateSignature(requestURI, expire);
        if (!expectedSignature.equals(signature)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        request.setAttribute("filename",requestURI);
        return true;
    }

    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx > 0) {
                String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
                params.put(key, value);
            }

        }
        return params;
    }

    private String generateSignature(String filename, String expire) {
        String data = filename + expire + SECRET_KEY;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
