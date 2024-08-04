package ifs.com.rssapi.Service;

import ifs.com.rssapi.Dto.UsuarioDto;
import ifs.com.rssapi.Model.UsuarioEntity;
import ifs.com.rssapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDto cadastrar(UsuarioDto usuarioDto){
        UsuarioEntity usuario = new UsuarioEntity(usuarioDto);
        UsuarioEntity novoUsuario = usuarioRepository.save(usuario);
        return new UsuarioDto(
                novoUsuario.getNome(),
                novoUsuario.getLogin(),
                novoUsuario.getSenha(),
                novoUsuario.getEmail(),
                novoUsuario.getDataNascimento(),
                novoUsuario.isStatus()
        );

    }
}
