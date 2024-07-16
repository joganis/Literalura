package com.aluraCurso.liralura.principal;

import com.aluraCurso.liralura.Model.*;
import com.aluraCurso.liralura.repository.AutorRepository;
import com.aluraCurso.liralura.repository.LibroRepository;
import com.aluraCurso.liralura.service.ConsumoAPI;
import com.aluraCurso.liralura.service.ConvierteDatosJson;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatosJson convierteDatosJson = new ConvierteDatosJson();
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    // Menu de opciones
    public void muestramenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println( """
         *******************************************
                  Buscar Libros
          1) Buscar libro por título 
          2) Listar libros registrados
          3) Listar autores registrados
          4) Listar autores vivos en un determinado año
          5) Listar libros por idioma
          
          0) Salir
          ********************************************     
          """);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
                leerSeleccion(opcion);
            } catch (InputMismatchException e) {

                System.out.println("opcion invalida");
                teclado.nextLine();
            }
        }
    }

    private void leerSeleccion(int opcion) {
        switch (opcion) {
            case 1 -> buscarLibroPorTitulo();
            case 2 -> listarLibrosRegistrados();
            case 3 -> listarAutoresRegistrados();
            case 4 -> listarAutoresPorYear();
            case 5 -> listarLibrosPorIdioma();
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción inválida. Por favor, ingrese un número del 0 al 5.");
        }
    }

    private void listarLibrosPorIdioma() {
        String menuIdioma = """
                Ingrese el idioma para buscar los libros: 
                es  Español
                en  Ingles
                fr  Frances 
                pt  Portugues
                """;
        System.out.println(menuIdioma);
        String idiomaBuscado = teclado.nextLine();

        CategoriaIdioma idioma = switch (idiomaBuscado) {
            case "es" -> CategoriaIdioma.fromEspanol("Español");
            case "en" -> CategoriaIdioma.fromEspanol("Ingles");
            case "fr" -> CategoriaIdioma.fromEspanol("Frances");
            case "pt" -> CategoriaIdioma.fromEspanol("Portugues");
            default -> {
                System.out.println("Entrada inválida.");
                yield null;
            }
        };

        if (idioma != null) {
            buscarPorIdioma(idioma);
        }
    }

    private void buscarPorIdioma(CategoriaIdioma idioma) {
        libros = libroRepository.findLibrosByidioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresPorYear() {
        System.out.println("Ingrese el año vivo de Autore(s) que desea buscar: ");
        try {
            int year = teclado.nextInt();
            autores = autorRepository.findAutoresByYear(year);
            if (autores.isEmpty()) {
                System.out.println("No hay autores en ese rango");
            } else {
                autores.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un año correcto");
            teclado.nextLine();
        }
    }

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarLibrosRegistrados() {
        libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private String realizarConsulta() {
        System.out.println("Escribe el nombre del libro a buscar: ");
        String nombreLibro = teclado.nextLine();
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        System.out.println("Esperando la respuesta...");
        return consumoAPI.obtenerDatosApi(url);
    }

    private void buscarLibroPorTitulo() {
        String respuesta = realizarConsulta();
        DatosConsultaAPI datosConsultaAPI = convierteDatosJson.obtenerDatos(respuesta, DatosConsultaAPI.class);

        if (datosConsultaAPI.numeroLibros() != 0) {
            DatosLibro primerLibro = datosConsultaAPI.resultado().get(0);
            final Autor[] autorLibro = {new Autor(primerLibro.autores().get(0))};

            libroRepository.findLibroBytitulo(primerLibro.titulo()).ifPresentOrElse(libroBase -> {
                System.out.println("No se puede registrar el mismo libro ");
            }, () -> {
                autorRepository.findBynombre(autorLibro[0].getNombre()).ifPresentOrElse(autorDeBase -> {
                    autorLibro[0] = autorDeBase;
                }, () -> {
                    autorRepository.save(autorLibro[0]);
                });

                Libro libro = new Libro(primerLibro);
                libro.setAutor(autorLibro[0]);
                libroRepository.save(libro);
                System.out.println(libro);
            });
        } else {
            System.out.println("Libro no encontrado...");
        }
    }

}
