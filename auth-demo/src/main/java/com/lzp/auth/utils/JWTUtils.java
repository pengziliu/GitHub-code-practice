package com.lzp.auth.utils;

import com.alibaba.fastjson.util.TypeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public abstract class JWTUtils {

    public static final String UID = "uid";
    private static final String SECRET = "WgtqaT1HNTZPZNMDJu3k";
    private static final long EXPIRE = 60 * 1000;

    /**
     * 生成token
     *
     * @param uid
     * @return
     */
    public static String generate(String uid) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE * 60 * 24);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(UID, uid);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 解析Claims
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //解析失败
            log.info("无效的token.{}",e.getMessage());
        }
        return claims;
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAt(String token) {
        return getClaim(token).getIssuedAt();
    }

    /**
     * 获取openid
     */
    public static String getOpenId(String token) {
        return TypeUtils.castToString(getClaim(token).get(UID));
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String token) {
        return getClaim(token).getExpiration();
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiration(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }

    }

    public static void main(String[] args) {
        String generate = generate("12121");
        String openId = getOpenId(generate);
        System.out.println(openId);
        System.out.println(generate);
    }
}
