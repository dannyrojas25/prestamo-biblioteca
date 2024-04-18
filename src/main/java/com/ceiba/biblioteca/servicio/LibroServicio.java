package com.ceiba.biblioteca.servicio;

import com.ceiba.biblioteca.modelo.*;
import com.ceiba.biblioteca.repositorio.LibroRepositorio;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
@Service
public class LibroServicio {
    private LibroRepositorio libroRepositorio;

    public ResponseEntity<String> guardarLibrosDesdeJSON(LibroJSON libroJSON) {
        try {
            StringBuilder mensaje = new StringBuilder();
            for (var detalle : libroJSON.getLibros()) {
                boolean libroExistente = libroRepositorio.existsByNombreAndAuthor(detalle.getNombre(), detalle.getAuthor());
                if (!libroExistente) {
                    Libro libro = Libro.builder()
                            .nombre(detalle.getNombre())
                            .author(detalle.getAuthor())
                            .foto(detalle.getFoto())
                            .build();
                    libroRepositorio.save(libro);
                } else {
                    mensaje.append("El libro '").append(detalle.getNombre()).append("' ya existe en la base de datos. No se insertará de nuevo.\n");
                }
                if (mensaje.length() > 0) {
                    return new ResponseEntity<>(mensaje.toString(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Se registraron exitosamente el/los libro(s).", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Se registró exitosamente el/los libro(s).", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error al procesar la solicitud: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
