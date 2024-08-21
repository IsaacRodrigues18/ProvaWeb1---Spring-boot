package ifs.com.rssapi.Controller;

import ifs.com.rssapi.Dto.AutenticacaoDto;
import ifs.com.rssapi.Service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AutenticacaoDto autenticacaoDto){

        var usuarioAutenticacaoToken = new UsernamePasswordAuthenticationToken(autenticacaoDto.login(),autenticacaoDto.senha());

        authenticationManager.authenticate(usuarioAutenticacaoToken);

        return autenticacaoService.obterToken(autenticacaoDto);
    }

    }
