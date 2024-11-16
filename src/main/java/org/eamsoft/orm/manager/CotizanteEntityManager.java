package org.eamsoft.orm.manager;

import org.eamsoft.orm.cache.SuperCache;
import org.eamsoft.orm.dao.CotizanteCsvOrm;
import org.eamsoft.orm.modelo.Cotizante;

import java.io.*;
import java.util.*;

public class CotizanteEntityManager {
    private CotizanteCsvOrm cotizanteDao;
    private SuperCache cache;
    private final String archivoCotizantesCsv;
    private final String archivoListaNegraCsv;

    // Constructor para inicializar el archivo CSV y la caché
    public CotizanteEntityManager() {
        this.cotizanteDao = new CotizanteCsvOrm();
        this.cache = SuperCache.getInstance();

        // Obtener la ruta del archivo CSV
        this.archivoCotizantesCsv = getClass().getClassLoader().getResource("cotizantess.csv").getPath();
        this.archivoListaNegraCsv = getClass().getClassLoader().getResource("lista_negra.csv").getPath();

        verificarFechasListaNegra();
    }

    /**
     * Obtiene todos los cotizantes, ya sea de la caché o del archivo CSV.
     * Si los datos no están en la caché, los carga desde el CSV y los almacena en la caché.
     */
    public List<Cotizante> findAll(String nombreArchivo, String archivo) {
        Map<String, Map<String, String>> cachedData = (Map<String, Map<String, String>>) cache.obtenerCache(nombreArchivo);
        List<Cotizante> cotizantes = new LinkedList<>();

        if (cachedData != null) {
            // Convertir cada Map<String, String> en un Cotizante
            for (Map<String, String> data : cachedData.values()) {
                Cotizante cotizante = cotizanteDao.mapearFila(data);
                cotizantes.add(cotizante);
            }
        } else {
            // Cargar desde el CSV y almacenar en la caché
            cotizantes = cotizanteDao.leerTodasLasFilas(archivo);

            // Convertir la lista de Cotizantes en un formato adecuado para la caché
            Map<String, Map<String, String>> cacheData = new HashMap<>();
            for (Cotizante cotizante : cotizantes) {
                cacheData.put(cotizante.getDocumento(), cotizanteDao.extraerAtributos(cotizante));
            }
            cache.agregarCache(nombreArchivo, cacheData);
        }

        return cotizantes;
    }


    /**
     * Busca un cotizante por su ID (Documento).
     */
    public Optional<Cotizante> findById(String id, String nombreArchivo, String archivo) {
        return findAll(nombreArchivo, archivo).stream()
                .filter(cotizante -> cotizante.getDocumento().equals(id))
                .findFirst();
    }

    /**
     * Guarda un cotizante en el archivo CSV y actualiza la caché.
     */
    public void save(Cotizante cotizante, String archivo, String nombreArchivo) {
        System.out.println("Guardando cotizante en archivo: " + cotizante.toCSV());
        System.out.println("Guardando cotizante en archivo: " + archivo);
        cotizanteDao.escribirFila(archivo, cotizante);
        cache.invalidate(nombreArchivo); // Invalidar caché para recargar en la próxima lectura
    }


    private void verificarFechasListaNegra() {
        List<Cotizante> listaNegra = findAll("lista_negra.csv", getArchivoListaNegraCsv());
        Date hoy = new Date();
        Iterator<Cotizante> iterator = listaNegra.iterator();

        while (iterator.hasNext()) {
            Cotizante cotizante = iterator.next();
            if (cotizante.getEnListaNegraUltimos6Meses() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(cotizante.getEnListaNegraUltimos6Meses());
                calendar.add(Calendar.MONTH, 6);
                Date seisMeses = calendar.getTime();
                if (hoy.after(seisMeses)) {
                    iterator.remove(); // Remover de forma segura
                }
            }
        }

        updateListaNegra(listaNegra); // Actualizar el archivo CSV
    }


    public void updateListaNegra(List<Cotizante> listaNegra) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoListaNegraCsv))) {
            pw.println("id,nombre,documento,edad,semanas_cotizadas,lista_negra_6_meses,pre_pensionado,fondo_publico,fondo_civil,fondo,fondo_civil_opcional,genero,ciudad,pais,detalles,motivo");
            for (Cotizante cotizante : listaNegra) {
                pw.println(cotizante.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
        cache.invalidate("lista_negra.csv");
    }


    public String getArchivoCotizantesCsv() {
        return this.archivoCotizantesCsv;
    }


    public String getArchivoListaNegraCsv() {
        return this.archivoListaNegraCsv;
    }
}