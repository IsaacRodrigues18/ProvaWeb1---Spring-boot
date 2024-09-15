package ifs.com.rssapi.Dto;


import ifs.com.rssapi.Model.NoticiaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
//usado no método de listar preferenicas
public class UsuarioPreferenciasDTO {
    private String nomeUsuario;
    private Map<String, List<NoticiaEntity>> categoriasComNoticias;

    public UsuarioPreferenciasDTO(String nomeUsuario, Map<String, List<NoticiaEntity>> categoriasComNoticias) {
        this.nomeUsuario = nomeUsuario;
        this.categoriasComNoticias = categoriasComNoticias;
    }
}

