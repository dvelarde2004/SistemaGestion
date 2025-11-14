package Vista;

import Modelo.LineaFactura;

public class LineaFacturaView {

    public void mostrarMensaje(String mensaje){
        // mensaje normal
        System.out.println(mensaje);
    }

    public void mostrarError(String error){
        // mensaje de error
        System.out.println(error);
    }

    public void mostrarDatosLinea(LineaFactura linea) {
        // muestra los datos de una linea de factura
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         LÍNEA DE FACTURA           │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-20s │\n", "Cantidad", linea.getCantidad());
        System.out.printf("│ %-15s: %-20s │\n", "Artículo", linea.getNombreArticulo() != null ? linea.getNombreArticulo() : "No asignado");
        System.out.printf("│ %-15s: %-20.2f │\n", "Precio Unit.", linea.getPrecioUnitario());
        System.out.printf("│ %-15s: %-20.2f │\n", "Total Línea", linea.getTotalLinea());
        System.out.println("└─────────────────────────────────────┘");
    }
}