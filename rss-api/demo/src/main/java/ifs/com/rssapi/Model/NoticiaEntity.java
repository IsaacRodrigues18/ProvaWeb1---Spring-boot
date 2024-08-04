package ifs.com.rssapi.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "tb_noticia")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_noticia")
    private Long idNoticia;

    @Column(name = "titulo", columnDefinition = "TEXT")
    private String titulo;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Column(name = "data_publicacao")
    private LocalDateTime dataPublicacao;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "imagem_url", columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;
}



