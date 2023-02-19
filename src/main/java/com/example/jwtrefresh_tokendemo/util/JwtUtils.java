package com.example.jwtrefresh_tokendemo.util;

import com.example.jwtrefresh_tokendemo.exceptions.MyCustomException;
import com.example.jwtrefresh_tokendemo.result.ResponseStatusEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    /**
     * 秘钥
     */
    private static final String SECRET = "secret";

    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 3600 * 1000;

    /**
     * 刷新时间7天
     */
    private static final long REFRESH_TIME = 7 * 24 * 3600 * 1000;

    private static Map<String, String> tokenMap = new HashMap<>();

    public Map<String, String> getTokenMap() {
        return tokenMap;
    }


    /**
     * 生成token
     *
     * @param subject
     * @return
     */
    public static String createToken(String subject) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);//过期时间

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 生成token
     *
     * @param subject
     * @return
     */
    public static String createRefreshToken(String subject) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + REFRESH_TIME);//过期时间

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 刷新token
     *
     * @param subject
     * @return {@link String}
     */
    public static String freshToken(String subject) {
        return createToken(subject);
    }

    /**
     * 刷新freshToken
     *
     * @param subject
     * @return {@link String}
     */
    public static String reFreshToken(String subject) {
        return createRefreshToken(subject);
    }

    /**
     * 获取token中注册信息
     *
     * @param token
     * @return
     */
    public static Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        } catch (Exception e) {
            throw new MyCustomException(ResponseStatusEnum.TOKEN_ERROR);
        }
    }

    /**
     * 验证token是否过期失效
     *
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired(Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token失效时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public String getUsernameFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }


}
