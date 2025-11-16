package Vista;

public class MainView {

    public void mostrarBienvenida() {
        // muestra la pantalla de bienvenida cuando empieza el programa // no es obligatoria
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│   SISTEMA DE FACTURACIÓN MVC       │");
        System.out.println("│           BIENVENIDO               │");
        System.out.println("└─────────────────────────────────────┘");
    }

    public void mostrarMenuPrincipal() {
        // muestra el menu principal con todas las opciones
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           MENÚ PRINCIPAL            │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. ➡ Gestión de Clientes           │");
        System.out.println("│ 2. ➡ Gestión de Artículos          │");
        System.out.println("│ 3. ➡ Gestión de Facturas           │");
        System.out.println("│ 4. ➡ Importar desde CSV            │");
        System.out.println("│ 0. ❌ Salir del Sistema            │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("✅ Seleccione una opción: ");
    }

    public void mostrarDespedida() {
        // muestra cuando el usuario decide salir del programa
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           ¡HASTA PRONTO!            │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ Gracias por usar el Sistema de      │");
        System.out.println("│ Facturación MVC                     │");
        System.out.println("│                                     │");
        System.out.println("│ Vuelva pronto para más gestiones    │");
        System.out.println("└─────────────────────────────────────┘");
    }

    public void mostrarError(String mensaje) {
        // mostrar mensajes de error
        System.out.println("❌ Error: " + mensaje);
    }
}