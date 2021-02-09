package net.ausiasmarch.barber.service;

import java.time.ZoneId;
import java.util.Optional;
import net.ausiasmarch.barber.entity.CitaEntity;
import net.ausiasmarch.barber.entity.TipousuarioEntity;
import net.ausiasmarch.barber.entity.UsuarioEntity;
import net.ausiasmarch.barber.helper.RandomHelper;
import net.ausiasmarch.barber.repository.CitaRepository;
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
            oUsuarioEntity.setPassword("da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04");
            oUsuarioEntity.setEmail(nombre + apellido1.charAt(0) + "@ausiasmarch.net");
            TipousuarioEntity oTipousuarioEntity = new TipousuarioEntity();
            oTipousuarioEntity.setId(2L);
            oUsuarioEntity.setTipousuario(oTipousuarioEntity);
            /*oUsuarioEntity.setToken("");
            oUsuarioEntity.setValidado(true);
            oUsuarioEntity.setActivo(true);*/

            oUsuarioRepository.save(oUsuarioEntity);
        }
        return cantidad;
    }

    public Long citaFill(Long cantidad) {

        for (int i = 1; i <= cantidad; i++) {
            CitaEntity oCitaEntity = new CitaEntity();
            oCitaEntity.setFecha(RandomHelper.getRadomDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());          
            Optional<UsuarioEntity> optionalUsuarioEntity = oUsuarioRepository.findById(Long.valueOf(RandomHelper.getRandomInt(1, 100)));
            UsuarioEntity oUsuarioEntity = optionalUsuarioEntity.get();                          
            oCitaEntity.setUsuario(oUsuarioEntity);
            oCitaRepository.save(oCitaEntity);
        }
        return cantidad;

    }
}
