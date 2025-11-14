package Menu;

import Modelo.*;
import Controlador.*;
import Vista.*;
import Util.Utilidades;
import java.util.Scanner;
import java.util.List;

public class MenuCliente {
    private Scanner scanner;
    private ClienteView vista;

    public MenuCliente(Scanner scanner) {
        this.scanner = scanner;
        this.vista = new ClienteView();  // vista para mostrar cosas de clientes
    }

    public void ejecutar() {
        boolean volver = false;  // controla cuando volver al menu principal

        while (!volver) {
            // cada vez que entramos al menu, limpiamos y mostramos
            Utilidades.limpiarPantalla();
            mostrarEstadisticas();  // muestra los numeros actuales
            vista.mostrarMenuCliente();  // muestra las opciones de clientes
            int opcion = scanner.nextInt();
            scanner.nextLine();  // esto es para limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    Utilidades.limpiarPantalla();
                    crearCliente();  // crear nuevo cliente
                    Utilidades.pausa();  // pausa para que vea el resultado
                    break;
                case 2:
                    Utilidades.limpiarPantalla();
                    listarClientes();  // ver clientes que hay
                    // pausa ya esta dentro de listarClientes
                    break;
                case 3:
                    Utilidades.limpiarPantalla();
                    exportarClientes();  // exportar a CSV
                    Utilidades.pausa();
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
        // sacamos los numeros actuales del sistema
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

    private void crearCliente() {
        // pedimos todos los datos del cliente uno por uno
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         CREAR NUEVO CLIENTE         │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│ Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("│ DNI: ");
        String dni = scanner.nextLine();
        System.out.print("│ Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("│ Población: ");
        String poblacion = scanner.nextLine();
        System.out.print("│ Código Postal: ");
        String cp = scanner.nextLine();
        System.out.print("│ Provincia: ");
        String provincia = scanner.nextLine();
        System.out.print("│ Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.println("└─────────────────────────────────────┘");

        // creamos el cliente y lo guardamos
        Cliente cliente = new Cliente();
        ClienteController controlador = new ClienteController(cliente, vista);

        if (controlador.establecerDatosCliente(nombre, dni, direccion, poblacion, cp, provincia, telefono)) {
            GestorDatos.agregarCliente(cliente);  // lo añadimos a la lista
            Utilidades.mostrarMensaje("Cliente guardado correctamente. Total: " + GestorDatos.getTotalClientes());
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = GestorDatos.getClientes();
        if (clientes.isEmpty()) {
            Utilidades.mostrarError("No hay clientes guardados");  // si no hay clientes
        } else {
            // mostramos la lista de clientes que hay
            System.out.println("┌─────────────────────────────────────┐");
            System.out.println("│       CLIENTES GUARDADOS (" + clientes.size() + ")      │");
            System.out.println("├─────────────────────────────────────┤");
            for (int i = 0; i < clientes.size(); i++) {
                Cliente cliente = clientes.get(i);
                System.out.printf("│ %-2d. %-30s │\n", i + 1, cliente.getNombre());
            }
            System.out.println("└─────────────────────────────────────┘");

            // preguntamos si quiere ver detalles de alguno
            System.out.print("Ver detalles (0 para volver): ");
            int seleccion = scanner.nextInt();
            scanner.nextLine();

            if (seleccion > 0 && seleccion <= clientes.size()) {
                Utilidades.limpiarPantalla();
                Cliente clienteSeleccionado = clientes.get(seleccion - 1);
                ClienteController controlador = new ClienteController(clienteSeleccionado, vista);
                controlador.mostrarDatosCliente();  // muestra los datos del cliente seleccionado
            }
        }
        Utilidades.pausa();  // pausa al final
    }

    private void exportarClientes() {
        List<Cliente> clientes = GestorDatos.getClientes();
        if (clientes.isEmpty()) {
            Utilidades.mostrarError("No hay clientes para exportar");  // si no hay
        } else {
            // exportamos cada cliente a CSV
            System.out.println("┌─────────────────────────────────────┐");
            System.out.println("│       EXPORTANDO CLIENTES          │");
            System.out.println("├─────────────────────────────────────┤");
            for (Cliente cliente : clientes) {
                ClienteController controlador = new ClienteController(cliente, vista);
                controlador.exportarClienteCSV();  // exporta cada cliente
            }
        }
    }
}