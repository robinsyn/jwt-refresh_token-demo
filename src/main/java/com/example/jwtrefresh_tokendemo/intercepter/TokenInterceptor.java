package com.example.jwtrefresh_tokendemo.intercepter;


import com.example.jwtrefresh_tokendemo.exceptions.GraceException;
import com.example.jwtrefresh_tokendemo.result.ResponseStatusEnum;
import com.example.jwtrefresh_tokendemo.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception, IOException {

        String userId = request.getHeader("headerUserId");
        String userRefreshToken = request.getHeader("headerUserRefreshToken");
        String userToken = request.getHeader("headerUserToken");

        Claims refreshTokenClaims = jwtUtils.getTokenClaim(userRefreshToken);
        // refresh过期
        if (refreshTokenClaims == null) {
            GraceException.display(ResponseStatusEnum.TOKEN_EXP);
            return false;
        }

        Claims tokenClaims = jwtUtils.getTokenClaim(userToken);
        // refresh未过期，token过期了
        if (tokenClaims == null) {
            String newToken = jwtUtils.freshToken(userId);
            String newRefreshToken = jwtUtils.reFreshToken(userId);
            HashMap<String, String> map = new HashMap<>();
            map.put("token", newToken);
            map.put("refreshToken", newRefreshToken);
            GraceException.display(ResponseStatusEnum.TOKEN_REFRESH, map);
            return false;
        }

        /**
         * true: 请求放行
         * false: 请求拦截
         */
        return true;
    }
}

