package Vista;

import Modelo.Factura;
import Modelo.LineaFactura;
import java.util.List;

public class FacturaView {

    public void mostrarMensaje(String mensaje){
        // mensaje normal
        System.out.println(mensaje);
    }

    public void mostrarError(String error){
        // mensaje de error
        System.out.println(error);
    }

    public void mostrarTotales(double base, double iva, double total) {
        // muestra los totales de la factura
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           TOTALES FACTURA          │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ %-20s: %-15.2f │\n", "Base Imponible", base);
        System.out.printf("│ %-20s: %-15.2f │\n", "IVA", iva);
        System.out.printf("│ %-20s: %-15.2f │\n", "Total Factura", total);
        System.out.println("└─────────────────────────────────────┘");
    }

    public void mostrarFacturaCompleta(Factura factura) {
        // muestra toda la factura con sus lineas y totales
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│             FACTURA                 │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ %-12s: %-25s │\n", "ID", factura.getId());
        System.out.printf("│ %-12s: %-25s │\n", "Fecha", factura.getFecha() != null ? factura.getFecha() : "No asignada");
        System.out.printf("│ %-12s: %-25d │\n", "IVA", factura.getIva());
        System.out.println("├─────────────────────────────────────┤");

        //Selecionamos la lista de lineas de factura
        List<LineaFactura> lineas = factura.getLineas();
        if (lineas.isEmpty()) {
            //Si esta vacia la lista
            System.out.println("│        No hay líneas añadidas       │");  // si no hay lineas
        } else {
            System.out.println("│            LÍNEAS                   │");
            System.out.println("├─────────────────────────────────────┤");

            //Si hay lineas
            //bucle que recorre la lista y la imprime
            for (int i = 0; i < lineas.size(); i++) {
                LineaFactura linea = lineas.get(i);
                System.out.printf("│ %-2d. %-8d x %-18s │\n",
                        i + 1,
                        linea.getCantidad(),
                        linea.getNombreArticulo() != null ?
                                (linea.getNombreArticulo().length() > 18 ?
                                        linea.getNombreArticulo().substring(0, 15) + "..." :  // si el nombre es largo lo cortamos para que se vea bien
                                        linea.getNombreArticulo()) :
                                "Artículo");
            }
        }

        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-20.2f │\n", "Base Imponible", factura.calcularBaseImponible());
        System.out.printf("│ %-15s: %-20.2f │\n", "IVA", factura.calcularIVA());
        System.out.printf("│ %-15s: %-20.2f │\n", "TOTAL", factura.calcularTotal());
        System.out.println("└─────────────────────────────────────┘");
    }

    public void pausa() {
        // pausa para que el usuario pueda leer
        System.out.println("\nPresione Enter para continuar...");
        try {
            System.in.read();  // espera a que el usuario de enter
        } catch (Exception e) {}
    }
}