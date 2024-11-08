package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class ReglaPrePensionado implements ReglaValidacion{

    @Override
    public ResultadoValidacion aplicar(Cotizante cotizante) {
        if (cotizante.esPrePensionado()) {
            // Si está en lista negra, devolvemos un resultado rechazado
            return new ResultadoValidacion(false, "Rechazado: Es pre-pensionado");
        }
        // Si no está en lista negra, devolvemos un resultado aprobado
        return new ResultadoValidacion(true, "Aprobado");
    }
    
}
