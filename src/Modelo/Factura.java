package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Factura {
    // esta clase representa una factura con todas sus lineas

    // contador para generar IDs automaticos unicos
    private static int contadorFacturas = 1;

    // IVA por defecto
    public static final int IVA_POR_DEFECTO = 21;

    // ID unico de la factura
    private String id;

    // fecha en formato AAAA-MM-DD
    private String fecha;

    // cliente al que pertenece la factura
    private Cliente cliente;

    // lista de lineas de la factura (maximo 10)
    private List<LineaFactura> lineas;
    private static int maxLineas = 10;

    // IVA actual de esta factura
    private int iva;

    // constructor basico - crea factura con fecha y cliente
    public Factura(String fecha, Cliente cliente) {
        this.id = generarIdAutomatico();  // genera ID automatico
        this.setFecha(fecha);             // valida y establece fecha
        this.setCliente(cliente);         // establece cliente
        this.lineas = new ArrayList<>();  // crea lista vacia de lineas
        this.iva = IVA_POR_DEFECTO;       // IVA por defecto
    }

    // constructor con IVA personalizado por cliente
    public Factura(String fecha, Cliente cliente, int ivaPersonalizado) {
        this.id = generarIdAutomatico();     // genera ID
        this.setFecha(fecha);                // fecha
        this.setCliente(cliente);            // cliente
        this.lineas = new ArrayList<>();     // lineas vacias
        this.setIva(ivaPersonalizado);       // IVA personalizado
    }

    // genera un ID automatico unico para cada factura
    private String generarIdAutomatico() {
        return "F-" + String.format("%04d", contadorFacturas++);  // formato F-0001, F-0002, etc.
    }

    // getter del ID (solo getter, no se puede cambiar)
    public String getId() { return id; }

    // getter de la fecha
    public String getFecha() { return fecha; }

    // setter de la fecha - comprueba que tenga formato AAAA-MM-DD
    public void setFecha(String fecha) {
        if (fecha != null && !fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalStateException("La fecha debe tener formato AAAA-MM-DD.");  // error si formato incorrecto
        }
        this.fecha = fecha;  // si esta bien
    }

    // getter del cliente
    public Cliente getCliente() { return cliente; }

    // setter del cliente - no puede ser null
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalStateException("La factura debe tener un cliente asociado.");  // error si no hay cliente
        }
        this.cliente = cliente;  // si esta bien
    }

    // getter de las lineas (solo lectura)
    public List<LineaFactura> getLineas() { return lineas; }

    // getter del IVA
    public int getIva() { return iva; }

    // setter del IVA - debe estar entre 0 y 100
    public void setIva(int iva) {
        if (iva < 0 || iva > 100) {
            throw new IllegalStateException("El IVA debe estar entre 0 y 100.");  // error si IVA no valido
        }
        this.iva = iva;  // si esta bien
    }

    // restaura el IVA al valor por defecto (21%)
    public void aplicarIvaPorDefecto() {
        this.iva = IVA_POR_DEFECTO;
    }

    // añade una linea a la factura
    public void añadirLinea(LineaFactura linea) {
        if (lineas.size() >= maxLineas) {
            throw new IllegalStateException("No se pueden añadir más de " + maxLineas + " líneas a la factura.");  // error si demasiadas lineas
        }
        if (linea == null) {
            throw new IllegalStateException("La línea no puede ser nula.");  // error si linea es null
        }
        lineas.add(linea);  // añade la linea
    }

    // calcula la base imponible (suma de todas las lineas)
    public double calcularBaseImponible() {
        double base = 0;
        for (LineaFactura linea : lineas) {
            base += linea.getTotalLinea();  // suma el total de cada linea
        }
        return base;
    }

    // calcula el IVA a partir de la base imponible
    public double calcularIVA() {
        return calcularBaseImponible() * (iva / 100.0);  // base * IVA%
    }

    // calcula el total (base + IVA)
    public double calcularTotal() {
        return calcularBaseImponible() + calcularIVA();  // base + IVA
    }

    // valida que la factura tenga todos los datos correctos
    public boolean validarDatosFactura() {
        if (cliente == null) {
            return false;  // debe tener cliente
        }
        if (lineas.isEmpty() || lineas.size() > maxLineas) {
            return false;  // debe tener lineas pero no demasiadas
        }
        return true;  // todo correcto
    }

    // convierte la factura a formato CSV
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // cabecera: ID;fecha;cliente;IVA
        sb.append(String.format("%s;%s;%s;%d",
                id,
                fecha != null ? fecha : "",
                cliente != null ? cliente.getNombre() : "",
                iva
        ));

        // añade cada linea separada por ;
        for (LineaFactura linea : lineas) {
            sb.append(";").append(linea.toString());
        }

        return sb.toString();
    }

    // convierte la factura a formato legible
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "=== FACTURA ===\nID: %s\nFecha: %s\nIVA: %d%%\n\nCLIENTE:\n%s\n\nLÍNEAS DE FACTURA:\n",
                id,
                fecha != null ? fecha : "No asignada",
                iva,
                cliente != null ? cliente.toFormattedString() : "No asignado"
        ));

        // añade cada linea
        for (int i = 0; i < lineas.size(); i++) {
            sb.append("\n--- Línea ").append(i + 1).append(" ---\n");
            sb.append(lineas.get(i).toFormattedString()).append("\n");
        }

        // añade totales
        sb.append(String.format(
                "\n=== TOTALES ===\nBase Imponible: %.2f€\nIVA (%d%%): %.2f€\nTOTAL: %.2f€",
                calcularBaseImponible(),
                iva,
                calcularIVA(),
                calcularTotal()
        ));

        return sb.toString();
    }
}