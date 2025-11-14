package Controlador;

import Modelo.Factura;
import Modelo.LineaFactura;
import Modelo.Cliente;
import Vista.FacturaView;

import java.util.List;

public class FacturaController {
    private Factura modelo;
    private FacturaView vista;

    public FacturaController(Factura modelo, FacturaView vista) {
        this.modelo = modelo;  // la factura
        this.vista = vista;    // la vista de facturas
    }

    // añade una linea a la factura
    public boolean añadirLinea(LineaFactura linea){
        try {
            modelo.añadirLinea(linea);  // el modelo añade la linea
            vista.mostrarMensaje("Línea agregada correctamente.");  // mensaje de exito
            return true;  // todo bien
        } catch (IllegalStateException e) {
            vista.mostrarError(e.getMessage());  // si hay error
            return false;  // algo fallo
        }
    }

    // cambia el IVA de la factura
    public void cambiarIva(int nuevoIva){
        try {
            modelo.setIva(nuevoIva);  // establece el nuevo IVA
            vista.mostrarMensaje("IVA cambiado correctamente a "+nuevoIva+"%.");  // mensaje
        } catch (IllegalStateException e) {
            vista.mostrarError(e.getMessage());  // error
        }
    }

    // pone el IVA por defecto (21%)
    public void aplicarIvaPorDefecto(){
        modelo.aplicarIvaPorDefecto();  // el modelo sabe cual es el IVA por defecto
        vista.mostrarMensaje("IVA restaurado al valor por defecto (21%).");  // mensaje
    }

    // calcula y muestra los totales de la factura
    public void mostrarTotales(){
        double base = modelo.calcularBaseImponible();  // base sin IVA
        double iva = modelo.calcularIVA();             // IVA calculado
        double total = modelo.calcularTotal();         // total con IVA

        vista.mostrarTotales(base, iva, total);  // la vista lo muestra bonito
    }

    // valida que la factura tenga todos los datos correctos
    public void validarFactura(){
        if(modelo.validarDatosFactura()){  // el modelo valida
            vista.mostrarMensaje("La factura es válida.👌");  // valida
        } else {
            vista.mostrarError("La factura no es válida.⚠️\nRevise los datos ingresados.");  // no valida
        }
    }

    // exporta la factura a CSV
    public void exportarFactura() {
        String csv = modelo.toString();  // la factura a CSV

        // nombre especial para el archivo de factura
        String nombreCliente = modelo.getCliente() != null ?
                modelo.getCliente().getNombre().replace(" ", "_") : "cliente";  // quita espacios del nombre
        String fecha = modelo.getFecha() != null ?
                modelo.getFecha().replace("-", "") : "nodate";  // fecha sin guiones
        String nombreArchivo = String.format("factura_%s_%s_%s",
                nombreCliente, fecha, modelo.getId());  // nombre del archivo

        // exportamos
        boolean exportado = Modelo.GestorArchivos.exportarCSV("facturas", csv, nombreArchivo);
        if (exportado) {
            vista.mostrarMensaje("✅ Factura exportada correctamente a archivo CSV");  // exito
        } else {
            vista.mostrarError("❌ Error al exportar factura a CSV");  // error
        }
    }

    // muestra la factura completa
    public void mostrarFactura(){
        vista.mostrarFacturaCompleta(modelo);  // la vista se encarga
    }

    // estos metodos son para obtener datos individuales de la factura
    public String getId() {return modelo.getId();}
    public String getFecha() {return modelo.getFecha();}
    public Cliente getCliente() {return modelo.getCliente();}
    public List<LineaFactura> getLineas() {return modelo.getLineas();}
    public int getIva() {return modelo.getIva();}
}