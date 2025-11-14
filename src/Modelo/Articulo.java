package Modelo;

public class Articulo {
    // esta clase guarda los datos de un articulo

    // nombre del articulo y su maximo de caracteres
    private String nombre;
    private static int maxCaracterNombre = 40;

    // precio del articulo
    private double precio;
    private static int maxCaracterPrecio = 6;

    // constructor - crea un articulo con nombre y precio
    public Articulo(String nombre, double precio){
        this.setNombre(nombre);   // usa el setter para validar
        this.setPrecio(precio);   // usa el setter para validar
    }

    // getter del nombre
    public String getNombre(){return nombre;}

    // setter del nombre - comprueba que no sea demasiado largo
    public void setNombre(String nombre) {
        if (nombre != null && nombre.length() > maxCaracterNombre) {
            throw new IllegalStateException("El nombre no debe exceder " + maxCaracterNombre + " caracteres.");  // error si es muy largo
        }
        this.nombre = nombre;  // si esta bien
    }

    // getter del precio
    public double getPrecio(){return precio;}

    // setter del precio - comprueba que sea positivo y no tenga demasiados digitos
    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalStateException("El precio no puede ser negativo.");  // error si es negativo
        }

        // comprueba que no tenga mas de 6 digitos (quitando el punto decimal)
        String precioStr = String.valueOf(precio).replace(".", "");
        if (precioStr.length() > maxCaracterPrecio) {
            throw new IllegalStateException("El precio no debe exceder " + maxCaracterPrecio + " dígitos.");  // error si tiene muchos digitos
        }

        this.precio = precio;  // si todo bien
    }

    // convierte el articulo a formato CSV
    @Override
    public String toString() {
        return String.format("%s;%.2f",  // nombre y precio con 2 decimales
                nombre != null ? nombre : "",  // maneja null
                precio
        );
    }

    // convierte el articulo a formato legible
    public String toFormattedString() {
        return String.format(
                "Nombre: %s\nPrecio: %.2f€",
                nombre != null ? nombre : "No asignado",
                precio
        );
    }
}