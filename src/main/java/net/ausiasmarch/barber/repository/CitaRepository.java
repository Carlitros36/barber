/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.repository;

import java.util.List;
import net.ausiasmarch.barber.entity.CitaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos
 */
public interface CitaRepository extends JpaRepository<CitaEntity, Long>{
    
    @Query(value = "SELECT * FROM cita c WHERE c.usuario_id = :id_usuario", nativeQuery = true)
    List<CitaEntity> findByCitaXUsuario(Long id_usuario);
}
