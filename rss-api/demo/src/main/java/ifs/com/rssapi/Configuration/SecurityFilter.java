package ifs.com.rssapi.Configuration;

import ifs.com.rssapi.Model.UsuarioEntity;
import ifs.com.rssapi.Repository.UsuarioRepository;
import ifs.com.rssapi.Service.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extraiTokeHeader(request);

        if (token != null) {
            String login = autenticacaoService.validaTokenJwt(token);
            if (login != null) {
                UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login);

                if (usuarioEntity != null) {
                    var autentication = new UsernamePasswordAuthenticationToken(usuarioEntity, null, usuarioEntity.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(autentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extraiTokeHeader(HttpServletRequest request) {
        var autenticacaoHeader = request.getHeader("Authorization");
        if (autenticacaoHeader == null || !autenticacaoHeader.startsWith("Bearer ")) {
            return null;
        }
        return autenticacaoHeader.substring(7); // Remove "Bearer " para obter o token
    }

}

