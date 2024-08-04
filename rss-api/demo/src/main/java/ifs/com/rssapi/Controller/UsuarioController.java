package ifs.com.rssapi.Controller;

import ifs.com.rssapi.Dto.UsuarioDto;
import ifs.com.rssapi.Model.NoticiaEntity;
import ifs.com.rssapi.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastra")
        public UsuarioDto cadastra(@RequestBody UsuarioDto usuarioDto){
        return usuarioService.cadastrar(usuarioDto);
    }
    @GetMapping
    private String getOk(){
        return "Ok";
    }
}

