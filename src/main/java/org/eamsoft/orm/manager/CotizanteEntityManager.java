package org.eamsoft.orm.manager;

import org.eamsoft.orm.cache.SuperCache;
import org.eamsoft.orm.dao.CotizanteCsvOrm;
import org.eamsoft.orm.modelo.Cotizante;

import java.io.*;
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
        this.archivoCsv = getClass().getClassLoader().getResource("cotizantess.csv").getPath();
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
            cache.agregarCache("cotizantess.csv", cacheData);
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
        cache.invalidate("cotizantess.csv"); // Invalidar caché para recargar en la próxima lectura
    }

    public void guardarListaNegraSimplificada(List<Cotizante> listaNegra) {
        try {
            // Crear la carpeta output si no existe
            File outputDir = new File("output");
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

            // Ruta del archivo lista_negra.csv
            String archivoSalida = "output/lista_negra.csv";

            try (FileWriter writer = new FileWriter(archivoSalida, false)) {
                // Encabezados personalizados
                writer.write("nombre,documento,fondo,institucion,motivo,fecha\n");

                // Escribir cada cotizante
                for (Cotizante cotizante : listaNegra) {
                    String tipoInstitucion = determinarTipoInstitucion(cotizante);

                    writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                            cotizante.getNombre(),
                            cotizante.getDocumento(),
                            cotizante.getFondo(),
                            tipoInstitucion, // Pública o Civil
                            cotizante.getDetalles(), // Motivo específico
                            java.time.LocalDate.now())); // Fecha actual
                }
                System.out.println("Archivo generado con éxito: " + archivoSalida);
            }
        } catch (IOException e) {
            System.err.println("Error al generar el archivo lista_negra.csv: " + e.getMessage());
        }
    }

    private String determinarTipoInstitucion(Cotizante cotizante) {
        // Lista de fondos públicos conocidos
        List<String> fondosPublicos = Arrays.asList("Policía", "Armada", "Inpec", "Minsalud", "Mininterior");

        if (fondosPublicos.contains(cotizante.getFondo())) {
            return "Pública"; // Pertenece a una institución pública
        } else {
            return "Civil"; // No pertenece a una institución pública
        }
    }


    public boolean existsInListaNegra(String documento) {
        try (BufferedReader reader = new BufferedReader(new FileReader("output/lista_negra.csv"))) {
            String linea;
            // Saltar la primera línea (encabezados)
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[1].equals(documento)) { // Verificar si el documento coincide
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo lista_negra.csv: " + e.getMessage());
        }
        return false;
    }
}
