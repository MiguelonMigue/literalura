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

//        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
//        String json = consumoApi.obtenerDatos(url); // tu clase de API
//
//        if (json == null || json.isBlank()) {
//            System.out.println("❌ No se pudo obtener datos de la API.");
//            return;
//        }
//
//        Resultados resultados = conversor.desdeJson(json, Resultados.class);
//
//        if (resultados == null || resultados.getResultados().isEmpty()) {
//            System.out.println("❌ No se encontraron libros con ese título.");
//            return;
//        }
//        DatosLibro datos = resultado.getResultados().get(0);
//        if (datos.getTitulo() == null || datos.getTitulo().isBlank() ||
//                datos.getIdiomas().isEmpty() ||
//                datos.getAutores().isEmpty()) {
//            System.out.println("❌ El resultado no contiene todos los datos necesarios.");
//            return;
//        }
//
//        // Primer resultado
//
//        Libro libro = new Libro();
//        libro.setTitulo(datos.getTitulo());
//        libro.setIdioma(datos.getIdiomas().get(0)); // solo primer idioma
//        libro.setDescargas(datos.getDescargas());
//
//        DatosAutor datosAutor = datos.getAutores().get(0); // primer autor
//        Autor autor = new Autor();
//        autor.setNombre(datosAutor.getNombre());
//        autor.setNacimiento(datosAutor.getNacimiento());
//        autor.setFallecimiento(datosAutor.getFallecimiento());
//
//        libro.setAutor(autor);
//        libroRepository.save(libro); // guarda también el autor (Cascade)

        try {
            String url = "https://gutendex.com/books/?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String json = consumoApi.obtenerDatos(url);

            // Validación clave para evitar el error
            if (json == null || json.isBlank()) {
                System.out.println("❌ No se recibió respuesta de la API.");
                return;
            }

            Resultados resultado = conversor.desdeJson(json, Resultados.class);

            // 🔒 Validación para evitar NullPointerException
            if (resultado == null || resultado.getResultados() == null || resultado.getResultados().isEmpty()) {
                System.out.println("❌ No se encontraron libros con ese título.");
                return;
            }

            DatosLibro datos = resultado.getResultados().get(0);

            // Aquí continúa tu lógica para guardar el libro

        } catch (Exception e) {
            System.out.println("❌ Error al buscar el libro: " + e.getMessage());
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
