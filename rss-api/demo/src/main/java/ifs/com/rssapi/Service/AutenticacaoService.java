package ifs.com.rssapi.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ifs.com.rssapi.Dto.AutenticacaoDto;
import ifs.com.rssapi.Model.UsuarioEntity;
import ifs.com.rssapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    public String obterToken(AutenticacaoDto autenticacaoDto) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(autenticacaoDto.login());
        return geraTokenJwt(usuarioEntity);
    }

    public String geraTokenJwt(UsuarioEntity usuarioEntity) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("token" );
            return JWT.create()
                    .withIssuer("rss-api-noticias")
                    .withSubject(usuarioEntity.getLogin())
                    .withExpiresAt(geraDataExpiracao())
                    .sign(algorithm);// assina o token
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token! " + e.getMessage());
        }
    }

    private Instant geraDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validaTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("token");
            return JWT.require(algorithm)
                    .withIssuer("rss-api-noticias")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
