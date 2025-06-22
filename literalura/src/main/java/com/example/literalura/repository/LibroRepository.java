package com.example.literalura.repository;

import com.example.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT l FROM Libro l JOIN l.autores WHERE a.nombre ILIKE %:nombreAutor%")
    List<Libro>findByAutorIgnoreCase();
    List<Libro>findNumeroDescargas(Integer numeroDescargas);
    List<Libro> findByIdiomaIgnoreCase(List<String> idioma);




}
