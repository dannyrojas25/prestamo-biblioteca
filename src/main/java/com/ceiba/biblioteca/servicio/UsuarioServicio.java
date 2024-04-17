package com.ceiba.biblioteca.servicio;

import com.ceiba.biblioteca.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    private UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public ResponseEntity<String> obtenerUsurio(String username){
        usuarioRepositorio.findByUsername(username);
        return new ResponseEntity<>("Se obtuvo con exito el usuario", HttpStatus.OK);
    }
}
