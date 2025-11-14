package Menu;

import Modelo.GestorArchivos;
import Modelo.GestorDatos;
import Modelo.Cliente;
import Modelo.Articulo;
import Modelo.LineaFactura;
import Modelo.Factura;
import Util.Utilidades;
import java.util.Scanner;
import java.util.List;

public class MenuImportacionCSV {
    private Scanner scanner;

    public MenuImportacionCSV(Scanner scanner) {
        this.scanner = scanner;  // solo necesitamos el scanner
    }

    public void ejecutar() {
        boolean volver = false;

        while (!volver) {
            Utilidades.limpiarPantalla();
            mostrarEstadisticas();
            mostrarMenu();  // menu de importacion
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Utilidades.limpiarPantalla();
                    importarClientes();  // importar clientes desde CSV
                    break;
                case 2:
                    Utilidades.limpiarPantalla();
                    importarArticulos();  // importar articulos desde CSV
                    break;
                case 3:
                    Utilidades.limpiarPantalla();
                    importarLineasFactura();  // importar lineas desde CSV
                    break;
                case 4:
                    Utilidades.limpiarPantalla();
                    importarFacturas();  // importar facturas desde CSV
                    break;
                case 0:
                    volver = true;  // volver al menu principal
                    break;
                default:
                    Utilidades.limpiarPantalla();
                    Utilidades.mostrarError("Opción no válida");
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

    private void mostrarMenu() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│       IMPORTAR DATOS DESDE CSV      │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Importar Clientes                │");
        System.out.println("│ 2. Importar Artículos               │");
        System.out.println("│ 3. Importar Líneas de Factura       │");
        System.out.println("│ 4. Importar Facturas                │");
        System.out.println("│ 0. Volver al menú principal         │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("✅ Seleccione una opción: ");
    }

    private void importarClientes() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│        IMPORTAR CLIENTES CSV        │");
        System.out.println("├─────────────────────────────────────┤");

        List<String> archivos = GestorArchivos.listarArchivosImportacion("cliente");
        if (archivos.isEmpty()) {
            System.out.println("│  No hay archivos en importaciones_csv  │");
            System.out.println("│      /base_cliente/                    │");
            System.out.println("└─────────────────────────────────────┘");
            Utilidades.pausa();
            return;
        }

        System.out.println("│      Archivos disponibles:           │");
        System.out.println("├─────────────────────────────────────┤");
        for (String archivo : archivos) {
            System.out.printf("│ %-35s │\n", archivo);
        }
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│ Ingrese ID a importar (000 para todos): ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("└─────────────────────────────────────┘");

        if (id == 0) {
            // importar todos los archivos
            int importados = 0;
            for (String archivo : archivos) {
                int archivoId = Integer.parseInt(archivo.substring(0, 3));
                importados += procesarImportacionClientes(archivoId);
            }
            Utilidades.mostrarMensaje(importados + " clientes importados correctamente");
        } else {
            procesarImportacionClientes(id);  // importar solo uno
        }
        Utilidades.pausa();
    }

    private int procesarImportacionClientes(int id) {
        List<String> lineas = GestorArchivos.importarCSV("cliente", id);
        int clientesImportados = 0;

        for (String linea : lineas) {
            try {
                String[] datos = linea.split(";");
                if (datos.length >= 7) {
                    // creamos cliente con los datos del CSV
                    Cliente cliente = new Cliente();
                    cliente.setNombre(datos[0]);
                    cliente.setDni(datos[1]);
                    cliente.setDireccion(datos[2]);
                    cliente.setPoblacion(datos[3]);
                    cliente.setCodigoPostal(datos[4]);
                    cliente.setProvincia(datos[5]);
                    cliente.setTelefono(datos[6]);

                    GestorDatos.agregarCliente(cliente);
                    clientesImportados++;
                }
            } catch (Exception e) {
                Utilidades.mostrarError("Error procesando línea: " + e.getMessage());
            }
        }

        if (clientesImportados > 0) {
            Utilidades.mostrarMensaje(clientesImportados + " clientes importados del archivo " + id);
        }
        return clientesImportados;
    }

    private void importarArticulos() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│       IMPORTAR ARTÍCULOS CSV        │");
        System.out.println("├─────────────────────────────────────┤");

        List<String> archivos = GestorArchivos.listarArchivosImportacion("articulo");
        if (archivos.isEmpty()) {
            System.out.println("│  No hay archivos en importaciones_csv  │");
            System.out.println("│      /base_articulo/                   │");
            System.out.println("└─────────────────────────────────────┘");
            Utilidades.pausa();
            return;
        }

        System.out.println("│      Archivos disponibles:           │");
        System.out.println("├─────────────────────────────────────┤");
        for (String archivo : archivos) {
            System.out.printf("│ %-35s │\n", archivo);
        }
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│ Ingrese ID a importar (000 para todos): ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("└─────────────────────────────────────┘");

        if (id == 0) {
            int importados = 0;
            for (String archivo : archivos) {
                int archivoId = Integer.parseInt(archivo.substring(0, 3));
                importados += procesarImportacionArticulos(archivoId);
            }
            Utilidades.mostrarMensaje(importados + " artículos importados correctamente");
        } else {
            procesarImportacionArticulos(id);
        }
        Utilidades.pausa();
    }

    private int procesarImportacionArticulos(int id) {
        List<String> lineas = GestorArchivos.importarCSV("articulo", id);
        int articulosImportados = 0;

        for (String linea : lineas) {
            try {
                String[] datos = linea.split(";");
                if (datos.length >= 2) {
                    String nombre = datos[0];
                    double precio = Double.parseDouble(datos[1]);

                    // creamos articulo con los datos del CSV
                    Articulo articulo = new Articulo(nombre, precio);
                    GestorDatos.agregarArticulo(articulo);
                    articulosImportados++;
                }
            } catch (Exception e) {
                Utilidades.mostrarError("Error procesando línea: " + e.getMessage());
            }
        }

        if (articulosImportados > 0) {
            Utilidades.mostrarMensaje(articulosImportados + " artículos importados del archivo " + id);
        }
        return articulosImportados;
    }

    private void importarLineasFactura() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│    IMPORTAR LÍNEAS FACTURA CSV      │");
        System.out.println("├─────────────────────────────────────┤");

        List<String> archivos = GestorArchivos.listarArchivosImportacion("linea");
        if (archivos.isEmpty()) {
            System.out.println("│  No hay archivos en importaciones_csv  │");
            System.out.println("│      /base_linea/                      │");
            System.out.println("└─────────────────────────────────────┘");
            Utilidades.pausa();
            return;
        }

        System.out.println("│      Archivos disponibles:           │");
        System.out.println("├─────────────────────────────────────┤");
        for (String archivo : archivos) {
            System.out.printf("│ %-35s │\n", archivo);
        }
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│ Ingrese ID a importar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("└─────────────────────────────────────┘");

        procesarImportacionLineas(id);
        Utilidades.pausa();
    }

    private void procesarImportacionLineas(int id) {
        List<String> lineas = GestorArchivos.importarCSV("linea", id);
        int lineasImportadas = 0;

        for (String linea : lineas) {
            try {
                String[] datos = linea.split(";");
                if (datos.length >= 3) {
                    int cantidad = Integer.parseInt(datos[0]);
                    String nombreArticulo = datos[1];
                    double precioUnitario = Double.parseDouble(datos[2]);

                    // creamos linea de factura con los datos del CSV
                    LineaFactura lineaFactura = new LineaFactura(cantidad, nombreArticulo, precioUnitario);
                    GestorDatos.agregarLineaFactura(lineaFactura);
                    lineasImportadas++;
                }
            } catch (Exception e) {
                Utilidades.mostrarError("Error procesando línea: " + e.getMessage());
            }
        }

        if (lineasImportadas > 0) {
            Utilidades.mostrarMensaje(lineasImportadas + " líneas de factura importadas del archivo " + id);
        }
    }

    private void importarFacturas() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│        IMPORTAR FACTURAS CSV        │");
        System.out.println("├─────────────────────────────────────┤");

        List<String> archivos = GestorArchivos.listarArchivosImportacion("factura");
        if (archivos.isEmpty()) {
            System.out.println("│  No hay archivos en importaciones_csv  │");
            System.out.println("│      /base_factura/                    │");
            System.out.println("└─────────────────────────────────────┘");
        } else {
            System.out.println("│   Funcionalidad en desarrollo...    │");
        }
        Utilidades.pausa();
    }
}