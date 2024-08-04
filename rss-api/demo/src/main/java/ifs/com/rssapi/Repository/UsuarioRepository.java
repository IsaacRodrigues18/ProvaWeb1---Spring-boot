package ifs.com.rssapi.Repository;

import ifs.com.rssapi.Model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @NonNull
    List<UsuarioEntity> findByEmail(@NonNull String email);

    @NonNull
    List<UsuarioEntity> findByLogin(@NonNull String login);

    @NonNull
    Optional<UsuarioEntity> findById(@NonNull Long id);
}
