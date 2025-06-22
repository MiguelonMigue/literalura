package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import com.example.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(int nacimiento, int fallecimiento);

    Optional<Autor> findByNombre(String nombre);

}
