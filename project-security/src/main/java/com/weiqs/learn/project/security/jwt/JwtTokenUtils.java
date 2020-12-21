package com.weiqs.learn.project.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author weiqisheng
 * @Title: JwtTokenUtils
 * @ProjectName myProject
 * @Description: TODO
 * @date 2020/12/1111:40
 */
public class JwtTokenUtils {

    //token请求头
    public static final String TOKEN_HEADER = "Authorization";
    //token前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    //签名主题
    public static final String SUBJECT = "piconjo";
    //过期时间
    public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
    //应用秘钥
    public static final String APPSECRET_KEY = "piconjo_secret";
    // 角色权限声明
    private static final String ROLE_CLAIMS = "role";

    /**
     * 生成token
     * @param username
     * @param role
     * @return
     */
    public static String createToken(String userName,String role){
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,role);

        String token = Jwts
                .builder()
                .setSubject(userName)
                .setClaims(map)
                .claim("userName",userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +EXPIRITION))
                .signWith(SignatureAlgorithm.HS256,APPSECRET_KEY).compact();
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
            e.printStackTrace();;
            return null;
        }
    }

    /**
     * 从token中获取userName
     * @param token
     * @return
     */
    public static String getUserName(String token){
        Claims claims = checkJWT(token);
        if (Objects.isNull(claims)){
            throw new RuntimeException("token为空");
        }
        return claims.get("userName").toString();
    }

    /**
     * 从token中获取角色
     * @param token
     * @return
     */
    public static String getUserRole(String token){
        Claims claims = checkJWT(token);
        if (Objects.isNull(claims)){
            throw new RuntimeException("token为空");
        }
        return claims.get(ROLE_CLAIMS).toString();
    }

    /**
     * 校验token是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        Claims claims = checkJWT(token);
        if (Objects.isNull(claims)){
            throw new RuntimeException("token为空");
        }
        return claims.getExpiration().before(new Date());
    }
}
