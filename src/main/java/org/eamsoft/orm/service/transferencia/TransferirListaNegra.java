package org.eamsoft.orm.service.transferencia;

import java.util.List;

import org.eamsoft.orm.manager.CotizanteEntityManager;
import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class TransferirListaNegra {
    
    private CotizanteEntityManager cotizanteManager = new CotizanteEntityManager();
    
    public ResultadoValidacion transferirAListaNegra(Cotizante cotizante){
        try{
            List<Cotizante> listaNegra = cotizanteManager.findAll("lista_negra.csv", cotizanteManager.getArchivoCotizantesCsv());
        }catch (NullPointerException nullPointerException){
            
        }
        return new ResultadoValidacion(true, null);
    }

}
