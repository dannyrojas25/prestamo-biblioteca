package com.ceiba.biblioteca.modelo;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String author;
    private String foto;
}
