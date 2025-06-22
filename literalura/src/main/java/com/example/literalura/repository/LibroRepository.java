package com.example.literalura.repository;

import com.example.literalura.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    List<Libro> findByIdioma(String idioma);
    boolean existsByTituloIgnoreCase(String titulo);
    Long countByIdioma(String idioma);
    List<Libro> findByIdiomaIgnoreCase(String idioma);
    Page<Libro> findAll(Pageable pageable);



}
