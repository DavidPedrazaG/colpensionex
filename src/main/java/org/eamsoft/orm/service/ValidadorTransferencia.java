package org.eamsoft.orm.service;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;
import org.eamsoft.orm.service.validation.rules.ReglaValidacion;

import java.util.List;

public class ValidadorTransferencia {
    private List<ReglaValidacion> reglas;

    public ValidadorTransferencia(List<ReglaValidacion> reglas) {
        this.reglas = reglas;
    }

    public ResultadoValidacion validar(Cotizante cotizante) {
        for (ReglaValidacion regla : reglas) {
            ResultadoValidacion resultado = regla.aplicar(cotizante);
            if (!resultado.esAprobado()) {
                return resultado;
            }
        }
        return new ResultadoValidacion(true, "Aprobado para transferencia");
    }
}
