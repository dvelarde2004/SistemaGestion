package Vista;

import Modelo.Articulo;

public class ArticuloView {

    public void mostrarMensaje(String mensaje){
        // mensaje normal
        System.out.println(mensaje);
    }

    public void mostrarError(String error){
        // mensaje de error
        System.out.println(error);
    }

    public void mostrarDatosArticulo(Articulo articulo) {
        // muestra los datos de un articulo
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           DATOS DEL ARTÍCULO        │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-20s │\n", "Nombre", articulo.getNombre() != null ? articulo.getNombre() : "No asignado");
        System.out.printf("│ %-15s: %-20.2f€ │\n", "Precio", articulo.getPrecio());
        System.out.println("└─────────────────────────────────────┘");
    }

    public void mostrarMenuArticulo() {
        // menu de gestion de articulos
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         GESTIÓN DE ARTÍCULOS        │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Crear artículo                   │");
        System.out.println("│ 2. Ver artículo actual              │");
        System.out.println("│ 3. Exportar a CSV                   │");
        System.out.println("│ 0. Volver al menú principal         │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Selecciona una opción: ");
    }

    public void mostrarFormularioCrearArticulo() {
        // formulario para crear articulo - pide nombre
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         CREAR NUEVO ARTÍCULO        │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│ Nombre del artículo: ");
    }

    public void mostrarFormularioPrecio() {
        // pide el precio del articulo
        System.out.print("│ Precio del artículo: ");
    }

    public void mostrarListaArticulos(java.util.List<Articulo> articulos) {
        // muestra la lista de todos los articulos que hay
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│          LISTA DE ARTÍCULOS         │");
        System.out.println("├─────────────────────────────────────┤");
        if (articulos.isEmpty()) {
            System.out.println("│     No hay artículos creados       │");  // si no hay articulos registrados
        } else {
            //hace el bucle para escribir toda la lista de articulos
            for (int i = 0; i < articulos.size(); i++) {
                Articulo art = articulos.get(i);
                System.out.printf("│ %-2d. %-25s %6.2f€ │\n",
                        i + 1,
                        art.getNombre() != null ?
                                (art.getNombre().length() > 25 ?
                                        art.getNombre().substring(0, 22) + "..." :  // si el nombre es muy largo lo cortamos
                                        art.getNombre()) :
                                "Sin nombre",
                        art.getPrecio());
            }
        }
        System.out.println("└─────────────────────────────────────┘");
    }
}