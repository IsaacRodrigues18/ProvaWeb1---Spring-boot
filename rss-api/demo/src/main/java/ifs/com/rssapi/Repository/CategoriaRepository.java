package ifs.com.rssapi.Repository;

import ifs.com.rssapi.Model.CategoriaEntity;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    @NonNull
    Optional<CategoriaEntity> findByNomeCategoria(@NonNull String nomeCategoria);


}



