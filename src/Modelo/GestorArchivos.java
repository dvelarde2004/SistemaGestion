package Modelo;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

public class GestorArchivos {
    // esta clase maneja todos los archivos y carpetas

    //Nombre de la carpeta base donde se guarda los datos
    private static final String DatosGuardados = "\\Datos_guardados";

    // rutas de las carpetas que usamos
    //ruta principal asignar la ruta especifica de donde queremos que se guarde la carpeta del programa
    private static final String CARPETA_BASE = "C:\\trabajos clase\\Segundo año\\programacion\\java\\clase bartomeu\\SistemaGestioDanielVelarde"+DatosGuardados;

    //resto de subcarpetas
    private static final String CSV_CREADOS = CARPETA_BASE + "/csv_creados";
    private static final String IMPORTACIONES_CSV = CARPETA_BASE + "/importaciones_csv";
    private static final String BASE_DATOS_CSV = CARPETA_BASE + "/base_datos_csv";

    // subcarpetas para importar diferentes tipos de datos
    private static final String BASE_CLIENTE = IMPORTACIONES_CSV + "/base_cliente";
    private static final String BASE_ARTICULO = IMPORTACIONES_CSV + "/base_articulo";
    private static final String BASE_FACTURA = IMPORTACIONES_CSV + "/base_factura";
    private static final String BASE_LINEA = IMPORTACIONES_CSV + "/base_linea";

    // contadores para IDs de archivos
    private static Map<String, Integer> contadores = new HashMap<>();
    private static String carpetaSesionActual;  // carpeta de esta sesion para backups

    // esto se ejecuta cuando se carga la clase
    static {
        inicializarEstructura();  // crea las carpetas
        cargarContadores();       // carga los contadores de archivos
        crearCarpetaSesion();     // crea carpeta para esta sesion
    }

    // crea todas las carpetas necesarias si no existen
    private static void inicializarEstructura() {
        try {
            Files.createDirectories(Paths.get(CSV_CREADOS));
            Files.createDirectories(Paths.get(BASE_CLIENTE));
            Files.createDirectories(Paths.get(BASE_ARTICULO));
            Files.createDirectories(Paths.get(BASE_FACTURA));
            Files.createDirectories(Paths.get(BASE_LINEA));
            Files.createDirectories(Paths.get(BASE_DATOS_CSV));
        } catch (IOException e) {
            System.out.println("❌ Error creando estructura de carpetas: " + e.getMessage());
        }
    }

    // crea una carpeta con la fecha y hora actual para esta sesion
    private static void crearCarpetaSesion() {
        try {
            // nombre con fecha y hora
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = sdf.format(new Date());
            carpetaSesionActual = BASE_DATOS_CSV + "/" + timestamp;
            Files.createDirectories(Paths.get(carpetaSesionActual));
            System.out.println("📁 Sesión iniciada: " + carpetaSesionActual);
        } catch (IOException e) {
            System.out.println("❌ Error creando carpeta de sesión: " + e.getMessage());
        }
    }

    // hace backup de todo cuando se cierra la aplicacion
    public static void hacerBackupAlCerrar() {
        try {
            System.out.println("💾 Realizando backup de la sesión...");

            // mira la carpeta donde estan los CSV creados
            Path carpetaCreados = Paths.get(CSV_CREADOS);
            if (!Files.exists(carpetaCreados) || !Files.isDirectory(carpetaCreados)) {
                return;  // si no existe, no hace nada
            }

            // lista todos los archivos CSV
            List<Path> archivos = Files.list(carpetaCreados)
                    .filter(path -> !Files.isDirectory(path))
                    .collect(Collectors.toList());

            if (archivos.isEmpty()) {
                System.out.println("📭 No hay archivos para hacer backup");
                return;  // si no hay archivos
            }

            // contadores para los IDs de backup por si quieres cambiar el id
            Map<String, Integer> contadoresBackup = new HashMap<>();
            contadoresBackup.put("clientes", 1);
            contadoresBackup.put("articulos", 1);
            contadoresBackup.put("facturas", 1);
            contadoresBackup.put("lineas", 1);

            // fecha para el nombre del archivo por si quieres cambiar la fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = sdf.format(new Date());

            // copia y renombra cada archivo
            for (Path archivoOriginal : archivos) {
                String nombreOriginal = archivoOriginal.getFileName().toString();
                String tipo = determinarTipoArchivo(nombreOriginal);  // que tipo es
                int id = contadoresBackup.getOrDefault(tipo, 1);

                // nuevo nombre: [ID]_[tipo]_backup_[fecha].csv
                String nuevoNombre = String.format("%03d_%s_backup_%s.csv", id, tipo, fecha);
                Path archivoBackup = Paths.get(carpetaSesionActual, nuevoNombre);

                // copia el archivo
                Files.copy(archivoOriginal, archivoBackup, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("✅ Backup: " + nuevoNombre);

                // aumenta el contador para el siguiente
                contadoresBackup.put(tipo, id + 1);
            }

            System.out.println("🎯 Backup completado en: " + carpetaSesionActual);

        } catch (IOException e) {
            System.out.println("❌ Error durante el backup: " + e.getMessage());
        }
    }

    // determina que tipo de archivo es por su nombre (no puedes cambiar el nombre)
    private static String determinarTipoArchivo(String nombreArchivo) {
        if (nombreArchivo.startsWith("clientes")) return "clientes";
        if (nombreArchivo.startsWith("articulos")) return "articulos";
        if (nombreArchivo.startsWith("factura")) return "facturas";
        if (nombreArchivo.startsWith("lineas")) return "lineas";
        return "otros";  // si no coincide con nada
    }

    // carga los contadores de archivos existentes
    private static void cargarContadores() {
        String[] tipos = {"clientes", "articulos", "facturas", "lineas"};
        for (String tipo : tipos) {
            contadores.put(tipo, obtenerUltimoID(tipo) + 1);  // ultimo ID + 1
        }
    }

    // obtiene el ultimo ID usado para un tipo de archivo
    private static int obtenerUltimoID(String tipo) {
        try {
            Path carpeta = Paths.get(CSV_CREADOS);
            if (!Files.exists(carpeta)) return 0;  // si no existe carpeta

            // busca archivos de ese tipo y extrae el numero mas alto
            return Files.list(carpeta)
                    .filter(path -> path.getFileName().toString().startsWith(tipo + "_"))
                    .map(path -> {
                        String nombre = path.getFileName().toString();
                        try {
                            return Integer.parseInt(nombre.substring(tipo.length() + 1, nombre.length() - 4));
                        } catch (NumberFormatException e) {
                            return 0;  // si no puede parsear, devuelve 0
                        }
                    })
                    .max(Integer::compareTo)
                    .orElse(0);  // si no hay archivos, devuelve 0

        } catch (IOException e) {
            return 0;  // si hay error, devuelve 0
        }
    }

    // obtiene el proximo ID para un tipo de archivo
    public static int getProximoID(String tipo) {
        int id = contadores.getOrDefault(tipo, 1);  // ID actual
        contadores.put(tipo, id + 1);  // aumenta para el siguiente
        return id;
    }

    // exporta datos a un archivo CSV
    public static boolean exportarCSV(String tipo, String contenido, String nombreEspecial) {
        try {
            String nombreArchivo;
            if (nombreEspecial != null) {
                nombreArchivo = nombreEspecial + ".csv";  // nombre especial
            } else {
                int id = getProximoID(tipo);  // ID automatico
                nombreArchivo = String.format("%s_%03d.csv", tipo, id);  // nombre con ID
            }

            Path archivo = Paths.get(CSV_CREADOS, nombreArchivo);
            Files.write(archivo, contenido.getBytes());  // escribe el archivo
            System.out.println("✅ Archivo exportado: " + archivo.toString());
            return true;  // exito
        } catch (IOException e) {
            System.out.println("❌ Error exportando archivo: " + e.getMessage());
            return false;  // error
        }
    }

    // lista archivos disponibles para importar
    public static List<String> listarArchivosImportacion(String tipo) {
        try {
            String carpetaTipo = "";
            // segun el tipo, va a una carpeta u otra
            switch (tipo.toLowerCase()) {
                case "cliente": carpetaTipo = BASE_CLIENTE; break;
                case "articulo": carpetaTipo = BASE_ARTICULO; break;
                case "factura": carpetaTipo = BASE_FACTURA; break;
                case "linea": carpetaTipo = BASE_LINEA; break;
                default: return new ArrayList<>();  // tipo no valido
            }

            Path carpeta = Paths.get(carpetaTipo);
            if (!Files.exists(carpeta)) return new ArrayList<>();  // si no existe carpeta

            // lista y formatea los archivos
            return Files.list(carpeta)
                    .filter(path -> path.toString().toLowerCase().endsWith(".csv"))
                    .map(path -> {
                        String nombre = path.getFileName().toString();
                        // extrae ID del formato "001_nombre.csv"
                        try {
                            String idStr = nombre.substring(0, 3);
                            int id = Integer.parseInt(idStr);
                            return String.format("%03d - %s", id, nombre.substring(4));
                        } catch (Exception e) {
                            return "000 - " + nombre;  // si no puede parsear
                        }
                    })
                    .sorted()
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return new ArrayList<>();  // si hay error
        }
    }

    // importa un archivo CSV por su ID
    public static List<String> importarCSV(String tipo, int id) {
        try {
            String carpetaTipo = "";
            switch (tipo.toLowerCase()) {
                case "cliente": carpetaTipo = BASE_CLIENTE; break;
                case "articulo": carpetaTipo = BASE_ARTICULO; break;
                case "factura": carpetaTipo = BASE_FACTURA; break;
                case "linea": carpetaTipo = BASE_LINEA; break;
                default: return new ArrayList<>();  // tipo no valido
            }

            Path carpeta = Paths.get(carpetaTipo);
            if (!Files.exists(carpeta)) return new ArrayList<>();  // si no existe

            // busca el archivo con ese ID
            Optional<Path> archivo = Files.list(carpeta)
                    .filter(path -> {
                        String nombre = path.getFileName().toString();
                        return nombre.startsWith(String.format("%03d_", id));  // que empiece con el ID
                    })
                    .findFirst();

            if (archivo.isPresent()) {
                return Files.readAllLines(archivo.get());  // lee todas las lineas
            }
        } catch (IOException e) {
            System.out.println("❌ Error importando archivo: " + e.getMessage());
        }
        return new ArrayList<>();  // si no encuentra o hay error
    }

    // getters para las rutas (por si se necesitan)
    public static String getRutaCSVCreados() { return CSV_CREADOS; }
    public static String getRutaImportaciones() { return IMPORTACIONES_CSV; }
    public static String getRutaBaseDatos() { return BASE_DATOS_CSV; }
}