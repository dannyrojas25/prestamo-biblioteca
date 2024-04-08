package com.ceiba.biblioteca.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerador {
    //Método para crear un token por medio de la autenticación
    public String generarToken(Authentication authentication){
        String  username = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

        SecretKey clave = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        //Linea para generar el Token
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiracionToken)
                .signWith(clave)
                .compact();
    }

    //Método para extraer un Username a partir de un Token
    public String obtenerUsernameDeJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(ConstantesSeguridad.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //Método para validar el token
    public boolean validarToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(ConstantesSeguridad.JWT_FIRMA)
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Jwt a experido o está incorrecto.");
        }
    }
}
