package ifs.com.rssapi.Model;
import ifs.com.rssapi.Dto.UsuarioDto;
import ifs.com.rssapi.Enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "tb_usuario")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioEntity implements UserDetails, Serializable {


    public UsuarioEntity(UsuarioDto usuarioDto){
        this.nome = usuarioDto.nome();;
        this.login = usuarioDto.login();
        this.senha = usuarioDto.senha();
        this.email = usuarioDto.email();
        this.dataNascimento =  usuarioDto.dataNascimento();
        this.status = usuarioDto.status();
        this.role = usuarioDto.role();
    }

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

    @Column(nullable = false)
    private RoleEnum role;

    @OneToMany(mappedBy = "usuarioEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PreferenciaEntity> preferencias = new HashSet<>();




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == RoleEnum.ADMIN){
            return List.of( new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("Role_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status;
    }

    @Override
    public boolean isEnabled() {
        return this.status;
    }
}
