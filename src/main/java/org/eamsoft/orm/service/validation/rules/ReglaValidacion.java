package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;


public interface ReglaValidacion {
    ResultadoValidacion aplicar(Cotizante cotizante);
}
