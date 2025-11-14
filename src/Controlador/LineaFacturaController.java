package Controlador;

import Modelo.LineaFactura;
import Vista.LineaFacturaView;

public class LineaFacturaController {
    private Modelo.LineaFactura modelo;
    private LineaFacturaView vista;

    public LineaFacturaController(LineaFactura modelo, LineaFacturaView vista) {
        this.modelo = modelo;  // la linea de factura
        this.vista = vista;    // la vista para lineas
    }

    // añade los datos a la linea de factura
    public boolean añadirDatos(int cantidad,String nombreArticulo, double precioUnitario){
        try {
            // establecemos todos los datos
            modelo.setCantidad(cantidad);
            modelo.setNombreArticulo(nombreArticulo);
            modelo.setPrecioUnitario(precioUnitario);

            vista.mostrarMensaje("Datos de la línea de factura guardada correctamente.👌");  // mensaje exito
            return true;  // todo bien
        } catch (IllegalStateException e) {
            // si hay error
            vista.mostrarError("Error al guardar los datos de la línea de factura:⚠️ " + e.getMessage());
            return false;  // algo fallo
        }
    }

    // muestra los datos de la linea
    public void mostrarLinea(){
        vista.mostrarDatosLinea(modelo);  // la vista lo muestra
    }

    // exporta la linea a CSV
    public void exportarLinea() {
        String csv = modelo.toString();  // la linea a CSV

        // exportamos a archivo
        boolean exportado = Modelo.GestorArchivos.exportarCSV("lineas", csv, null);
        if (exportado) {
            vista.mostrarMensaje("✅ Línea de factura exportada correctamente a archivo CSV");  // exito
        } else {
            vista.mostrarError("❌ Error al exportar línea de factura a CSV");  // error
        }
    }

    // obtiene el total de la linea (cantidad * precio)
    public double getTotalLinea(){
        return modelo.getTotalLinea();  // el modelo calcula el total
    }

    // para obtener datos individuales de la linea
    public int getCantidad(){return modelo.getCantidad();}
    public String getNombreArticulo(){return modelo.getNombreArticulo();}
    public double getPrecioUnitario(){return modelo.getPrecioUnitario();}
}