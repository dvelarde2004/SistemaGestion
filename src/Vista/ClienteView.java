package Vista;

import Modelo.Cliente;

public class ClienteView {

    public void mostrarMensaje(String mensaje) {
        // muestra un mensaje normal
        System.out.println(mensaje);
    }

    public void mostrarError(String error) {
        // muestra un mensaje de error
        System.out.println(error);
    }

    public void mostrarDatosCliente(Cliente cliente){
        // muestra los datos de un cliente
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           DATOS DEL CLIENTE         │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ %-12s: %-25s │\n", "Nombre", cliente.getNombre() != null ? cliente.getNombre() : "No asignado");
        System.out.printf("│ %-12s: %-25s │\n", "DNI", cliente.getDni() != null ? cliente.getDni() : "No asignado");
        System.out.printf("│ %-12s: %-25s │\n", "Dirección", cliente.getDireccion() != null ? cliente.getDireccion() : "No asignado");
        System.out.printf("│ %-12s: %-25s │\n", "Población", cliente.getPoblacion() != null ? cliente.getPoblacion() : "No asignado");
        System.out.printf("│ %-12s: %-25s │\n", "Código Postal", cliente.getCodigoPostal() != null ? cliente.getCodigoPostal() : "No asignado");
        System.out.printf("│ %-12s: %-25s │\n", "Provincia", cliente.getProvincia() != null ? cliente.getProvincia() : "No asignado");
        System.out.printf("│ %-12s: %-25s │\n", "Teléfono", cliente.getTelefono() != null ? cliente.getTelefono() : "No asignado");
        System.out.println("└─────────────────────────────────────┘");
    }

    public void mostrarMenuCliente() {
        // muestra el menu de gestion del clientes
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         GESTIÓN DE CLIENTES         │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Crear cliente                    │");
        System.out.println("│ 2. Ver cliente actual               │");
        System.out.println("│ 3. Exportar a CSV                   │");
        System.out.println("│ 0. Volver al menú principal         │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print(" Selecciona una opción: ");
    }
}