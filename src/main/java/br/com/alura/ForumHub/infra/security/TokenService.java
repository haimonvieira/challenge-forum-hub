package br.com.alura.ForumHub.infra.security;

import br.com.alura.ForumHub.domain.usuario.Usuario;
import br.com.alura.ForumHub.infra.exception.ValidacaoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${forum_hub.secret}")
    private String secret;

    @Value("${forum_hub.expiration}")
    private long expiration;

    private static final String ISSUER = "Fórum Hub";

    public String gerarToken(Usuario usuario){

        try {

            var algoritmo = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(expiracao())
                    .sign(algoritmo);

        }catch (JWTCreationException e){
            throw new ValidacaoException("Erro ao gerar Token JWT: " + e);
        }

    }

    //Metodo que válida o token
    public String getSubject(String tokenJWT){

        try {

            var algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        }catch (JWTVerificationException e){
            throw new ValidacaoException("Token JWT inválido ou expirado: " + e);
        }

    }

    private Instant expiracao(){
        return LocalDateTime.now().plusSeconds(expiration).toInstant(ZoneOffset.of("-03:00"));
    }

}
