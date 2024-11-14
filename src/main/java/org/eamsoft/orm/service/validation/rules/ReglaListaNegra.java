package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.transferencia.TransferirListaNegra;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class ReglaListaNegra implements ReglaValidacion{

    private TransferirListaNegra tListaNegra = new TransferirListaNegra();

    @Override
    public ResultadoValidacion aplicar(Cotizante cotizante) {
        return tListaNegra.buscarEnListaNegra(cotizante);
    }
}
