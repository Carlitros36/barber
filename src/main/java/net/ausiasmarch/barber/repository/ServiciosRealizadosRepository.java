/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.repository;

import net.ausiasmarch.barber.entity.ServiciosRealizadosEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Carlos
 */
public interface ServiciosRealizadosRepository extends JpaRepository<ServiciosRealizadosEntity, Long>{
    
    @Query(value = "SELECT * FROM ServiciosRealizados s WHERE s.servicios_id = :id_servicios", nativeQuery = true)
    Page<ServiciosRealizadosEntity> findByServiciosRealizadosXServicios(Long id_servicios, Pageable pageable);
    
    @Query(value = "SELECT * FROM ServiciosRealizados s WHERE s.cita_id = :id_cita", nativeQuery = true)
    Page<ServiciosRealizadosEntity> findByServiciosRealizadosXCita(Long id_cita, Pageable pageable);
}
