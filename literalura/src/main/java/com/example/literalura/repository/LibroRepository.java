package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import com.example.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);
    boolean existsByGutendexId(Integer id);
    List<Libro> findByLanguagesContainingIgnoreCase(String idioma);
