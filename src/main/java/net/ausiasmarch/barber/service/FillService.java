package net.ausiasmarch.barber.service;

import java.sql.Time;
import java.time.ZoneId;
import java.util.Optional;
import net.ausiasmarch.barber.entity.CitaEntity;
import net.ausiasmarch.barber.entity.InventarioEntity;
import net.ausiasmarch.barber.entity.ServiciosEntity;
import net.ausiasmarch.barber.entity.ServiciosRealizadosEntity;
import net.ausiasmarch.barber.entity.TipousuarioEntity;
import net.ausiasmarch.barber.entity.UsuarioEntity;
import net.ausiasmarch.barber.helper.RandomHelper;
import net.ausiasmarch.barber.repository.CitaRepository;
import net.ausiasmarch.barber.repository.InventarioRepository;
import net.ausiasmarch.barber.repository.ServiciosRealizadosRepository;
import net.ausiasmarch.barber.repository.ServiciosRepository;
import net.ausiasmarch.barber.repository.TipousuarioRepository;
import net.ausiasmarch.barber.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FillService {

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;
    
    @Autowired
    CitaRepository oCitaRepository;
    
    @Autowired
    InventarioRepository oInventarioRepository;

    @Autowired
    ServiciosRealizadosRepository oServiciosRealizadosRepository;
    
    @Autowired
    ServiciosRepository oServiciosRepository;
    
    public Long tipousuarioFill() {

        TipousuarioEntity oTipousuarioEntity = new TipousuarioEntity();
        oTipousuarioEntity.setNombre("Administrador");
        oTipousuarioRepository.save(oTipousuarioEntity);

        oTipousuarioEntity = new TipousuarioEntity();
        oTipousuarioEntity.setNombre("Cliente");
        oTipousuarioRepository.save(oTipousuarioEntity);

        return 2L;
        
    }

    public Long usuarioFill(Long cantidad) {

        String[] nombres = {"Andrea", "David", "Baldomero", "Balduino", "Baldwin", "Baltasar", "Barry", "Bartolo",
            "Bartolomé", "Baruc", "Baruj", "Candelaria", "Cándida", "Canela", "Caridad", "Carina", "Carisa",
            "Caritina", "Carlota", "Baltazar"};
        String[] apellidos = {"Gomez", "Guerrero", "Cardenas", "Cardiel", "Cardona", "Cardoso", "Cariaga", "Carillo",
            "Carion", "Castiyo", "Castorena", "Castro", "Grande", "Grangenal", "Grano", "Grasia", "Griego",
            "Grigalva"};

        for (int i = 1; i <= cantidad; i++) {

            UsuarioEntity oUsuarioEntity = new UsuarioEntity();
            String nombre = nombres[(int) (Math.floor(Math.random() * ((nombres.length - 1) - 0 + 1) + 0))];
            String apellido1 = apellidos[(int) (Math.floor(Math.random() * ((apellidos.length - 1) - 0 + 1) + 0))];
            String apellido2 = apellidos[(int) (Math.floor(Math.random() * ((apellidos.length - 1) - 0 + 1) + 0))];
            oUsuarioEntity.setNombre(nombre);
            oUsuarioEntity.setApellido1(apellido1);
            oUsuarioEntity.setApellido2(apellido2);
            oUsuarioEntity.setLogin(nombre.substring(0, 2).toLowerCase() + apellido1.substring(0, 2).toLowerCase() + apellido2.substring(0, 2).toLowerCase());
            oUsuarioEntity.setPassword(nombre);
            oUsuarioEntity.setEmail(nombre + apellido1.charAt(0) + "@ausiasmarch.net");
            TipousuarioEntity oTipousuarioEntity = new TipousuarioEntity();
            oTipousuarioEntity.setId(2L);
            oUsuarioEntity.setTipousuario(oTipousuarioEntity);/*
            oUsuarioEntity.setToken("");
            oUsuarioEntity.setValidado(true);
            oUsuarioEntity.setActivo(true);*/

            oUsuarioRepository.save(oUsuarioEntity);
        }
        return cantidad;
    }

    public Long citaFill(Long cantidad) {

        for (int i = 1; i <= cantidad; i++) {
            CitaEntity oCitaEntity = new CitaEntity();
            oCitaEntity.setFecha(RandomHelper.getRandomDate());
            Optional<UsuarioEntity> optionalUsuarioEntity = oUsuarioRepository.findById(Long.valueOf(RandomHelper.getRandomInt(100, 150)));
            UsuarioEntity oUsuarioEntity = optionalUsuarioEntity.get();                          
            oCitaEntity.setUsuario(oUsuarioEntity);
            oCitaRepository.save(oCitaEntity);
        }
        return cantidad;

    }
    
     public String getNombreProducto() {
        String nombre = "";
        switch (RandomHelper.getRandomInt(1, 3)) {
            case 1:
                nombre = "Productos";
                break;
            case 2:
                nombre = "Accesorios";
                break;
            case 3:
                nombre = "Herramientas";
                break;
        }
        return nombre;
    }
     
     public String getApellidoProducto() {
        String nombre = "";
        switch (RandomHelper.getRandomInt(1, 4)) {
            case 1:
                nombre += " de limpieza";
                break;
            case 2:
                nombre += " de puesta a punto";
                break;
            case 3:
                nombre += " de servicio";
                break;
            case 4:
                nombre += " de protección";
                break;
        }
        return nombre;
    }
     
     private String getProductoFinal() {
        String nombre = "";
        switch (RandomHelper.getRandomInt(1, 14)) {

            case 1:
                nombre += " de color negro";
                break;
            case 2:
                nombre += " de color blanco";
                break;
            case 3:
                nombre += " de color gris";
                break;
            case 4:
                nombre += " de color verde";
                break;
            case 5:
                nombre += " de color rojo";
                break;
            case 6:
                nombre += " de color azul";
                break;
            case 7:
                nombre += " de color amarillo";
                break;
            case 8:
                nombre += " de color marrón";
                break;
            case 9:
                nombre += " de color rosa";
                break;
            case 10:
                nombre += " adaptables";
                break;
            case 11:
                nombre += " variables";
                break;
            case 12:
                nombre += " super eficientes";
                break;
            case 13:
                nombre += " ultra potentes";
                break;
            case 14:
                nombre += " dinámicos";
                break;
        }
        return nombre;
    }
     
     public Long InventarioFill(Long cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            InventarioEntity oInventarioEntity = new InventarioEntity();
            oInventarioEntity.setNombre(this.getNombreProducto() + this.getApellidoProducto() + this.getProductoFinal());
            oInventarioEntity.setExistencias(RandomHelper.getRandomInt(1, 20));
            oInventarioRepository.save(oInventarioEntity);
        }
        return cantidad;
    }
     
      public Long ServiciosRealizadosFill(Long cantidad) {
        for (int i = 1; i <= cantidad; i++) {
           ServiciosRealizadosEntity oServiciosRealizadosEntity = new ServiciosRealizadosEntity();
            oServiciosRealizadosEntity.setCantidad(1);
            Optional<CitaEntity> optionalCitaEntity = oCitaRepository.findById(Long.valueOf(RandomHelper.getRandomInt(16, 19)));
            CitaEntity oCitaEntity = optionalCitaEntity.get();
            oServiciosRealizadosEntity.setCita(oCitaEntity);
            Optional<ServiciosEntity> optionalServiciosEntity = oServiciosRepository.findById(Long.valueOf(RandomHelper.getRandomInt(1, 6)));
            ServiciosEntity oServiciosEntity = optionalServiciosEntity.get();
            oServiciosRealizadosEntity.setServicio(oServiciosEntity);
            oServiciosRealizadosRepository.save(oServiciosRealizadosEntity);
        }
        return cantidad;
    }
}
