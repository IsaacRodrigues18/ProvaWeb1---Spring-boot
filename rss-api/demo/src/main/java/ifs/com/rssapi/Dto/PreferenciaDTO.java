package ifs.com.rssapi.Dto;


import ifs.com.rssapi.Model.PreferenciaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Usado no método de salvar preferencia
public class PreferenciaDTO {
    private Long usuarioId;
    private Long categoriaId;

}




