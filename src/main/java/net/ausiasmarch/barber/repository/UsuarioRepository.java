package net.ausiasmarch.barber.repository;

import net.ausiasmarch.barber.entity.TipousuarioEntity;
import net.ausiasmarch.barber.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findByLoginAndPassword(String login, String password);
    
    @Query(value = "SELECT * FROM usuario u WHERE c.id_tipousuario = :id_tipousuario", nativeQuery = true)
    Page<UsuarioEntity> findByUsuarioXTipousuario(Long id_tipousuario, Pageable pageable);

    Page<UsuarioEntity> findByTipousuario(TipousuarioEntity oTipousuarioEntity, Pageable oPageable);
}
