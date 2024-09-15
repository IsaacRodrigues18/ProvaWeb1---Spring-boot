
package ifs.com.rssapi.Service;

import ifs.com.rssapi.Dto.PreferenciaDTO;
import ifs.com.rssapi.Dto.UsuarioPreferenciasDTO;
import ifs.com.rssapi.Model.CategoriaEntity;
import ifs.com.rssapi.Model.NoticiaEntity;
import ifs.com.rssapi.Model.PreferenciaEntity;
import ifs.com.rssapi.Model.UsuarioEntity;
import ifs.com.rssapi.Repository.CategoriaRepository;
import ifs.com.rssapi.Repository.PreferenciaRepository;
import ifs.com.rssapi.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private NoticiaService noticiaService;



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

            Map<String, List<NoticiaEntity>> categoriasComNoticias = new HashMap<>();

            for (PreferenciaEntity preferencia : preferencias) {
                String nomeCategoria = preferencia.getCategoriaEntity().getNomeCategoria();
                List<NoticiaEntity> noticias = noticiaService.listarMaisRecentesPorCategoria(preferencia.getCategoriaEntity().getIdCategoria());
                categoriasComNoticias.put(nomeCategoria, noticias);
            }

            return new UsuarioPreferenciasDTO(usuario.getNome(), categoriasComNoticias);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

}

