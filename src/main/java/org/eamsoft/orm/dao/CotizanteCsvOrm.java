package org.eamsoft.orm.dao;

import org.eamsoft.orm.modelo.Cotizante;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CotizanteCsvOrm extends CsvOrmBase<Cotizante>{

    @Override
    public Cotizante mapearFila(Map<String, String> fila) {
        Cotizante cotizante = new Cotizante();

        String idStr = fila.get("id");
        cotizante.setId(parseIntegerOrDefault(idStr, 0));
        // Asignar directamente campos que no necesitan validación adicional
        cotizante.setNombre(fila.get("nombre"));
        cotizante.setDocumento(fila.get("documento"));

        // Validar y convertir el campo "edad"
        String edadStr = fila.get("edad");
        cotizante.setEdad(parseIntegerOrDefault(edadStr, 0)); // Si es inválido, asignar 0

        // Validar y convertir el campo "semanas_cotizadas"
        String semanasStr = fila.get("semanas_cotizadas");
        cotizante.setSemanasCotizadas(parseIntegerOrDefault(semanasStr, 0)); // Si es inválido, asignar 0

        // Validar otros campos
        cotizante.setFondo(fila.getOrDefault("fondo", "NA"));
        cotizante.setFondoPublico(fila.getOrDefault("fondo_publico", "NA"));
        cotizante.setFondoCivilOpcional(fila.getOrDefault("fondo_civil_opcional", "NA"));
        cotizante.setCiudad(fila.get("ciudad"));
        cotizante.setPais(fila.get("pais"));
        cotizante.setGenero(fila.get("genero"));
        cotizante.setDetalles(fila.get("detalles"));

        // Manejar la fecha de lista negra
        try {
            String csvDate = fila.get("lista_negra_6_meses");
            if (csvDate != null && !csvDate.isEmpty() && !csvDate.equalsIgnoreCase("null")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                cotizante.setEnListaNegraUltimos6Meses(sdf.parse(csvDate));
            } else {
                cotizante.setEnListaNegraUltimos6Meses(null);
            }
        } catch (Exception e) {
            cotizante.setEnListaNegraUltimos6Meses(null); // En caso de error, asignar null
        }

        // Validar el campo booleano
        cotizante.setEsPrePensionado(Boolean.parseBoolean(fila.getOrDefault("pre_pensionado", "false")));

        return cotizante;
    }

    private int parseIntegerOrDefault(String value, int defaultValue) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue; // Si ocurre un error, retornar el valor predeterminado
        }
    }



    @Override
    public Map<String, String> extraerAtributos(Cotizante cotizante) {
        Map<String, String> atributos = new HashMap<>();
        atributos.put("nombre", cotizante.getNombre());
        atributos.put("documento", cotizante.getDocumento());
        atributos.put("edad", String.valueOf(cotizante.getEdad()));
        atributos.put("semanas_cotizadas", String.valueOf(cotizante.getSemanasCotizadas()));
        atributos.put("fondo", cotizante.getFondo());
        atributos.put("fondo_civil_opcional", cotizante.getFondoCivilOpcional());
        atributos.put("ciudad", cotizante.getCiudad());
        atributos.put("pais", cotizante.getPais());
        atributos.put("genero", cotizante.getGenero());
        atributos.put("detalles", cotizante.getDetalles());
        atributos.put("lista_negra_6_meses", String.valueOf(cotizante.getEnListaNegraUltimos6Meses()));
        atributos.put("pre_pensionado", String.valueOf(cotizante.esPrePensionado()));
        return atributos;
    }
}
