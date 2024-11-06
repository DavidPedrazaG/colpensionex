package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class ReglaListaNegra implements ReglaValidacion{

    @Override
    public ResultadoValidacion aplicar(Cotizante cotizante) {
        if (cotizante.haEstadoEnListaNegraUltimos6Meses()) {
            // Si está en lista negra, devolvemos un resultado rechazado
            return new ResultadoValidacion(false, "Rechazado: En lista negra en los últimos 6 meses");
        }
        // Si no está en lista negra, devolvemos un resultado aprobado
        return new ResultadoValidacion(true, "Aprobado");
    }
}
