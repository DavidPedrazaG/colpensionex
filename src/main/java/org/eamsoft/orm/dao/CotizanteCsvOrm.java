package org.eamsoft.orm.dao;

import org.eamsoft.orm.modelo.Cotizante;

import java.util.HashMap;
import java.util.Map;

public class CotizanteCsvOrm extends CsvOrmBase<Cotizante>{

    @Override
    public Cotizante mapearFila(Map<String, String> fila) {
        Cotizante cotizante = new Cotizante();

        // Asignar el nombre y el documento directamente
        cotizante.setNombre(fila.get("nombre"));
        cotizante.setDocumento(fila.get("documento"));

        // Manejar el valor de Edad, verificando si es null o vacío
        String edadStr = fila.get("edad");
        if (edadStr != null && !edadStr.isEmpty()) {
            cotizante.setEdad(Integer.parseInt(edadStr));
        } else {
            cotizante.setEdad(0); // Asignar un valor predeterminado, por ejemplo 0
        }

        // Manejar el valor de SemanasCotizadas, verificando si es null o vacío
        String semanasStr = fila.get("semanas_cotizadas");
        if (semanasStr != null && !semanasStr.isEmpty()) {
            cotizante.setSemanasCotizadas(Integer.parseInt(semanasStr));
        } else {
            cotizante.setSemanasCotizadas(0); // Asignar un valor predeterminado, por ejemplo 0
        }

        // Asignar el fondo de procedencia, verificando si es null o vacío
        String fondoPublico = fila.get("fondo");
        cotizante.setFondo(fondoPublico != null ? fondoPublico : "NA"); // Asigna "NA" si no está presente

        String fondoCivil = fila.get("fondo_civil_opcional");
        cotizante.setFondoCivilOpcional(fondoCivil != null ? fondoCivil : "NA"); // Asigna "NA" si no está presente

        cotizante.setCiudad(fila.get("ciudad"));
        cotizante.setPais(fila.get("pais"));
        cotizante.setGenero(fila.get("genero"));
        cotizante.setDetalles(fila.get("detalles"));
        cotizante.setEnListaNegraUltimos6Meses(Boolean.parseBoolean(fila.get("lista_negra_6_meses")));
        cotizante.setEsPrePensionado(Boolean.parseBoolean(fila.get("pre_pensionado")));
        return cotizante;
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
