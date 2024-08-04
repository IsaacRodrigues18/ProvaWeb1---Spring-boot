package ifs.com.rssapi.Repository;

import ifs.com.rssapi.Model.PreferenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenciaRepository extends JpaRepository<PreferenciaEntity,Long> {
    List<PreferenciaEntity> findByUsuarioEntityId(Long usuarioId);
}

