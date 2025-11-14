package Menu;

import Modelo.Factura;
import Modelo.*;
import Controlador.*;
import Vista.*;
import Util.Utilidades;
import java.util.Scanner;
import java.util.List;

public class MenuFactura {
    private Scanner scanner;
    private FacturaView vista;

    public MenuFactura(Scanner scanner) {
        this.scanner = scanner;
        this.vista = new FacturaView();  // vista para facturas
    }

    public void ejecutar() {
        boolean volver = false;

        while (!volver) {
            Utilidades.limpiarPantalla();
            mostrarEstadisticas();
            mostrarMenuPrincipalFacturas();  // menu principal de facturas
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Utilidades.limpiarPantalla();
                    crearFactura();  // crear factura nueva
                    break;
                case 2:
                    Utilidades.limpiarPantalla();
                    seleccionarFacturaExistente();  // trabajar con factura existente
                    break;
                case 3:
                    Utilidades.limpiarPantalla();
                    verArticulosDisponibles();  // ver que articulos hay
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

    private void mostrarMenuPrincipalFacturas() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         GESTIÓN DE FACTURAS         │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Crear factura                    │");
        System.out.println("│ 2. Seleccionar factura existente    │");
        System.out.println("│ 3. Ver artículos disponibles        │");
        System.out.println("│ 0. Volver al menú principal         │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("🧾 Seleccione una opción: ");
    }

    private void crearFactura() {
        List<Cliente> clientes = GestorDatos.getClientes();
        if (clientes.isEmpty()) {
            // si no hay clientes, no se puede crear factura
            Utilidades.limpiarPantalla();
            vista.mostrarError("Primero debe crear al menos un cliente");
            Utilidades.pausa();
            return;
        }

        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         CREAR NUEVA FACTURA         │");
        System.out.println("├─────────────────────────────────────┤");

        // seleccionamos el cliente para la factura
        System.out.println("│          SELECCIONAR CLIENTE        │");
        System.out.println("├─────────────────────────────────────┤");
        for (int i = 0; i < clientes.size(); i++) {
            String nombreCliente = clientes.get(i).getNombre();
            if (nombreCliente.length() > 30) {
                nombreCliente = nombreCliente.substring(0, 27) + "...";  // si es muy largo lo cortamos
            }
            System.out.printf("│ %-2d. %-30s │\n", i + 1, nombreCliente);
        }
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│ Seleccione cliente: ");
        int clienteIndex = scanner.nextInt();
        scanner.nextLine();

        if (clienteIndex < 1 || clienteIndex > clientes.size()) {
            Utilidades.limpiarPantalla();
            vista.mostrarError("Cliente no válido");
            Utilidades.pausa();
            return;
        }

        Cliente clienteSeleccionado = clientes.get(clienteIndex - 1);

        System.out.print("│ Fecha (AAAA-MM-DD): ");
        String fecha = scanner.nextLine();

        // creamos la factura y vamos a gestionarla
        Factura factura = new Factura(fecha, clienteSeleccionado);
        GestorDatos.agregarFactura(factura);

        Utilidades.mostrarMensaje("✅ Factura " + factura.getId() + " creada correctamente");
        gestionarFacturaEspecifica(factura);  // vamos al menu de esa factura
    }

    private void seleccionarFacturaExistente() {
        List<Factura> facturas = GestorDatos.getFacturas();
        if (facturas.isEmpty()) {
            Utilidades.limpiarPantalla();
            vista.mostrarError("No hay facturas creadas");
            Utilidades.pausa();
            return;
        }

        // mostramos las facturas que hay
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│      FACTURAS DISPONIBLES           │");
        System.out.println("├─────────────────────────────────────┤");
        for (int i = 0; i < facturas.size(); i++) {
            Factura factura = facturas.get(i);
            String nombreCliente = factura.getCliente().getNombre();
            if (nombreCliente.length() > 20) {
                nombreCliente = nombreCliente.substring(0, 17) + "...";
            }
            System.out.printf("│ %-2d. %-8s - %-20s │\n",
                    i + 1, factura.getId(), nombreCliente);
        }
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 0. Volver                           │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("✅ Seleccione factura: ");
        int facturaIndex = scanner.nextInt();
        scanner.nextLine();

        if (facturaIndex == 0) {
            return;  // si elige 0, volvemos
        }

        if (facturaIndex < 1 || facturaIndex > facturas.size()) {
            Utilidades.limpiarPantalla();
            vista.mostrarError("Factura no válida");
            Utilidades.pausa();
            return;
        }

        Factura facturaSeleccionada = facturas.get(facturaIndex - 1);
        gestionarFacturaEspecifica(facturaSeleccionada);  // vamos al menu de esa factura
    }

    private void gestionarFacturaEspecifica(Factura factura) {
        boolean volver = false;
        FacturaController controlador = new FacturaController(factura, vista);

        while (!volver) {
            Utilidades.limpiarPantalla();
            System.out.println("┌─────────────────────────────────────┐");
            System.out.printf("│    GESTIÓN DE FACTURA %-12s │\n", factura.getId());
            System.out.printf("│    Cliente: %-23s │\n",
                    factura.getCliente().getNombre().length() > 23 ?
                            factura.getCliente().getNombre().substring(0, 20) + "..." :
                            factura.getCliente().getNombre());
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│ 1. Añadir línea                     │");
            System.out.println("│ 2. Ver líneas actuales              │");
            System.out.println("│ 3. Gestionar IVA                    │");
            System.out.println("│ 4. Precio total                     │");
            System.out.println("│ 5. Ver factura completa             │");
            System.out.println("│ 6. Exportar factura                 │");
            System.out.println("│ 0. Volver a gestión de facturas     │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("✅ Seleccione opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Utilidades.limpiarPantalla();
                    añadirLineaAFacturaExistente(factura);  // añadir linea a la factura
                    break;
                case 2:
                    Utilidades.limpiarPantalla();
                    controlador.mostrarFactura();  // ver lineas que tiene
                    Utilidades.pausa();
                    break;
                case 3:
                    Utilidades.limpiarPantalla();
                    gestionarIVAFactura(factura);  // cambiar el IVA
                    break;
                case 4:
                    Utilidades.limpiarPantalla();
                    controlador.mostrarTotales();  // ver totales
                    Utilidades.pausa();
                    break;
                case 5:
                    Utilidades.limpiarPantalla();
                    controlador.mostrarFactura();  // ver factura completa
                    Utilidades.pausa();
                    break;
                case 6:
                    Utilidades.limpiarPantalla();
                    controlador.exportarFactura();  // exportar a CSV
                    Utilidades.pausa();
                    break;
                case 0:
                    volver = true;  // volver al menu de facturas
                    break;
                default:
                    Utilidades.limpiarPantalla();
                    vista.mostrarError("Opción no válida");
                    Utilidades.pausa();
            }
        }
    }

    private void añadirLineaAFacturaExistente(Factura factura) {
        FacturaController controlador = new FacturaController(factura, vista);

        // verificamos que haya articulos disponibles
        List<Articulo> articulos = GestorDatos.getArticulos();
        if (articulos.isEmpty()) {
            System.out.println("┌─────────────────────────────────────┐");
            System.out.println("│     ❌ NO HAY ARTÍCULOS            │");
            System.out.println("│  Primero crea artículos en el    │");
            System.out.println("│  menú Gestión de Artículos       │");
            System.out.println("└─────────────────────────────────────┘");
            Utilidades.pausa();
            return;
        }

        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│       AÑADIR LÍNEA A " + factura.getId() + "    │");
        System.out.println("├─────────────────────────────────────┤");

        // mostramos los articulos disponibles
        System.out.println("│      ARTÍCULOS DISPONIBLES         │");
        System.out.println("├─────────────────────────────────────┤");
        for (int i = 0; i < articulos.size(); i++) {
            Articulo art = articulos.get(i);
            String nombre = art.getNombre();
            if (nombre.length() > 25) {
                nombre = nombre.substring(0, 22) + "...";
            }
            System.out.printf("│ %-2d. %-25s %6.2f€ │\n",
                    i + 1, nombre, art.getPrecio());
        }
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│ Seleccione artículo: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (seleccion < 1 || seleccion > articulos.size()) {
            Utilidades.limpiarPantalla();
            vista.mostrarError("❌ Selección no válida");
            Utilidades.pausa();
            return;
        }

        // articulo seleccionado
        Articulo articuloSeleccionado = articulos.get(seleccion - 1);

        // pedimos la cantidad
        System.out.print("│ Cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("└─────────────────────────────────────┘");

        // creamos la linea de factura y la añadimos
        LineaFactura linea = new LineaFactura(cantidad, articuloSeleccionado.getNombre(), articuloSeleccionado.getPrecio());
        controlador.añadirLinea(linea);
        Utilidades.pausa();
    }

    private void gestionarIVAFactura(Factura factura) {
        FacturaController controlador = new FacturaController(factura, vista);

        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│        CAMBIAR IVA FACTURA         │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. IVA por defecto (21%)           │");
        System.out.println("│ 2. IVA personalizado               │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Opción: ");
        int opcionIVA = scanner.nextInt();
        scanner.nextLine();

        if (opcionIVA == 1) {
            controlador.aplicarIvaPorDefecto();  // 21% por defecto
        } else if (opcionIVA == 2) {
            System.out.print("Nuevo IVA (%): ");
            int nuevoIVA = scanner.nextInt();
            scanner.nextLine();
            controlador.cambiarIva(nuevoIVA);  // IVA personalizado
        } else {
            vista.mostrarError("Opción no válida");
        }
        Utilidades.pausa();
    }

    private void verArticulosDisponibles() {
        List<Articulo> articulos = GestorDatos.getArticulos();
        if (articulos.isEmpty()) {
            System.out.println("┌─────────────────────────────────────┐");
            System.out.println("│     No hay artículos disponibles    │");
            System.out.println("└─────────────────────────────────────┘");
        } else {
            System.out.println("┌─────────────────────────────────────┐");
            System.out.println("│        ARTÍCULOS DISPONIBLES        │");
            System.out.println("├─────────────────────────────────────┤");
            for (int i = 0; i < articulos.size(); i++) {
                Articulo art = articulos.get(i);
                String nombre = art.getNombre();
                if (nombre.length() > 25) {
                    nombre = nombre.substring(0, 22) + "...";
                }
                System.out.printf("│ %-2d. %-25s %6.2f€ │\n",
                        i + 1, nombre, art.getPrecio());
            }
            System.out.println("└─────────────────────────────────────┘");
        }
    }
}