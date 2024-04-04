package com.ceiba.biblioteca.modelo;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class LibroJSON {
    private String status;
    private List<Libro> libros;
}
