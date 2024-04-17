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
            return new ResponseEntity<>("Se registró exitosamente el/los libro(s).", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error al procesar la solicitud: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
