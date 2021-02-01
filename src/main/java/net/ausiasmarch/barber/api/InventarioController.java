/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.barber.entity.InventarioEntity;
import net.ausiasmarch.barber.entity.UsuarioEntity;
import net.ausiasmarch.barber.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos
 */
@RestController
@RequestMapping("/inventario")
public class InventarioController {
 
    @Autowired
    HttpSession oHttpSession;

    @Autowired
    InventarioRepository oInventarioRepository;
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        if (oInventarioRepository.existsById(id)) {
            return new ResponseEntity<InventarioEntity>(oInventarioRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<InventarioEntity>(oInventarioRepository.getOne(id), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oInventarioRepository.count() <= 100) {
            return new ResponseEntity<List<InventarioEntity>>(oInventarioRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oInventarioRepository.count(), HttpStatus.OK);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody InventarioEntity oInventarioEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                if (oInventarioEntity.getId() == null) {
                    return new ResponseEntity<InventarioEntity>(oInventarioRepository.save(oInventarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody InventarioEntity oInventarioEntity) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) { //administrador
                oInventarioEntity.setId(id);
                if (oInventarioRepository.existsById(id)) {
                    return new ResponseEntity<InventarioEntity>(oInventarioRepository.save(oInventarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {  //cliente
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {

                oInventarioRepository.deleteById(id);

                if (oInventarioRepository.existsById(id)) {
                    return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @GetMapping("/orderexistencias/10/desc")
    public ResponseEntity<?> get10InventarioOrderByExistenciasDesc() {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null || oUsuarioEntity.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<InventarioEntity> oPage = oInventarioRepository.findTop10ByOrderByExistenciasDesc();
            return new ResponseEntity<List<InventarioEntity>>(oPage, HttpStatus.OK);
        }
    }
}
