package Modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorDatos {
    // esta clase se encarga de guardar todos los datos del sistema (hace de base de datos)

    // listas donde guardamos todo
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Articulo> articulos = new ArrayList<>();
    private static List<LineaFactura> lineas = new ArrayList<>();
    private static List<Factura> facturas = new ArrayList<>();

    //  METODOS PARA CLIENTES

    // añade un cliente a la lista
    public static void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // devuelve una copia de la lista de clientes (para no modificar la original)
    public static List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    // obtiene un cliente por su posicion en la lista
    public static Cliente getClientePorIndice(int indice) {
        if (indice >= 0 && indice < clientes.size()) {
            return clientes.get(indice);  // si esta en rango, lo devuelve
        }
        return null;  // si no existe, devuelve null
    }

    //  METODOS PARA ARTICULOS

    // añade un articulo a la lista
    public static void agregarArticulo(Articulo articulo) {
        articulos.add(articulo);
    }

    // devuelve copia de la lista de articulos
    public static List<Articulo> getArticulos() {
        return new ArrayList<>(articulos);
    }

    // obtiene un articulo por su posicion
    public static Articulo getArticuloPorIndice(int indice) {
        if (indice >= 0 && indice < articulos.size()) {
            return articulos.get(indice);
        }
        return null;
    }

    //  METODOS PARA LINEAS DE FACTURA

    // añade una linea de factura
    public static void agregarLineaFactura(LineaFactura linea) {
        lineas.add(linea);
    }

    // devuelve copia de las lineas
    public static List<LineaFactura> getLineasFactura() {
        return new ArrayList<>(lineas);
    }

    //  METODOS PARA FACTURAS

    // añade una factura
    public static void agregarFactura(Factura factura) {
        facturas.add(factura);
    }

    // devuelve copia de las facturas
    public static List<Factura> getFacturas() {
        return new ArrayList<>(facturas);
    }

    //  METODOS DE UTILIDAD

    // cuantos clientes hay
    public static int getTotalClientes() {
        return clientes.size();
    }

    // cuantos articulos hay
    public static int getTotalArticulos() {
        return articulos.size();
    }

    // cuantas facturas hay
    public static int getTotalFacturas() {
        return facturas.size();
    }
}