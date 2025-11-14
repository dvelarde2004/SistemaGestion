package Controlador;

import Modelo.Cliente;
import Vista.ClienteView;

public class ClienteController {
    private Cliente modelo;
    private ClienteView vista;

    public ClienteController(Cliente modelo, ClienteView vista) {
        this.modelo = modelo;  // el modelo con los datos del cliente
        this.vista = vista;    // la vista para mostrar cosas
    }

    // establece todos los datos del cliente de una vez
    public boolean establecerDatosCliente(String nombre, String dni, String direccion, String poblacion, String codigoPostal, String provincia, String telefono){
        try {
            // vamos estableciendo todos los datos uno por uno
            modelo.setNombre(nombre);
            modelo.setDni(dni);
            modelo.setDireccion(direccion);
            modelo.setPoblacion(poblacion);
            modelo.setCodigoPostal(codigoPostal);
            modelo.setProvincia(provincia);
            modelo.setTelefono(telefono);

            vista.mostrarMensaje("Datos del cliente guardados correctamente 👌.");  // mensaje de exito
            return true;  // todo bien
        } catch (IllegalStateException e) {
            // si hay algun error, lo mostramos
            vista.mostrarError("Error al guardar los datos del cliente:⚠️ " + e.getMessage());
            return false;  // algo salio mal
        }
    }

    // muestra los datos del cliente usando la vista
    public void mostrarDatosCliente(){
        vista.mostrarDatosCliente(modelo);  // la vista se encarga de mostrarlo bonito
    }

    // exporta el cliente a formato CSV
    public void exportarClienteCSV() {
        String csv = modelo.toString();  // el modelo sabe como convertirse a CSV

        // exportamos el CSV a un archivo real
        boolean exportado = Modelo.GestorArchivos.exportarCSV("clientes", csv, null);
        if (exportado) {
            vista.mostrarMensaje("✅ Cliente exportado correctamente a archivo CSV");  // exito
        } else {
            vista.mostrarError("❌ Error al exportar cliente a CSV");  // error
        }
    }

    // estos metodos son para obtener datos individuales del cliente
    public String getNombre(){return modelo.getNombre();}
    public String getDni(){return modelo.getDni();}
    public String getDireccion(){return modelo.getDireccion();}
    public String getPoblacion(){return modelo.getPoblacion();}
    public String getCodigoPostal(){return modelo.getCodigoPostal();}
    public String getProvincia(){return modelo.getProvincia();}
    public String getTelefono(){return modelo.getTelefono();}
}