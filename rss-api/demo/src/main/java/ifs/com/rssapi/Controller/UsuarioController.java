package ifs.com.rssapi.Controller;

import ifs.com.rssapi.Dto.LoginDto;
import ifs.com.rssapi.Model.UsuarioEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ifs.com.rssapi.Dto.UsuarioDto;
import ifs.com.rssapi.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:58652")  // Permite CORS apenas para essa origem específica
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuario/cadastra")
    public UsuarioDto cadastra(@RequestBody UsuarioDto usuarioDto) {
        return usuarioService.cadastrar(usuarioDto);
    }

    @PostMapping("/usuario/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        boolean isAuthenticated = usuarioService.login(loginDto);

        if (isAuthenticated) {
            // Busca o usuário autenticado
            UsuarioEntity usuario = usuarioService.obterUsuarioPorLogin(loginDto.getLogin());

            // Retorna uma resposta contendo o ID do usuário e uma mensagem de sucesso
            return ResponseEntity.ok(Map.of(
                    "message", "Login realizado com sucesso!",
                    "usuarioId", usuario.getId()  // Retorna o ID do usuário autenticado
            ));
        } else {
            return ResponseEntity.status(401).body("Login falhou!");
        }
    }


}



//VERSÃO INSTAVÉL PARTE 3
    /*@PostMapping("/usuario/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        boolean isAuthenticated = usuarioService.login(loginDto);

        if (isAuthenticated) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(401).body("Login falhou!");
        }
    }
}*/











