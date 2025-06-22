package com.example.literalura.service;

import com.example.literalura.dto.LibroDTO;
import com.example.literalura.model.*;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private ConsumoAPI consumoApi;
    private Resultados resultado;
    private ConvierteDatos conversor;

    @Autowired
    public LibroService(
            LibroRepository libroRepository,
            AutorRepository autorRepository,
            ConsumoAPI consumoApi,
            ConvierteDatos conversor) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }


    public void buscarYGuardarLibro(String titulo) {
        try {
            String url = "https://gutendex.com/books/?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String json = consumoApi.obtenerDatos(url);

            // Validación clave para evitar error
            if (json == null || json.isBlank()) {
                System.out.println("✘ No se recibió respuesta de la API.");
                return;
            }

            Resultados resultado = conversor.desdeJson(json, Resultados.class);

            if (resultado == null || resultado.getResultados() == null || resultado.getResultados().isEmpty()) {
                System.out.println("✘ No se encontraron libros con ese título.");
                return;
            }

            LibroDTO libroDTO = resultado.getResultados().get(0);

            // Verificar si ya está guardado
            if (libroRepository.existsByTitulo(libroDTO.titulo())) {
                System.out.println("⚠ El libro ya existe en la base de datos.");
                return;
            }

            // Crear autor desde DTO
            DatosAutor datosAutor = libroDTO.autores();
            Autor autor = new Autor(datosAutor);

            // Verificar si ya existe el autor
            Autor autorFinal = autorRepository.findByNombre(autor.getNombre())
                    .orElseGet(() -> autorRepository.save(autor));

            // Crear y guardar libro con autor
            Libro libro = new Libro(libroDTO);
            libro.setAutor(autorFinal);
            libroRepository.save(libro);

            System.out.println("✅ Libro guardado: " + libro.getTitulo());

        } catch (Exception e) {
            System.out.println("✘ Error al buscar o guardar el libro: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void contarLibrosPorIdioma() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el idioma (ej: en, es, fr):");
        String idioma = scanner.nextLine();


        Long cantidad = libroRepository.countByIdioma(idioma);
        System.out.println("Hay " + cantidad + " libros en el idioma: " + idioma);

    }

    public void listarAutoresVivosEn(int año) {
        List<Autor> autoresVivos = autorRepository
                .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(año, año);

        if (autoresVivos.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + año);
        } else {
            System.out.println("Autores vivos en el año " + año + ":");
            autoresVivos.forEach(System.out::println);
        }
    }

    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("Listado de libros:");
            libros.forEach(System.out::println);
        }
    }
    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("Listado de autores:");
            autores.forEach(System.out::println);
            }
        }

    public void listarLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("Libros en idioma " + idioma + ":");
            libros.forEach(System.out::println);
        }
    }







}
