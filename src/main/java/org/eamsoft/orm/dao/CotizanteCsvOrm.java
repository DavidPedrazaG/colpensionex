package org.eamsoft.orm.dao;

import org.eamsoft.orm.modelo.Cotizante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CotizanteCsvOrm extends CsvOrmBase<Cotizante>{

    @Override
    public Cotizante mapearFila(Map<String, String> fila) {
        Cotizante cotizante = new Cotizante();

        // Asignar el nombre y el documento directamente
        cotizante.setNombre(fila.get("Nombre"));
        cotizante.setDocumento(fila.get("Documento"));

        // Manejar el valor de Edad, verificando si es null o vacío
        String edadStr = fila.get("Edad");
        if (edadStr != null && !edadStr.isEmpty()) {
            cotizante.setEdad(Integer.parseInt(edadStr));
        } else {
            cotizante.setEdad(0); // Asignar un valor predeterminado, por ejemplo 0
        }

        // Manejar el valor de SemanasCotizadas, verificando si es null o vacío
        String semanasStr = fila.get("Semanas_Cotizadas");
        if (semanasStr != null && !semanasStr.isEmpty()) {
            cotizante.setSemanasCotizadas(Integer.parseInt(semanasStr));
        } else {
            cotizante.setSemanasCotizadas(0); // Asignar un valor predeterminado, por ejemplo 0
        }

        // Asignar el fondo de procedencia, verificando si es null o vacío
        String fondo = fila.get("Fondo");
        cotizante.setFondo(fondo != null ? fondo : "Desconocido"); // Asigna "Desconocido" si no está presente

        cotizante.setCiudad(fila.get("Ciudad"));
        cotizante.setPais(fila.get("Pais"));
        return cotizante;
    }


    @Override
    public Map<String, String> extraerAtributos(Cotizante cotizante) {
        Map<String, String> atributos = new HashMap<>();
        atributos.put("Nombre", cotizante.getNombre());
        atributos.put("Documento", cotizante.getDocumento());
        atributos.put("Edad", String.valueOf(cotizante.getEdad()));
        atributos.put("SemanasCotizadas", String.valueOf(cotizante.getSemanasCotizadas()));
        atributos.put("Fondo", cotizante.getFondo());
        atributos.put("Ciudad", cotizante.getCiudad());
        atributos.put("Pais", cotizante.getPais());
        return atributos;
    }
}