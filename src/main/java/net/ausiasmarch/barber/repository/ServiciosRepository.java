/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.repository;

import java.util.List;
import net.ausiasmarch.barber.entity.ServiciosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Carlos
 */
public interface ServiciosRepository extends JpaRepository<ServiciosEntity, Long>{
    
    List<ServiciosEntity> findTop5ByOrderByPrecioDesc();

}
