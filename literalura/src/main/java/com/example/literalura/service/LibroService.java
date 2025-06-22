package com.example.literalura.service;

import com.example.literalura.dto.LibroDTO;
import com.example.literalura.model.Libro;
import com.example.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public List<LibroDTO> buscarLibros() {
        return convierteDatos(libroRepository.findAll());
    }

    public List<LibroDTO> buscarAutores() {
        return convierteDatos(libroRepository.findByAutorIgnoreCase());
    }



//    public List<LibroDTO> listarAutoresVivosEnAnio() {
//        return convierteDatos(libroRepository.);
//    }

    public List<LibroDTO> mostrarLibrosBuscados() {
        return convierteDatos(libroRepository.findAll());
    }

    public List<LibroDTO> exhibirLibrosEnSuIdioma() {
        return convierteDatos(libroRepository.findAll());
    }

   public List<LibroDTO> convierteDatos(List<Libro> libros) {
        return libros.stream()
                .map(l -> new LibroDTO(
                        l.getId(),
                        l.getTitulo(),
                        l.getAutores(),
                        l.getNumeroDescargas(),
                        l.getIdiomas() // asegurarte que `getIdiomas()` exista y devuelva el tipo correcto
                ))
                .collect(Collectors.toList());
    }
}
