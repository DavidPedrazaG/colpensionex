package org.eamsoft.orm.service.transferencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eamsoft.orm.manager.CotizanteEntityManager;
import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class TransferirListaNegra {

    private final CotizanteEntityManager cotizanteManager = new CotizanteEntityManager();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public TransferirListaNegra() {
    }

    public ResultadoValidacion transferirAListaNegra(Cotizante cotizante) {
        System.out.println("Transfiriendo cotizante a lista negra: " + cotizante.getDocumento());
        cotizante.setEnListaNegraUltimos6Meses(new Date());
        cotizanteManager.save(cotizante, cotizanteManager.getArchivoListaNegraCsv(), "lista_negra.csv");
        return new ResultadoValidacion(false,
                "Rechazado: Tiene observación disciplinaria, será pasado a la lista negra con fecha de hoy "
                        + sdf.format(cotizante.getEnListaNegraUltimos6Meses()));
    }


    public Cotizante reiniciarTiempoEnLista(Cotizante cotizante){
        return cotizante;
    }

    public ResultadoValidacion buscarEnListaNegra(Cotizante cotizante){
        List<Cotizante> listaNegra = cotizanteManager.findAll("lista_negra.csv", cotizanteManager.getArchivoListaNegraCsv());
        for (Cotizante aux : listaNegra) {
            if(aux.getDocumento()!=null){
                if(aux.getDocumento().equals(cotizante.getDocumento())){
                    aux.setEnListaNegraUltimos6Meses(new Date());
                    cotizanteManager.updateListaNegra(listaNegra);
                    return new ResultadoValidacion(false, "Rechazado: Esta en la lista negra, se encontrará en lista negra con fecha de hoy "+sdf.format(aux.getEnListaNegraUltimos6Meses()));

                }
            }
        }
        return new ResultadoValidacion(true, "Aprobado");
    }
}

