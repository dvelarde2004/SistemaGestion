package Controlador;

import Modelo.Articulo;
import Vista.ArticuloView;

public class ArticuloController {
    private Articulo modelo;
    private ArticuloView vista;

    public ArticuloController(Articulo modelo, ArticuloView vista) {
        this.modelo = modelo;  // el articulo
        this.vista = vista;    // la vista para articulos
    }

    // establece los datos del articulo
    public boolean establecerDatos(String nombre, double precio){
        try {
            // ponemos el nombre y precio
            modelo.setNombre(nombre);
            modelo.setPrecio(precio);

            vista.mostrarMensaje("Datos del artículo guardados correctamente.👌");  // mensaje de exito
            return true;  // todo bien
        } catch (IllegalStateException e) {
            // si hay error
            vista.mostrarError("Error al guardar los datos del artículo:⚠️ " + e.getMessage());
            return false;  // algo fallo
        }
    }

    // muestra los datos del articulo
    public void mostrarArticulo(){
        vista.mostrarDatosArticulo(modelo);  // la vista lo muestra
    }

    // exporta el articulo a CSV
    public void exportarArticulo() {
        String csv = modelo.toString();  // el articulo a CSV

        // exportamos a archivo
        boolean exportado = Modelo.GestorArchivos.exportarCSV("articulos", csv, null);
        if (exportado) {
            vista.mostrarMensaje("✅ Artículo exportado correctamente a archivo CSV");  // exito
        } else {
            vista.mostrarError("❌ Error al exportar artículo a CSV");  // error
        }
    }

    // para obtener datos individuales del articulo
    public String getNombre(){return modelo.getNombre();}
    public double getPrecio(){return modelo.getPrecio();}
}