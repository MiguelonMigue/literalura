package com.example.literalura.principal;

import com.example.literalura.model.Autor;
import com.example.literalura.model.DatosLibro;
import com.example.literalura.model.Libro;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import com.example.literalura.service.ConsumoAPI;
import com.example.literalura.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal  {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository repository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }



    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    1 - Buscar libros
                        2 - Buscar autores
                        3 - Mostrar libros buscados
                        4 - Exhibir libros en su idioma
                        5 - Listar todos los autores
                        6 - Listar autores vivos en un año
                        0 - Salir
                    """);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibros();
                case 2 -> buscarAutores();
                case 3 -> mostrarLibrosBuscados();
                case 4 -> exhibirLibrosEnSuIdioma();
                case 5 -> listarAutores();
                case 6 -> listarAutoresVivosEnAnio();
                case 0 -> System.out.println("Cerrando la aplicación");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibros() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        String nombreLibro = teclado.nextLine();
        try {
            String json = consumoAPI.obtenerDatos(URL + nombreLibro.replace(" ", "+"));
            DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);
            System.out.println("Libro encontrado: " + datos.titulo());
            // Aquí deberías convertir DatosLibro a Libro y guardarlo
        } catch (Exception e) {
            System.out.println("Error al buscar el libro. Intenta con otro nombre.");
        }
    }

    private void buscarAutores() {
        System.out.println("Escribe el nombre del libro para ver sus autores:");
        String nombreLibro = teclado.nextLine();

        List<Libro> libros = repository.findAll();

        Optional<Libro> libroEncontrado = libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();

        if (libroEncontrado.isPresent()) {
            Libro libro = libroEncontrado.get();
            System.out.println("Autores del libro \"" + libro.getTitulo() + "\":");
            libro.getAutores().forEach(autor -> System.out.println("- " + autor.getNombre()));
        } else {
            System.out.println("No se encontró ningún libro con ese nombre.");
        }
    }


    // Los otros métodos deben seguir lógica similar
    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
        } else {
            System.out.println("Autores registrados:");
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año para consultar autores vivos:");
        int anio = teclado.nextInt();
        teclado.nextLine(); // Limpiar buffer

        List<Autor> autoresVivos = autorRepository.findAll().stream()
                .filter(a -> a.getAnioDeNacimiento() != null && a.getAnioDeNacimiento() <= anio)
                .filter(a -> a.getAnioDeFallecimiento() == null || a.getAnioDeFallecimiento() > anio)
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + ".");
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            autoresVivos.forEach(System.out::println);
        }
    }

    private void mostrarLibrosBuscados() {
        List<Libro> libros = repository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No se han buscado libros aún.");
        } else {
            System.out.println("Libros guardados:");
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        }
    }

    private void exhibirLibrosEnSuIdioma() {
        System.out.println("Escribe el código del idioma (ej: 'es', 'en'):");
        String idioma = teclado.nextLine();

        List<Libro> libros = repository.findAll().stream()
                .filter(l -> l.getIdiomas() != null && l.getIdiomas().contains(idioma))
                .toList();

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'.");
        } else {
            System.out.println("Libros en idioma '" + idioma + "':");
            libros.forEach(System.out::println);
        }
    }
}