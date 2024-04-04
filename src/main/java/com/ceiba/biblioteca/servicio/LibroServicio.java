package com.ceiba.biblioteca.servicio;

import com.ceiba.biblioteca.modelo.*;
import com.ceiba.biblioteca.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
@Service
public class LibroServicio {
    @Autowired
    LibroRepositorio libroRepositorio;

    public ResponseEntity<String> guardarLibrosDesdeJSON(LibroJSON libroJSON) {
        try {
            for (var detalle : libroJSON.getLibros()) {
                Libro libro = Libro.builder()
                        .nombre(detalle.getNombre())
                        .author(detalle.getAuthor())
                        .foto(detalle.getFoto())
                        .build();
                libroRepositorio.save(libro);
            }
            return ResponseEntity.ok("Se registró exitosamente el/los libro(s).");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
