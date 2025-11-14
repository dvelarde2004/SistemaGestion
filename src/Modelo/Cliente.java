package Modelo;

public class Cliente {
    // esta clase guarda todos los datos de un cliente

    // nombre del cliente y maximo de caracteres que puede tener
    private String nombre;
    private static int maxCaracterNombre = 40;

    // dni del cliente y su tamaño maximo
    private String dni;
    private static int maxCaracterDni = 9;

    // direccion del cliente y maximo de caracteres
    private String direccion;
    private static int maxCaracterDireccion = 40;

    // poblacion y su maximo
    private String poblacion;
    private static int maxCaracterPoblacion = 20;

    // provincia y su maximo
    private String provincia;
    private static int maxCaracterProvincia = 20;

    // codigo postal y su tamaño exacto
    private String codigoPostal;
    private static int maxCaracterCodigoPostal = 5;

    // telefono y su tamaño exacto
    private String telefono;
    private static int maxCaracterTelefono = 9;

    // getter para el nombre
    public String getNombre(){return nombre;}

    // comprueba que el nombre no sea demasiado largo
    public void setNombre(String nombre){
        if(nombre.length() <= maxCaracterNombre){
            this.nombre = nombre;  // si esta bien, lo guardamos
        } else {
            System.out.println("El nombre excede el maximo de caracteres: " + maxCaracterNombre);  // si es muy largo
        }
    }

    // getter del DNI
    public String getDni(){return dni;}

    // setter del DNI - comprueba que tenga el tamaño correcto
    public void setDni(String dni) {
        if (dni !=null && dni.length() != maxCaracterDni) {
            throw new IllegalStateException("El DNI debe tener " + maxCaracterDni + " caracteres.");  // error si no tiene 9 caracteres
        }
        this.dni = dni;  // si esta bien, lo guardamos
    }

    // getter de la direccion
    public String getDireccion(){return direccion;}

    // setter de la direccion - comprueba tamaño
    public void setDireccion(String direccion){
        if (direccion != null && direccion.length()> maxCaracterDireccion) {
            throw new IllegalStateException("La direccion no debe exceder " + maxCaracterDireccion + " caracteres.");  // error si es muy larga
        }
        this.direccion = direccion;  // si esta bien
    }

    // getter de la poblacion
    public String getPoblacion(){return poblacion;}

    // setter de la poblacion - comprueba tamaño
    public void setPoblacion(String poblacion){
        if (poblacion != null && poblacion.length()> maxCaracterPoblacion) {
            throw new IllegalStateException("La poblacion no debe exceder " + maxCaracterPoblacion + " caracteres.");  // error si es muy larga
        }
        this.poblacion = poblacion;  // si esta bien
    }

    // getter de la provincia
    public String getProvincia(){return provincia;}

    // setter de la provincia - comprueba tamaño
    public void setProvincia(String provincia){
        if (provincia != null && provincia.length()> maxCaracterProvincia) {
            throw new IllegalStateException("La provincia no debe exceder " + maxCaracterProvincia + " caracteres.");  // error si es muy larga
        }
        this.provincia = provincia;  // si esta bien
    }

    // getter del codigo postal
    public String getCodigoPostal(){return codigoPostal;}

    // setter del codigo postal - comprueba que tenga 5 digitos y sean numeros
    public void setCodigoPostal(String codigoPostal){
        if (codigoPostal != null){
            // comprueba que tenga exactamente 5 caracteres
            if(codigoPostal.length() != maxCaracterCodigoPostal){
                throw new IllegalStateException("El codigo postal debe tener exactamente " + maxCaracterCodigoPostal + " caracteres.");
            }
            // comprueba que sean solo numeros
            if (!codigoPostal.matches("\\d{5}")) {
                throw new IllegalStateException("El codigo postal debe contener solo numeros " + maxCaracterCodigoPostal + " digitos.");
            }
        }
        this.codigoPostal = codigoPostal;  // si todo esta bien
    }

    // getter del telefono
    public String getTelefono(){return telefono;}

    // setter del telefono - comprueba que tenga 9 digitos y sean numeros
    public void setTelefono(String telefono){
        if (telefono != null){
            // comprueba que tenga exactamente 9 caracteres
            if(telefono.length() != maxCaracterTelefono){
                throw new IllegalStateException("El telefono debe tener exactamente " + maxCaracterTelefono + " caracteres.");
            }
            // comprueba que sean solo numeros
            if (!telefono.matches("\\d{9}")) {
                throw new IllegalStateException("El telefono debe contener solo numeros " + maxCaracterTelefono + " digitos.");
            }
        }
        this.telefono = telefono;  // si todo esta bien
    }

    // convierte el cliente a formato CSV para exportar
    @Override
    public String toString(){
        return String.format("%s;%s;%s;%s;%s;%s;%s",
                // manejamos valores nulos para que no salga "null" en el CSV
                nombre != null ? nombre : "",
                dni != null ? dni : "",
                direccion != null ? direccion : "",
                poblacion != null ? poblacion : "",
                codigoPostal != null ? codigoPostal : "",
                provincia != null ? provincia : "",
                telefono != null ? telefono : ""
        );
    }

    // convierte el cliente a formato legible para mostrar
    public String toFormattedString() {
        return String.format(
                "Nombre: %s\nDNI: %s\nDirección: %s\nPoblación: %s\nCodigo Postal: %s\nProvincia: %s\nTeléfono: %s",
                nombre != null ? nombre : "No asignado",
                dni != null ? dni : "No asignado",
                direccion != null ? direccion : "No asignado",
                poblacion != null ? poblacion : "No asignado",
                codigoPostal != null ? codigoPostal : "No asignado",
                provincia != null ? provincia : "No asignado",
                telefono != null ? telefono : "No asignado"
        );
    }
}