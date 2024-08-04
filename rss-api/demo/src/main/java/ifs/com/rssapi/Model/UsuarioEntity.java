package ifs.com.rssapi.Model;
import ifs.com.rssapi.Dto.UsuarioDto;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "tb_usuario")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String email;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "usuarioEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PreferenciaEntity> preferencias = new HashSet<>();

    public UsuarioEntity(UsuarioDto usuarioDto){
        this.nome = usuarioDto.nome();;
        this.login = usuarioDto.login();
        this.senha = usuarioDto.senha();
        this.email = usuarioDto.email();
        this.dataNascimento =  usuarioDto.dataNascimento();
        this.status = usuarioDto.status();
    }






}
