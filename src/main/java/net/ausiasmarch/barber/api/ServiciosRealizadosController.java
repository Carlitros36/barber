/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.barber.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.barber.entity.ServiciosRealizadosEntity;
import net.ausiasmarch.barber.entity.UsuarioEntity;
import net.ausiasmarch.barber.repository.CitaRepository;
import net.ausiasmarch.barber.repository.ServiciosRealizadosRepository;
import net.ausiasmarch.barber.repository.UsuarioRepository;
import net.ausiasmarch.barber.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/servicios_realizados")
public class ServiciosRealizadosController {
 
    @Autowired
    HttpSession oHttpSession;
    
    @Autowired
    ServiciosRealizadosRepository oServiciosRealizadosRepository;
    
    @Autowired
    UsuarioRepository oUsuarioRepository;
    
    @Autowired
    CitaRepository oCitaRepository;
    
    @Autowired
    FillService oFillService;
    
     @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else { //si hay sesion
            if (oUsuarioEntity.getTipousuario().getId() == 1) { // admin
                if (oServiciosRealizadosRepository.existsById(id)) {
                    return new ResponseEntity<ServiciosRealizadosEntity>(oServiciosRealizadosRepository.getOne(id), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            } else { //cliente
                ServiciosRealizadosEntity oServiciosRealizadosEntity = oServiciosRealizadosRepository.getOne(id);
                if (oServiciosRealizadosEntity != null) {
                    if (oServiciosRealizadosEntity.getCita().getUsuario().equals(oUsuarioEntity.getId())) {
                        return new ResponseEntity<ServiciosRealizadosEntity>(oServiciosRealizadosEntity, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            }
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> count() {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        ServiciosRealizadosEntity oServiciosRealizadosEntity = new ServiciosRealizadosEntity();

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                return new ResponseEntity<Long>(oServiciosRealizadosRepository.count(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {

            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                if (oServiciosRealizadosRepository.count() <= 1000) {
                    return new ResponseEntity<List<ServiciosRealizadosEntity>>(oServiciosRealizadosRepository.findAll(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
            }
        }
    }

    @PostMapping("/fill/{amount}")
    public ResponseEntity<?> fill(@PathVariable(value = "amount") Long amount) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                return new ResponseEntity<Long>(oFillService.ServiciosRealizadosFill(amount), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
