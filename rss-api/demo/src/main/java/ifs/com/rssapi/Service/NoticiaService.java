package ifs.com.rssapi.Service;

import ifs.com.rssapi.Model.NoticiaEntity;
import ifs.com.rssapi.Repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticiaService  {

    @Autowired
    private NoticiaRepository noticiaRepository;

    public List<NoticiaEntity> listarTodasNoticias() {
        List<Object[]> resultados = noticiaRepository.listarTodasNoticias();
        return resultados.stream().map(this::mapToNoticiaEntity).collect(Collectors.toList());
    }

    public List<NoticiaEntity> listarMaisRecentesNoticias() {
        List<Object[]> resultados = noticiaRepository.listarMaisRecentesNoticias();
        return resultados.stream().map(this::mapToNoticiaEntity).collect(Collectors.toList());
    }

    private NoticiaEntity mapToNoticiaEntity(Object[] resultado) {
        NoticiaEntity noticia = new NoticiaEntity();
        noticia.setIdNoticia(((Number) resultado[0]).longValue());
        noticia.setDataPublicacao(((java.sql.Timestamp) resultado[1]).toLocalDateTime());
        noticia.setDescricao((String) resultado[2]);
        noticia.setImageUrl((String) resultado[3]);
        noticia.setLink((String) resultado[4]);
        noticia.setTitulo((String) resultado[5]);
        noticia.setCategoria(null);  // Map category if needed
        return noticia;
    }

    public Optional<NoticiaEntity> buscarNoticiaPorId(Long id) {
        return noticiaRepository.findById(id);
    }




    public boolean existsByLink(String link) {
        return noticiaRepository.existsByLink(link);
    }



}
