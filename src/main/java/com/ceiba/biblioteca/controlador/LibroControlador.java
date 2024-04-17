package com.ceiba.biblioteca.controlador;

import com.ceiba.biblioteca.modelo.LibroJSON;
import com.ceiba.biblioteca.servicio.LibroServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/api/libro/")
public class LibroControlador {

    private final LibroServicio libroServicio;

    public LibroControlador(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
    }

    @PostMapping(value = "insertar")
    public ResponseEntity<String> insertarLibros(@RequestBody LibroJSON libroJSON) {
        return this.libroServicio.guardarLibrosDesdeJSON(libroJSON);
    }

}
