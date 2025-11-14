package Modelo;

public class LineaFactura {
    // esta clase representa una linea de una factura

    // cantidad de articulos y rango valido
    private int cantidad;
    private static int minCantidad = 1;
    private static int maxCantidad = 9999;  // maximo 4 digitos

    // nombre del articulo y maximo de caracteres
    private String nombreArticulo;
    private static int maxCaracterNombre = 40;

    // precio unitario y restricciones
    private double precioUnitario;
    private static int maxDigitosPrecio = 6;

    // constructor y crea una linea con todos los datos
    public LineaFactura(int cantidad, String nombreArticulo, double precioUnitario) {
        this.setCantidad(cantidad);           // valida y establece cantidad
        this.setNombreArticulo(nombreArticulo); // valida y establece nombre
        this.setPrecioUnitario(precioUnitario); // valida y establece precio
    }

    // getter de la cantidad
    public int getCantidad() { return cantidad; }

    // setter de la cantidad ( debe estar entre 1 y 9999) se puede subir
    public void setCantidad(int cantidad) {

        //condicion no puedes colocar cantidad inferior ni mañor a la minima
        if (cantidad < minCantidad || cantidad > maxCantidad) {
            throw new IllegalStateException("La cantidad debe estar entre " + minCantidad + " y " + maxCantidad + ".");  // error si cantidad no valida
        }
        this.cantidad = cantidad;  // si esta bien
    }

    // getter del nombre del articulo
    public String getNombreArticulo() { return nombreArticulo; }

    // setter del nombre (no debe pasarse del maximo de caracteres)
    public void setNombreArticulo(String nombreArticulo) {
        //condicion no puedes colocar cantidad inferior ni mañor a la minima
        if (nombreArticulo != null && nombreArticulo.length() > maxCaracterNombre) {
            throw new IllegalStateException("El nombre del articulo no debe exceder " + maxCaracterNombre + " caracteres.");  // error si muy largo
        }
        this.nombreArticulo = nombreArticulo;  // si esta bien
    }

    // getter del precio unitario
    public double getPrecioUnitario() { return precioUnitario; }

    // setter del precio - debe ser positivo y no tener demasiados digitos
    public void setPrecioUnitario(double precioUnitario) {

        //condicion no puedes colocar cantidad inferior ni mañor a la minima
        if (precioUnitario <= 0) {
            throw new IllegalStateException("El precio unitario debe ser mayor que 0.");  // error si no es positivo
        }

        // comprueba que no tenga mas de 6 digitos (quitando el punto decimal)
        String precioStr = String.valueOf(precioUnitario).replace(".", "");
        if (precioStr.length() > maxDigitosPrecio) {
            throw new IllegalStateException("El precio unitario no debe exceder " + maxDigitosPrecio + " dígitos.");  // error si muchos digitos
        }

        this.precioUnitario = precioUnitario;  // si va bien
    }

    // calcula el total de esta linea (cantidad * precio)
    public double getTotalLinea() {
        return cantidad * precioUnitario;  // multiplica
    }

    // convierte la linea a formato CSV
    @Override
    public String toString() {
        return String.format("%d;%s;%.2f",  // cantidad;nombre;precio //separado por ;
                cantidad,
                nombreArticulo != null ? nombreArticulo : "",  // maneja null
                precioUnitario
        );
    }

    // convierte la linea a formato legible
    public String toFormattedString() {
        return String.format(
                "Cantidad: %d\nArticulo: %s\nPrecio por Unidad: %.2f€\nTotal Línea: %.2f€",
                cantidad,
                nombreArticulo != null ? nombreArticulo : "No asignado",
                precioUnitario,
                getTotalLinea()  // calcula el total
        );
    }
}