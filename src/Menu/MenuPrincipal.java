package Menu;

import Vista.MainView;
import Util.Utilidades;
import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;
    private MainView vista;

    public MenuPrincipal(Scanner scanner, MainView vista) {
        this.scanner = scanner;  // para leer lo que escribe el usuario
        this.vista = vista;      // para mostrar las pantallas
    }

    public int mostrar() {
        // limpia la pantalla y muestra el menu principal
        Utilidades.limpiarPantalla();
        vista.mostrarMenuPrincipal();
        return scanner.nextInt();  // devuelve lo que eligio el usuario
    }

    public void procesarOpcion(int opcion) {
        // segun lo que eligio, vamos a una parte o otra
        switch (opcion) {
            case 1:
                // gestion de clientes
                Utilidades.limpiarPantalla();
                MenuCliente menuCliente = new MenuCliente(scanner);
                menuCliente.ejecutar();  // ejecuta el menu de clientes
                break;
            case 2:
                // gestion de articulos
                Utilidades.limpiarPantalla();
                MenuArticulo menuArticulo = new MenuArticulo(scanner);
                menuArticulo.ejecutar();  // ejecuta el menu de articulos
                break;
            case 3:
                // gestion de facturas
                Utilidades.limpiarPantalla();
                MenuFactura menuFactura = new MenuFactura(scanner);
                menuFactura.ejecutar();  // ejecuta el menu de facturas
                break;
            case 4:
                // importar desde CSV
                Utilidades.limpiarPantalla();
                MenuImportacionCSV menuImportacion = new MenuImportacionCSV(scanner);
                menuImportacion.ejecutar();  // ejecuta el menu de importacion
                break;
            case 0:
                break;  // salir, no hacemos nada mas
            default:
                // si puso cualquier otra cosa que no es valida
                Utilidades.limpiarPantalla();
                vista.mostrarError("Opción no válida");
                Utilidades.pausa();  // pausa para que lea el error
        }
    }
}