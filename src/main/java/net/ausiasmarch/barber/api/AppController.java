package net.ausiasmarch.barber.api;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AppController {

    @Autowired
    HttpSession oHttpSession;
   
    @GetMapping("/")
    public ResponseEntity<String> info() {
        return new ResponseEntity<String>("Bienvenido a Barber Web", HttpStatus.OK);
    }
   
}
