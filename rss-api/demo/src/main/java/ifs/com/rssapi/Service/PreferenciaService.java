
package ifs.com.rssapi.Service;

import ifs.com.rssapi.Dto.PreferenciaDTO;
import ifs.com.rssapi.Dto.UsuarioPreferenciasDTO;
import ifs.com.rssapi.Model.CategoriaEntity;
import ifs.com.rssapi.Model.PreferenciaEntity;
import ifs.com.rssapi.Model.UsuarioEntity;
import ifs.com.rssapi.Repository.CategoriaRepository;
import ifs.com.rssapi.Repository.PreferenciaRepository;
import ifs.com.rssapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;



    public PreferenciaEntity salvarPreferencia(PreferenciaDTO preferenciaDTO) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(preferenciaDTO.getUsuarioId());
        Optional<CategoriaEntity> categoriaOpt = categoriaRepository.findById(preferenciaDTO.getCategoriaId());

        if (usuarioOpt.isPresent() && categoriaOpt.isPresent()) {
            UsuarioEntity usuario = usuarioOpt.get();
            CategoriaEntity categoria = categoriaOpt.get();
            PreferenciaEntity preferencia = new PreferenciaEntity();
            preferencia.setUsuarioEntity(usuario);
            preferencia.setCategoriaEntity(categoria);
            return preferenciaRepository.save(preferencia);
        } else {
            throw new RuntimeException("Usuário ou categoria não encontrados");
        }
    }

    public UsuarioPreferenciasDTO getUsuarioPreferencias(Long usuarioId) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isPresent()) {
            UsuarioEntity usuario = usuarioOpt.get();
            List<PreferenciaEntity> preferencias = preferenciaRepository.findByUsuarioEntityId(usuarioId);

            List<String> categorias = preferencias.stream()
                    .map(preferencia -> preferencia.getCategoriaEntity().getNomeCategoria())
                    .collect(Collectors.toList());

            return new UsuarioPreferenciasDTO(usuario.getNome(), categorias);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }


    }

}

