/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.barber.entity.ServiciosEntity;
import net.ausiasmarch.barber.entity.UsuarioEntity;
import net.ausiasmarch.barber.repository.ServiciosRepository;
import net.ausiasmarch.barber.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/servicios")
public class ServiciosController {
    
    @Autowired
    HttpSession oHttpSession;
    
    @Autowired
    ServiciosRepository oServiciosRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        if (oServiciosRepository.existsById(id)) {
            return new ResponseEntity<ServiciosEntity>(oServiciosRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<ServiciosEntity>(oServiciosRepository.getOne(id), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oServiciosRepository.count() <= 1000) {
            return new ResponseEntity<List<ServiciosEntity>>(oServiciosRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oServiciosRepository.count(), HttpStatus.OK);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ServiciosEntity oServiciosEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                if (oServiciosEntity.getId() == null) {
                    return new ResponseEntity<ServiciosEntity>(oServiciosRepository.save(oServiciosEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ServiciosEntity oServiciosEntity) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) { //administrador
                oServiciosEntity.setId(id);
                if (oServiciosRepository.existsById(id)) {
                    return new ResponseEntity<ServiciosEntity>(oServiciosRepository.save(oServiciosEntity), HttpStatus.OK);
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

                oServiciosRepository.deleteById(id);

                if (oServiciosRepository.existsById(id)) {
                    return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @GetMapping("/orderprecio/5/desc")
    public ResponseEntity<?> get5ServiciosOrderByPrecioDesc() {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null || oUsuarioEntity.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<ServiciosEntity> oPage = oServiciosRepository.findTop5ByOrderByPrecioDesc();
            return new ResponseEntity<List<ServiciosEntity>>(oPage, HttpStatus.OK);
        }
    }
}
