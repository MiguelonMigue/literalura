package com.example.literalura.service;

import com.example.literalura.dto.*;
import com.example.literalura.model.Resultados;
import com.example.literalura.model.*;
import com.example.literalura.repository.*;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoApi;
    private final ConvierteDatos conversor;
    private Libro libro;
    private Autor autor;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository,
                        ConsumoAPI consumoApi, ConvierteDatos conversor) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }

    public void buscarYGuardarLibro(String titulo) {
        try {
            String url = "https://gutendex.com/books/?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String json = consumoApi.obtenerDatos(url);

            Resultados resultados = conversor.desdeJson(json, Resultados.class);
            LibroDTO libroDTO = resultados.resultados().get(0);

            if (libroRepository.existsByTitulo(libroDTO.titulo())) {
                System.out.println("⚠ Libro ya registrado");
                return;
            }

            Set<Autor> autores = libroDTO.autores().stream()
                    .map(a -> autorRepository.findByNombre(a.nombre())
                            .orElseGet(() -> autorRepository.save(
                                    new Autor(a.nombre(), a.nacimiento(), a.fallecimiento()))))
                    .collect(Collectors.toSet());

            Libro libro = new Libro(libroDTO.titulo(),libroDTO.idiomas(),libroDTO.descargas(),libroDTO.autores());
            libro.setAutores(autores);

            libroRepository.save(libro);
            System.out.println("✅ Libro guardado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listarLibros(String titulo) {
        buscarYGuardarLibro(titulo);

    }

    public void listarLibrosPorIdioma(String idioma) {
        buscarYGuardarLibro(libro.getIdioma());

    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }


    public List<Autor> listarAutoresVivosEn(int anio) {
        return autorRepository.findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(autor.getNacimiento(),autor.getFallecimiento());
    }

    public void contarLibrosPorIdioma() {

    }
}
