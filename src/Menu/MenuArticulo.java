package Menu;

import Modelo.*;
import Controlador.*;
import Vista.*;
import Util.Utilidades;
import java.util.Scanner;
import java.util.List;

public class MenuArticulo {
    private Scanner scanner;
    private ArticuloView vista;

    public MenuArticulo(Scanner scanner) {
        this.scanner = scanner;
        this.vista = new ArticuloView();  // vista para articulos
    }

    public void ejecutar() {
        boolean volver = false;

        while (!volver) {
            Utilidades.limpiarPantalla();
            mostrarEstadisticas();
            vista.mostrarMenuArticulo();  // menu de articulos
            int opcion = scanner.nextInt();
            scanner.nextLine();  // limpia buffer

            switch (opcion) {
                case 1:
                    Utilidades.limpiarPantalla();
                    crearArticulo();  // crear articulo nuevo
                    Utilidades.pausa();
                    break;
                case 2:
                    Utilidades.limpiarPantalla();
                    listarArticulos();  // ver articulos que hay
                    Utilidades.pausa();
                    break;
                case 3:
                    Utilidades.limpiarPantalla();
                    exportarArticulos();  // exportar a CSV
                    Utilidades.pausa();
                    break;
                case 0:
                    volver = true;  // volver al menu principal
                    break;
                default:
                    Utilidades.limpiarPantalla();
                    vista.mostrarError("Opción no válida");
                    Utilidades.pausa();
            }
        }
    }

    private void mostrarEstadisticas() {
        int totalClientes = Modelo.GestorDatos.getTotalClientes();
        int totalArticulos = Modelo.GestorDatos.getTotalArticulos();
        int totalLineas = Modelo.GestorDatos.getLineasFactura().size();
        int totalFacturas = Modelo.GestorDatos.getTotalFacturas();

        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         ESTADÍSTICAS ACTUALES      │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ ✅ Clientes registrados: %-10d │\n", totalClientes);
        System.out.printf("│ ✅ Artículos en catálogo: %-9d │\n", totalArticulos);
        System.out.printf("│ ✅ Líneas de factura: %-13d │\n", totalLineas);
        System.out.printf("│ ✅ Facturas generadas: %-12d │\n", totalFacturas);
        System.out.println("└─────────────────────────────────────┘");
    }

    private void crearArticulo() {
        // pedimos los datos del articulo
        vista.mostrarFormularioCrearArticulo();  // pide nombre
        String nombre = scanner.nextLine();
        vista.mostrarFormularioPrecio();  // pide precio
        double precio = scanner.nextDouble();
        scanner.nextLine();

        // creamos el articulo y lo guardamos
        Articulo articulo = new Articulo(nombre, precio);
        ArticuloController controlador = new ArticuloController(articulo, vista);

        if (controlador.establecerDatos(nombre, precio)) {
            GestorDatos.agregarArticulo(articulo);  // lo añadimos
            Utilidades.mostrarMensaje("Artículo guardado correctamente. Total: " + GestorDatos.getTotalArticulos());
        }
    }

    private void listarArticulos() {
        List<Articulo> articulos = GestorDatos.getArticulos();
        vista.mostrarListaArticulos(articulos);  // la vista se encarga de mostrarlos
    }

    private void exportarArticulos() {
        List<Articulo> articulos = GestorDatos.getArticulos();
        if (articulos.isEmpty()) {
            vista.mostrarError("No hay artículos para exportar");  // si no hay
        } else {
            // exportamos cada articulo a CSV
            for (Articulo articulo : articulos) {
                ArticuloController controlador = new ArticuloController(articulo, vista);
                controlador.exportarArticulo();  // exporta cada articulo
            }
        }
    }
}