package com.example.literalura.principal;

import com.example.literalura.model.Libro;
import com.example.literalura.repository.LibroRepository;
import com.example.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Scanner;
@Component

public class Principal {

    @Autowired
    private final LibroService libroService;
    private LibroRepository libroRepository;
    @Autowired
    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    private Scanner scanner = new Scanner(System.in);

    public static String normalizarTexto(String input) {
        if (input == null) return "";

        // Convertir a minúsculas
        String texto = input.toLowerCase().trim();

        // Eliminar tildes y acentos
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Reemplazar múltiples espacios por uno solo
        texto = texto.replaceAll("\\s{2,}", " ");

        return texto;
    }


    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("""
                1 - Buscar libro por título
                2 - Listar todos los libros
                3 - Listar libros por idioma
                4 - Listar autores
                5 - Listar autores vivos en un año
                6 - Cantidad de libros por idioma
                0 - Salir
            """);


                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        System.out.println("Ingrese título del libro:");
                        String tituloIngresado = scanner.nextLine();
                        String titulo = normalizarTexto(tituloIngresado);


                        if(titulo.isEmpty()){
                            System.out.println("El título no puede estar vacío");
                            break;

                        }
                        libroService.buscarYGuardarLibro(titulo);
                    }

                    case 2 -> libroService.listarLibros();
                    case 3 -> {
                        System.out.println("Ingrese el idioma (ej: en, es, fr):");
                        String idioma = normalizarTexto(scanner.nextLine());
                        libroService.listarLibrosPorIdioma(idioma);
                    }
                    case 4 -> libroService.listarAutores();
                    case 5 -> {
                        System.out.println("Ingrese el año:");
                        int año = Integer.parseInt(scanner.nextLine());
                        libroService.listarAutoresVivosEn(año);
                    }
                    case 6 -> libroService.contarLibrosPorIdioma();
                    case 0 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción inválida");
                }

                opcion = -1;

        } while (opcion != 0);
        System.out.println("Ingrese un año (ej: 1990):");
        try {
            int año = Integer.parseInt(scanner.nextLine());
            libroService.listarAutoresVivosEn(año);
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Intente con un número.");
        }

        Pageable pageable = PageRequest.of(0, 5); // página 0, tamaño 5
        Page<Libro> pagina = libroRepository.findAll(pageable);
        pagina.getContent().forEach(System.out::println);
    }
}
