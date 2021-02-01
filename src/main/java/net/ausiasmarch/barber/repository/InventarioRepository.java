/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.repository;

import java.util.List;
import net.ausiasmarch.barber.entity.InventarioEntity;


/**
 *
 * @author Carlos
 */
public interface InventarioRepository {
    
    List<InventarioEntity> findTop10ByOrderByExistenciasDesc();
}
