package ifs.com.rssapi.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//usado no método de listar preferenicas
public class UsuarioPreferenciasDTO {
    private String nomeUsuario;
    private List<String> categoriasPreferidas;
}

