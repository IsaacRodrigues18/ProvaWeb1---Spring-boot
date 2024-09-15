package ifs.com.rssapi.Service;

import ifs.com.rssapi.Dto.LoginDto;
import ifs.com.rssapi.Dto.UsuarioDto;
import ifs.com.rssapi.Model.UsuarioEntity;
import ifs.com.rssapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDto cadastrar(UsuarioDto usuarioDto){

        UsuarioEntity usuarioJaExiste = usuarioRepository.findByLogin(usuarioDto.login());

        if(usuarioJaExiste != null){
            throw new RuntimeException("Usuário já existe");
        }

        var passWordHash = passwordEncoder.encode(usuarioDto.senha());

        UsuarioEntity usuario = new UsuarioEntity(usuarioDto);
        usuario.setSenha(passWordHash); // Certifique-se de definir a senha criptografada

        UsuarioEntity novoUsuario = usuarioRepository.save(usuario);

        return new UsuarioDto(
                novoUsuario.getNome(),
                novoUsuario.getLogin(),
                novoUsuario.getSenha(),
                novoUsuario.getEmail(),
                novoUsuario.getDataNascimento(),
                novoUsuario.isStatus(),
                novoUsuario.getRole()
        );
    }

    public boolean login(LoginDto loginDto) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(loginDto.getLogin());

        if (usuario == null) {
            return false;  // Usuário não encontrado
        }

        return passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha());  // Verifica se a senha está correta
    }

    public UsuarioEntity obterUsuarioPorLogin(String login) {
        return usuarioRepository.findByLogin(login);  // Método para buscar o usuário pelo login
    }



    //VERSÃO DE LOGIN ESTÁVEL - PARTE 3
    /*public boolean login(LoginDto loginDto) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(loginDto.getLogin());
        if (usuario == null) {
            return false;  // Usuário não encontrado
        }
        return passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha()); // Verifica se a senha é válida
    }*/

}

