package org.eamsoft.orm.manager;

import org.eamsoft.orm.cache.SuperCache;
import org.eamsoft.orm.dao.CotizanteCsvOrm;
import org.eamsoft.orm.modelo.Cotizante;

import java.util.*;

public class CotizanteEntityManager {
    private CotizanteCsvOrm cotizanteDao;
    private SuperCache cache;
    private String archivoCsv;

    // Constructor para inicializar el archivo CSV y la caché
    public CotizanteEntityManager() {
        this.cotizanteDao = new CotizanteCsvOrm();
        this.cache = SuperCache.getInstance();
        // Obtener la ruta del archivo CSV
        this.archivoCsv = getClass().getClassLoader().getResource("cotizantes.csv").getPath();
    }

    /**
     * Obtiene todos los cotizantes, ya sea de la caché o del archivo CSV.
     * Si los datos no están en la caché, los carga desde el CSV y los almacena en la caché.
     */
    public List<Cotizante> findAll() {
        Map<String, Map<String, String>> cachedData = (Map<String, Map<String, String>>) cache.obtenerCache("cotizantes.csv");
        List<Cotizante> cotizantes = new LinkedList<>();

        if (cachedData != null) {
            // Convertir cada Map<String, String> en un Cotizante
            for (Map<String, String> data : cachedData.values()) {
                Cotizante cotizante = cotizanteDao.mapearFila(data);
                cotizantes.add(cotizante);
            }
        } else {
            // Cargar desde el CSV y almacenar en la caché
            cotizantes = cotizanteDao.leerTodasLasFilas(archivoCsv);

            // Convertir la lista de Cotizantes en un formato adecuado para la caché
            Map<String, Map<String, String>> cacheData = new HashMap<>();
            for (Cotizante cotizante : cotizantes) {
                cacheData.put(cotizante.getDocumento(), cotizanteDao.extraerAtributos(cotizante));
            }
            cache.agregarCache("cotizantes.csv", cacheData);
        }

        return cotizantes;
    }


    /**
     * Busca un cotizante por su ID (Documento).
     */
    public Optional<Cotizante> findById(String id) {
        return findAll().stream()
                .filter(cotizante -> cotizante.getDocumento().equals(id))
                .findFirst();
    }

    /**
     * Guarda un cotizante en el archivo CSV y actualiza la caché.
     */
    public void save(Cotizante cotizante) {
        cotizanteDao.escribirFila(archivoCsv, cotizante);
        cache.invalidate("cotizantes.csv"); // Invalidar caché para recargar en la próxima lectura
    }
}
