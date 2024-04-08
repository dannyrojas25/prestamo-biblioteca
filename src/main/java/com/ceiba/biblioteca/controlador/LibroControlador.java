package com.ceiba.biblioteca.controlador;

import com.ceiba.biblioteca.modelo.LibroJSON;
import com.ceiba.biblioteca.servicio.LibroServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class LibroControlador {

    private final LibroServicio libroServicio;

    @PostMapping(value = "insertarLibro")
    public ResponseEntity<?> insertarLibros(@RequestBody LibroJSON libroJSON) {
        return ResponseEntity.ok(libroServicio.guardarLibrosDesdeJSON(libroJSON));
    }

}
