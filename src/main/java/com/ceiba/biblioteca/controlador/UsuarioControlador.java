package com.ceiba.biblioteca.controlador;

import com.ceiba.biblioteca.servicio.UsuarioServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/usuario/")
public class UsuarioControlador {
    private UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    @GetMapping("obtener/{username}")
    public ResponseEntity<String> obtenerUsurio(@PathVariable String username){
        return this.usuarioServicio.obtenerUsurio(username);
    }
}
