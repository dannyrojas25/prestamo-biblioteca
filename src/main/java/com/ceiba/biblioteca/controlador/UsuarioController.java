package com.ceiba.biblioteca.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class UsuarioController {
    @PostMapping(value = "demo")
    public String welcome(){
        return "Welcome from secure endpoint";
    }

}
