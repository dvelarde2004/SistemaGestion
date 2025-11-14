package Util;

public class Utilidades {

    public static void limpiarPantalla() {
        // esto limpia la consola imprimiendo un monton de lineas vacias no es lo ideal pero bueno
        for (int i = 0; i < 30; i++) {
            System.out.println();  // imprime linea vacia
        }
    }

    public static void pausa() {
        // paron para que el usuario pueda leer antes de continuar
        System.out.println("\nPresione Enter para continuar...");
        try {
            System.in.read();  // espera a que pulse enter

        } catch (Exception e) {}  // si hay error pues nada, no hacemos caso
    }

    public static void mostrarMensaje(String mensaje) {
        // muestra un mensaje
        System.out.println("💬 " + mensaje);
    }

    public static void mostrarError(String error) {
        // muestra un error
        System.out.println("❌ " + error);
    }
}