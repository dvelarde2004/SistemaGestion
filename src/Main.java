import Vista.MainView;
import Menu.MenuPrincipal;
import Modelo.GestorArchivos;
import Util.Utilidades;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // inicialilazamos el teclado y el view
        Scanner scanner = new Scanner(System.in);
        MainView vista = new MainView();

        // esto es para que cuando se cierre la app haga backup solo
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Utilidades.limpiarPantalla();
            System.out.println("\n🔁 Cerrando aplicación...");
            GestorArchivos.hacerBackupAlCerrar();
            System.out.println("👋 Aplicación cerrada correctamente");
        }));

        // mostramos la bienvenida al =usuario se puede eliminar=
        Utilidades.limpiarPantalla();
        vista.mostrarBienvenida();
        Utilidades.pausa();

        // creamos el menu principal y empezamos el bucle
        MenuPrincipal menu = new MenuPrincipal(scanner, vista);

        //Variable para salir del bucle
        boolean salir = false;

        //Bucle principal
        while (!salir) {
            try {
                // mostramos el menu principal
                int opcion = menu.mostrar();

                if (opcion == 0) {
                    salir = true;  // si elige 0, salimos
                    Utilidades.limpiarPantalla();
                    vista.mostrarDespedida();
                } else {
                    menu.procesarOpcion(opcion);  // si no, seguimos con el programa
                }
            } catch (Exception e) {
                // si hay algun error, lo mostramos y seguimos
                Utilidades.limpiarPantalla();
                Utilidades.mostrarError("Error: " + e.getMessage());
                scanner.nextLine();  // limpiamos el buffer por si acaso
                Utilidades.pausa();
            }
        }

        scanner.close();
    }
}