package com.example.firstapp.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.firstapp.Model.Role;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    public static String generateToken(String signingKey, int userId, String subject, String userName, List<Role> authorities) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder().setSubject(subject).claim("userId", userId).claim("Authorities", authorities)
                .claim("userName", userName).setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        return builder.compact();
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

    public boolean validateToken(String authToken, String signingKey) {
        try {
            Jwts.parser().setSigningKey(signingKey).parseClaimsJws(authToken);

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserNameFromJWTs(String token, String signingKey) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return String.valueOf(claims.get("userName"));
    }

    public Object getRoleFromJWT(String token, String signingKey) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return claims.get("roleName");
    }
    public List<Role> getRoleNameFromJWT(String token, String signingKey) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return (List<Role>) claims.get("Authorities");
    }


    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(object);
    }

    public static String getUserIdFromJWT(String token, String signingKey) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return String.valueOf(claims.get("userId"));
    }
    public static String generateOTP() {
        int randomPin = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomPin);

        return otp; // returning value of otp
    }

    public static String generateHashString() throws Exception {

        String message = String.valueOf(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();

        MessageDigest md;

        try {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-1");
            // Generate the random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            // Passing the salt to the digest for the computation
            md.update(salt);
            // Generate the salted hash
            byte[] hashedPassword = md.digest(message.getBytes(StandardCharsets.UTF_8));

            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

            return String.valueOf(sb);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }


   /* public String getRoleJwt(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        int userId = (int) getIdFromJWTs(token, Constants.SIGNIN_KEY);
        String roleName = (String) getRoleFromJWT(token, Constants.SIGNIN_KEY);
        return userId + "-" + roleName;
    }*/

}
