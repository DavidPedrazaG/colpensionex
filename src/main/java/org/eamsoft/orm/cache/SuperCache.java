package org.eamsoft.orm.cache;

import java.util.HashMap;
import java.util.Map;

public class SuperCache {
    private static SuperCache instance;
    private final Map<String, Map<String, Map<String, String>>> cache = new HashMap<>();

    private SuperCache() {}

    public static synchronized SuperCache getInstance() {
        if (instance == null) {
            instance = new SuperCache();
        }
        return instance;
    }

    public void agregarCache(String nombreArchivo, Map<String, Map<String, String>> datos) {
        cache.put(nombreArchivo, datos);
    }

    public Map<String, Map<String, String>> obtenerCache(String nombreArchivo) {
        return cache.get(nombreArchivo);
    }

    public void invalidate(String nombreArchivo) {
        cache.remove(nombreArchivo);
    }
}
