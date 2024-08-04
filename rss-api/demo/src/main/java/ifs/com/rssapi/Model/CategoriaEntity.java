package ifs.com.rssapi.Model;



import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "tb_categoria")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nome_categoria", nullable = false, unique = true)
    private String nomeCategoria;

    @Column(name = "url_rss", nullable = false)
    private String urlRss;

    @OneToMany(mappedBy = "categoria")
    private Set<NoticiaEntity> noticiaList = new HashSet<>();

    //@ManyToMany(mappedBy = "categorias")
    //private Set<UsuarioEntity> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "categoriaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PreferenciaEntity> preferencias = new HashSet<>();


    public CategoriaEntity(String categoriaNome, String rssUrl) {
        this.nomeCategoria = categoriaNome;
        this.urlRss = rssUrl;
    }



}
