package cn.ean.emp.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author ean
 * @FileName JwtTokenUtils
 * @Date 2022/5/23 18:07
 **/
@Component
public class JwtTokenUtils {

    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


    /**
     * 从token中获取登陆用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String userName;

        try {
            Claims claims = getClaimsFromToken(token);
            userName = claims.getSubject();
            System.out.println("\n\n=====JwtTokenUtils  debugger: " +
                    "\busername:" + userName);
        } catch (Exception e) {
            userName = null;
            System.out.println("====error====JwtTokenUtil-getUserNameFromToken");
            e.printStackTrace();
        }

        return userName;
    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {

        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("\n\n=====JwtTokenUtils  debugger: " +
                    "\bclaims:" + claims.toString());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        }

        return claims;

    }


    /**
     * 验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);

        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpireDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取失效时间
     * @param token
     * @return
     */
    private Date getExpireDateFromToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return claimsFromToken.getExpiration();
    }

    /**
     * 判断token是否能被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


    /**
     * 根据 荷载/负载 生成JWT TOKEN
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }





}
