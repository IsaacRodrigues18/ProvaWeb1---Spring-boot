package ifs.com.rssapi.Repository;

import ifs.com.rssapi.Model.NoticiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticiaRepository extends JpaRepository<NoticiaEntity, Long> {
    @Query(value = "SELECT id_noticia, data_publicacao, descricao, imagem_url, link, titulo, id_categoria FROM tb_noticia", nativeQuery = true)
    List<Object[]> listarTodasNoticias();

    @Query(value = "SELECT id_noticia, data_publicacao, descricao, imagem_url, link, titulo, id_categoria FROM tb_noticia ORDER BY data_publicacao DESC", nativeQuery = true)
    List<Object[]> listarMaisRecentesNoticias();
    Optional<NoticiaEntity> findById(Long id);

    boolean existsByLink(String link);
}
